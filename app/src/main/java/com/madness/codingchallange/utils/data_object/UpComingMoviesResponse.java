package com.madness.codingchallange.utils.data_object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Web service response for {@link com.madness.codingchallange.utils.data_manager.web_data_manager.WebDataManager#getUpcomingMovies}
 * used in {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesFragment}
 */

public class UpComingMoviesResponse implements Serializable{

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("results")
    @Expose
    private UpComingMoviesPojo[] results;

    @SerializedName("dates")
    @Expose
    private UpComingMoviesHelper dates;

    @SerializedName("total_pages")
    @Expose
    private Integer total_pages;

    @SerializedName("total_results")
    @Expose
    private Integer total_results;


    public Integer getPage() {
        return page;
    }

    public UpComingMoviesPojo[] getResults() {
        return results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public Integer getTotal_results() {
        return total_results;
    }
}
