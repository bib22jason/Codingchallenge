package com.madness.codingchallange.upcoming_movies_view.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.madness.codingchallange.R;
import com.madness.codingchallange.utils.data_object.ConfigurationResponse;
import com.madness.codingchallange.utils.data_object.GenrePojo;
import com.madness.codingchallange.utils.data_object.GenreResponse;
import com.madness.codingchallange.utils.data_object.UpComingMoviesPojo;
import com.madness.codingchallange.utils.data_object.UpComingMoviesResponse;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Response;

/**
 * Fragment that contains the list of upcoming movies, get the configuration data, genre list from API and the present it to the user
 */
public class UpcomingMoviesFragment extends Fragment implements UpcomingMoviesContracts.UpcomingMoviesView{

    private UpcomingMoviesPresenter presenter = new UpcomingMoviesPresenter();
    private ArrayList<UpComingMoviesPojo> movieList = new ArrayList<>();
    private ArrayList<GenrePojo> genreData = new ArrayList<>();
    private ConfigurationResponse dataConfig;
    private RecyclerView recyclerView;
    private Button pageL, pageA;
    private UpcomingMoviesRecyclerAdapter adapter;
    private AlertDialog dialog = null;
    private static final String MOVIE_LIST = "movie_list";
    private static final String GENRE_LIST = "genre_list";
    private static final String DATA_CONFIG = "data_config";
    private static Integer page = 1;
    private Integer maxPages = 0;

    public static UpcomingMoviesFragment newInstance(){
        return new UpcomingMoviesFragment();
    }

    public UpcomingMoviesFragment() {
        // Required empty public constructor
    }

    /**
     * onCreateView method where views are assigned also set the presenbter and make the first web service call
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_movies, container, false);
        pageL = view.findViewById(R.id.less_page);
        pageA = view.findViewById(R.id.add_page);
        pageL.setOnClickListener(changPageButtons);
        pageA.setOnClickListener(changPageButtons);
        recyclerView = view.findViewById(R.id.recycler_container);

        //Basic config of Recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Presenter init
        presenter.init(this);

        if(savedInstanceState != null){
            movieList = (ArrayList<UpComingMoviesPojo>) savedInstanceState.getSerializable(MOVIE_LIST);
            genreData = (ArrayList<GenrePojo>) savedInstanceState.getSerializable(GENRE_LIST);
            dataConfig = (ConfigurationResponse) savedInstanceState.getSerializable(DATA_CONFIG);
            if(dataConfig != null) {
                adapter = new UpcomingMoviesRecyclerAdapter(movieList, dataConfig.getImages(), genreData);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }else {
            presenter.showLoading();
            presenter.getConfiguration();
        }
        return view;
    }

    /**
     * OnClickListner method for buttons
     */
    private View.OnClickListener changPageButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.less_page:
                    if(page <= 1){
                        page = 1;
                    }else{
                        page--;
                        presenter.getUpcomingMovies(page);
                        presenter.showLoading();
                        v.setEnabled(false);
                    }
                    break;
                case R.id.add_page:
                    if(page < maxPages){
                        page++;
                        presenter.getUpcomingMovies(page);
                        presenter.showLoading();
                        v.setEnabled(false);
                    }
                    break;
            }
        }
    };

    /**
     * Save fragment state
     * @param outState bundle with info stored
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MOVIE_LIST, movieList);
        outState.putSerializable(GENRE_LIST, genreData);
        outState.putSerializable(DATA_CONFIG, dataConfig);
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter} shows alertdialog
     */
    @Override
    public void showLoading() {
        if(dialog == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(false);
            builder.setMessage("Retrieving list");
            dialog = builder.create();
            dialog.show();
        }
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter} hides alertdialog
     */
    @Override
    public void hideLoading() {
        if(dialog != null){
            dialog.hide();
            dialog = null;
        }
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * stores response from web service in local varibale then passes it to the recycler so it can be painted in device
     */
    @Override
    public void getUpcomingMoviesSuccess(Response<UpComingMoviesResponse> response) {
        UpComingMoviesResponse data = response.body();
        if(data != null) {
            maxPages = data.getTotal_pages();
        }
        movieList.clear();
        if(data != null) {
            movieList.addAll(Arrays.asList(data.getResults()));
            adapter = new UpcomingMoviesRecyclerAdapter(movieList, dataConfig.getImages(), genreData);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            presenter.hideLoading();

            //re enable buttons
            pageL.setEnabled(true);
            pageA.setEnabled(true);
        }else{
            //re enable buttons
            pageL.setEnabled(true);
            pageA.setEnabled(true);
            presenter.hideLoading();
            Toast.makeText(getContext(), "There was an error while downloading, please try again!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * Method recovers Data configuration from API and calls for the next web services call
     */
    @Override
    public void getConfigurationSuccess(Response<ConfigurationResponse> response) {
        dataConfig = response.body();
        presenter.getGenreList();
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * Method recovers Data configuration from API and calls for the next web services call
     */
    @Override
    public void getGenreListSuccess(Response<GenreResponse> response) {
        GenreResponse data = response.body();
        genreData.clear();
        if(data != null) {
            genreData.addAll(Arrays.asList(data.getGenres()));
            presenter.getUpcomingMovies(page);
        }else {
            //avoid crashing errors, if null genres wont show up
            genreData = new ArrayList<>();
            presenter.getUpcomingMovies(page);
        }
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * Method tells the user if the webservice didtn respond
     */
    @Override
    public void getConfigurationFail(Throwable t) {
        Toast.makeText(getContext(), "There was an error while downloading the information", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * Method tells the user if the webservice didtn respond
     */
    @Override
    public void getGenreListFail(Throwable t) {
        Toast.makeText(getContext(), "There was an error while downloading the information", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * Method tells the user if the webservice didtn respond
     */
    @Override
    public void getUpcomingMoviesFail(Throwable t) {
        Toast.makeText(getContext(), "There was an error while downloading the information", Toast.LENGTH_SHORT).show();
    }
}
