package com.madness.codingchallange.upcoming_movies_view.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
public class UpcomingMoviesFragment extends Fragment
        implements UpcomingMoviesContracts.UpcomingMoviesView {

    private UpcomingMoviesPresenter presenter = new UpcomingMoviesPresenter();
    private ArrayList<UpComingMoviesPojo> movieList = new ArrayList<>();
    private ArrayList<GenrePojo> genreData = new ArrayList<>();
    private ConfigurationResponse dataConfig;
    private RecyclerView recyclerView;
    private UpcomingMoviesRecyclerAdapter adapter;
    private AlertDialog dialog = null;
    private static final String MOVIE_LIST = "movie_list";
    private static final String GENRE_LIST = "genre_list";
    private static final String DATA_CONFIG = "data_config";
    private static final String MAX_PAGES = "max_pages";
    private static Integer PAGE = 1;
    private static Integer NO_SCROLLING = 0;
    private Integer maxPages = 0;
    private Integer orientation;
    private Boolean networkAvailable = false;
    private static String TAG = UpcomingMoviesFragment.class.getSimpleName();

    public static UpcomingMoviesFragment newInstance() {
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
        recyclerView = view.findViewById(R.id.recycler_container);
        TextView noInternetTV = view.findViewById(R.id.no_internet);
        //Basic config of Recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Presenter init
        presenter.init(this);
        orientation = getScreenOrientation();
        networkAvailable = isNetworkAvailable(getContext());

        //check network availability
        if(networkAvailable){
            recyclerView.setVisibility(View.VISIBLE);
            noInternetTV.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.GONE);
            noInternetTV.setVisibility(View.VISIBLE);
        }

        if (savedInstanceState != null) {
            movieList = (ArrayList<UpComingMoviesPojo>) savedInstanceState.getSerializable(MOVIE_LIST);
            genreData = (ArrayList<GenrePojo>) savedInstanceState.getSerializable(GENRE_LIST);
            dataConfig = (ConfigurationResponse) savedInstanceState.getSerializable(DATA_CONFIG);
            maxPages = savedInstanceState.getInt(MAX_PAGES);
            if (dataConfig != null) {
                adapter = new UpcomingMoviesRecyclerAdapter(movieList, dataConfig.getImages(), genreData, orientation);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                setRecyclerBottomListener();
            }else{
                if(networkAvailable) {
                    movieList = new ArrayList<>();
                    genreData = new ArrayList<>();
                    presenter.getConfiguration();
                }
            }
        } else {
            if(networkAvailable) {
                presenter.getConfiguration();
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * Save fragment state
     *
     * @param outState bundle with info stored
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(networkAvailable) {
            super.onSaveInstanceState(outState);
            outState.putSerializable(MOVIE_LIST, movieList);
            outState.putSerializable(GENRE_LIST, genreData);
            outState.putSerializable(DATA_CONFIG, dataConfig);
            outState.putInt(MAX_PAGES, maxPages);
        }
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter} shows alertdialog
     */
    @Override
    public void showLoading() {
        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(true);
            builder.setMessage(R.string.loading_dialog_string);
            dialog = builder.create();
            dialog.show();
        }
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter} hides alertdialog
     */
    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.hide();
            dialog = null;
        }
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * stores response from web service in local varibale then passes it to the recycler so it can be painted in device
     */
    @Override
    public void showList(Response<UpComingMoviesResponse> response) {
        UpComingMoviesResponse data = response.body();
        if (data != null) {
            maxPages = data.getTotal_pages();
        }
        if (data != null) {
            movieList.addAll(Arrays.asList(data.getResults()));
            adapter = new UpcomingMoviesRecyclerAdapter(movieList, dataConfig.getImages(), genreData, orientation);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            setRecyclerBottomListener();

        } else {
            //response came null
            Log.e(TAG, response.message());
            Toast.makeText(getContext(), R.string.error_msg_downloading_again_string, Toast.LENGTH_SHORT).show();
            presenter.getUpcomingMovies(PAGE, NO_SCROLLING);
        }
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * Method recovers Data configuration from API and calls for the next web services call
     */
    @Override
    public void storeConfigurationData(Response<ConfigurationResponse> response) {
        dataConfig = response.body();
        presenter.getGenreList();
    }


    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * Method recovers Data configuration from API and calls for the next web services call
     */
    @Override
    public void storeGenreList(Response<GenreResponse> response) {
        GenreResponse data = response.body();
        genreData.clear();
        if (data != null) {
            genreData.addAll(Arrays.asList(data.getGenres()));
            presenter.getUpcomingMovies(PAGE, NO_SCROLLING);
        } else {
            //avoid crashing errors, if genres is null
            genreData = new ArrayList<>();
            presenter.getUpcomingMovies(PAGE, NO_SCROLLING);
        }
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * Method tells the user if the webservice did'nt respond
     */
    @Override
    public void getConfigurationFail(Throwable t) {
        Log.e(TAG, t.getMessage());
        Toast.makeText(getContext(), R.string.error_msg_download_upcoming_movie_list, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * Method tells the user if the webservice didtn respond
     */
    @Override
    public void getGenreListFail(Throwable t) {
        Log.e(TAG, t.getMessage());
        Toast.makeText(getContext(), R.string.error_msg_download_upcoming_movie_list, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLastItem(final Integer position) {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(position - 1);
            }
        }, 800);
    }

    /**
     * Method from {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts.UpcomingMoviesPresenter}
     * Method tells the user if the webservice did'nt respond
     */
    @Override
    public void showListFail(Throwable t) {
        Log.e(TAG, t.getMessage());
        Toast.makeText(getContext(), R.string.error_msg_download_upcoming_movie_list, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method used to get current orientation of the screen   * @return screen orientation
     */
    public int getScreenOrientation() {
        int orientation = Configuration.ORIENTATION_UNDEFINED;
        if (getActivity() != null) {
            Display getOrient = getActivity().getWindowManager().getDefaultDisplay();

            if (getOrient.getWidth() == getOrient.getHeight()) {
                orientation = Configuration.ORIENTATION_SQUARE;
            } else {
                if (getOrient.getWidth() < getOrient.getHeight()) {
                    orientation = Configuration.ORIENTATION_PORTRAIT;
                } else {
                    orientation = Configuration.ORIENTATION_LANDSCAPE;
                }
            }
        }
        return orientation;
    }

    public void setRecyclerBottomListener() {
        //Recycler listener to know when the user reach last item
        adapter.setOnBottomReachListener(new UpcomingMoviesRecyclerAdapter.OnBottomReachListener() {
            @Override
            public void onBottomReach(int position) {
                if (PAGE < maxPages) {
                    PAGE++;
                    presenter.getUpcomingMovies(PAGE, position);
                } else {
                    Toast.makeText(getContext(), R.string.last_item_msg_string, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if(connectivityManager != null){
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        }else{
            return false;
        }
    }
}
