package com.madness.codingchallange.movie_info_view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.madness.codingchallange.R;
import com.madness.codingchallange.utils.UtilsMethods;
import com.madness.codingchallange.utils.data_object.ConfigurationPojo;
import com.madness.codingchallange.utils.data_object.GenrePojo;
import com.madness.codingchallange.utils.data_object.UpComingMoviesPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Activity used to show Movie specific information to the user when select a movie in
 * {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesFragment}
 */
public class ShowMovieInfoActivity extends AppCompatActivity implements ShowMovieInfoContracts.ShowMovieInfoView{

    private ShowMovieInfoPresenter presenter = new ShowMovieInfoPresenter();
    private UpComingMoviesPojo movieData;
    private ConfigurationPojo dataConfig;
    private ArrayList<GenrePojo> genreList = new ArrayList<>();
    private TextView movieTitle, genre, releaseDate, overview;
    private ImageView poster, backPoster;
    private static final String MOVIE_KEY = "movieList";
    private static final String CONFIG_DATA = "configurationData";
    private static final String GENRE_LIST = "genreList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_infoactivity);
        presenter.init(this);
        movieTitle = findViewById(R.id.mo_title);
        genre = findViewById(R.id.genre_list);
        releaseDate = findViewById(R.id.rel_date);
        overview = findViewById(R.id.mo_overview);
        poster = findViewById(R.id.frontal_poster);
        backPoster = findViewById(R.id.movie_back_poster);

        if(getIntent() != null){
            movieData = (UpComingMoviesPojo) getIntent().getSerializableExtra(MOVIE_KEY);
            dataConfig = (ConfigurationPojo) getIntent().getSerializableExtra(CONFIG_DATA);
            genreList = (ArrayList<GenrePojo>) getIntent().getSerializableExtra(GENRE_LIST);
            presenter.setData();
        }
    }

    /**
     * This method sets the information into the views in the UI
     */
    @Override
    public void setData() {
        String posterPath = "", backdropPath = "";
        if(dataConfig != null){
            posterPath = dataConfig.getBase_url() + "/" + dataConfig.getLogo_sizes()[4] + "/";
            backdropPath = dataConfig.getBase_url() + "/" + dataConfig.getLogo_sizes()[4] + "/";
        }
        if(movieData != null){
            String releaseString = movieData.getRelase_date();
            String overviewString = movieData.getOverview();
            String titleString = movieData.getTitle();
            posterPath = posterPath.concat(movieData.getPoster_path());
            backdropPath = backdropPath.concat(movieData.getBackdrop_path());

            movieTitle.setText(titleString);
            releaseDate.setText(releaseString);
            overview.setText(overviewString);
            genre.setText(UtilsMethods.setGenreNoConcat(movieData.getGenre_ids(), genreList));
        }else{
            movieTitle.setText(getString(R.string.missing_text_smg_activity));
            releaseDate.setText(getString(R.string.missing_text_smg_activity));
            overview.setText(getString(R.string.missing_text_smg_activity));
            genre.setText(getString(R.string.missing_text_smg_activity));
            posterPath = "missing_path";
            backdropPath = "missing_path";
        }

        Picasso.get()
                .load(posterPath)
                .placeholder(R.drawable.progress_animation)
                .into(poster);

        Picasso.get()
                .load(backdropPath)
                .placeholder(R.drawable.progress_animation)
                .fit()
                .into(backPoster);
    }
}
