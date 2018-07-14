package com.madness.codingchallange.utils.data_object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Web services response for  {@link com.madness.codingchallange.utils.data_manager.web_data_manager.WebDataManager#getGenreList}
 * used in {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesFragment}
 */

public class GenreResponse {

    @SerializedName("genres")
    @Expose
    private GenrePojo[] genres;

    public GenrePojo[] getGenres() {
        return genres;
    }
}
