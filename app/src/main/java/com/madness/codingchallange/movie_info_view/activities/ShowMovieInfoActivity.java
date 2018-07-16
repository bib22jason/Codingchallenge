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
    private ImageView poster;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movie_infoactivity);
        presenter.init(this);
        movieTitle = findViewById(R.id.movie_title);
        genre = findViewById(R.id.genre);
        releaseDate = findViewById(R.id.release_date);
        overview = findViewById(R.id.movie_overview);
        poster = findViewById(R.id.movie_poster);
        relativeLayout = findViewById(R.id.relative_background);

        if(getIntent() != null){
            movieData = (UpComingMoviesPojo) getIntent().getSerializableExtra("movieList");
            dataConfig = (ConfigurationPojo) getIntent().getSerializableExtra("configurationData");
            genreList = (ArrayList<GenrePojo>) getIntent().getSerializableExtra("genreList");
            presenter.setData();
        }
    }

    /**
     * This method sets the information into the views in the UI
     */
    @Override
    public void setData() {
        movieTitle.setText(movieData.getTitle());
        releaseDate.setText(UtilsMethods.setReleaseDate(movieData.getRelase_date()));
        overview.setText(movieData.getOverview());
        genre.setText(UtilsMethods.setGenre(movieData.getGenre_ids(), genreList));
        Picasso.get()
                .load(dataConfig.getBase_url() + "/" + dataConfig.getLogo_sizes()[4] + "/" + movieData.getPoster_path())
                .into(poster);
    }
}
