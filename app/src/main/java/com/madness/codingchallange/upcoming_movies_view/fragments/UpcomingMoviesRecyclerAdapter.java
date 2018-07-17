package com.madness.codingchallange.upcoming_movies_view.fragments;

import android.content.Intent;
import android.content.res.Configuration;
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
    private Integer orientation;

    //Bottom reach listener
    private OnBottomReachListener onBottomReachListener;
    public void setOnBottomReachListener(OnBottomReachListener onBottomReachListener) {
        this.onBottomReachListener = onBottomReachListener;
    }

    /**
     * Constructor of class
     * @param movieList the list of the upcoming movies
     * @param configurationData list of data configuration from API
     * @param genreList the list of genres
     * @param orientation portrait or landscape
     */
    UpcomingMoviesRecyclerAdapter(ArrayList<UpComingMoviesPojo> movieList, ConfigurationPojo configurationData,
                                  ArrayList<GenrePojo> genreList, Integer orientation) {
        this.movieList = movieList;
        this.configurationData = configurationData;
        this.genreList = genreList;
        this.orientation = orientation;
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
        String title = movieList.get(holder.getAdapterPosition()).getTitle();

        holder.title.setText(!title.isEmpty() ? title : "Missing title text");
        holder.releaseDate.setText(UtilsMethods.setReleaseDateNoConcat(movieList.get(holder.getAdapterPosition()).getRelase_date()));

        Picasso.get()
                .load(configurationData.getBase_url() + "/" + configurationData.getLogo_sizes()[4] + "/" + movieList.get(holder.getAdapterPosition()).getPoster_path())
                .placeholder(R.drawable.progress_animation)
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

        if(position == movieList.size() - 1 && onBottomReachListener != null){
            onBottomReachListener.onBottomReach(position);
        }

        if(Configuration.ORIENTATION_LANDSCAPE == orientation){
            String overvText = movieList.get(holder.getAdapterPosition()).getOverview();
            holder.overview.setVisibility(View.VISIBLE);
            holder.overview.setText(overvText);
        }else{
            holder.overview.setVisibility(View.GONE);
            holder.overview.setText("");
        }
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
         * Views from layout R.layout.upcoming_movies_card_holder
         */
        private CardView cardView;
        private TextView title, releaseDate, overview;
        private ImageView poster;

        UpcomingMoviesHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview);
            poster = itemView.findViewById(R.id.movie_poster);
            title = itemView.findViewById(R.id.movie_title);
            overview = itemView.findViewById(R.id.movie_over);
            releaseDate = itemView.findViewById(R.id.movie_relase_date);
        }
    }

    public interface OnBottomReachListener {
        void onBottomReach(int position);
    }
}
