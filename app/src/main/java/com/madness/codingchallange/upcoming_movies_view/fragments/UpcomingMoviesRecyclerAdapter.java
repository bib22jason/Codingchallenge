package com.madness.codingchallange.upcoming_movies_view.fragments;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.madness.codingchallange.R;
import com.madness.codingchallange.movie_info_view.activities.ShowMovieInfoActivity;
import com.madness.codingchallange.utils.UtilsMethods;
import com.madness.codingchallange.utils.data_object.ConfigurationPojo;
import com.madness.codingchallange.utils.data_object.GenrePojo;
import com.madness.codingchallange.utils.data_object.UpComingMoviesPojo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Recycler adapter class for {@link UpcomingMoviesFragment}
 */
public class UpcomingMoviesRecyclerAdapter extends RecyclerView.Adapter<UpcomingMoviesRecyclerAdapter.UpcomingMoviesHolder> {

    //Data coming from constructor
    private ArrayList<UpComingMoviesPojo> movieList;
    private ArrayList<GenrePojo> genreList;
    private ConfigurationPojo configurationData;

    //Max number of characters in case the overview of the movie is too long
    private static final Integer MAX_CHARS = 300;

    /**
     * Constructor of class
     * @param movieList the list of the upcoming movies
     * @param configurationData list of data configuration from API
     * @param genreList the list of genres
     */
    UpcomingMoviesRecyclerAdapter(ArrayList<UpComingMoviesPojo> movieList, ConfigurationPojo configurationData,
                                  ArrayList<GenrePojo> genreList) {
        this.movieList = movieList;
        this.configurationData = configurationData;
        this.genreList = genreList;
    }

    /**
     * Method that creates the new view for recycler
     * @param parent view parent
     * @param viewType view type
     * @return new view holder
     */
    @NonNull
    @Override
    public UpcomingMoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_movies_card_holder, parent, false);
        return new UpcomingMoviesHolder(view);
    }

    /**
     * @param holder view container of your custom view
     * @param position position of adapter
     */
    @Override
    public void onBindViewHolder(@NonNull final UpcomingMoviesHolder holder, int position) {
        String overview = movieList.get(holder.getAdapterPosition()).getOverview();
        if (overview.length() >= MAX_CHARS) {
            overview = overview.substring(0, MAX_CHARS) + "...";
        }
        holder.title.setText(movieList.get(holder.getAdapterPosition()).getTitle());
        holder.overview.setText(overview);
        String genres = UtilsMethods.setGenre(movieList.get(holder.getAdapterPosition()).getGenre_ids(), genreList);
        holder.genres.setText(genres);
        holder.relaseDate.setText(UtilsMethods.setReleaseDate(movieList.get(holder.getAdapterPosition()).getRelase_date()));

        Picasso.get()
                .load(configurationData.getBase_url() + "/" + configurationData.getLogo_sizes()[4] + "/" + movieList.get(holder.getAdapterPosition()).getPoster_path())
                .into(holder.poster);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShowMovieInfoActivity.class);
                UpComingMoviesPojo data = movieList.get(holder.getAdapterPosition());
                intent.putExtra("movieList", data);
                intent.putExtra("genreList", genreList);
                intent.putExtra("configurationData", configurationData);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    /**
     * Sub class of adapter that is used as View Holder
     */
    class UpcomingMoviesHolder extends RecyclerView.ViewHolder {


        /**
         * Views from layout R.layout.upcming_movies_card_holder
         */
        private CardView cardView;
        private TextView title, overview, genres, relaseDate;
        private ImageView poster;

        UpcomingMoviesHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview);
            poster = itemView.findViewById(R.id.movie_poster);
            title = itemView.findViewById(R.id.movie_title);
            overview = itemView.findViewById(R.id.movie_overview);
            genres = itemView.findViewById(R.id.movie_genre);
            relaseDate = itemView.findViewById(R.id.movie_relase_date);
        }
    }

}
