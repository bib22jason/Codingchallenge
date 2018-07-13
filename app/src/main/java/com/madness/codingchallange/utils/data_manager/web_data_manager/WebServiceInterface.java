package com.madness.codingchallange.utils.data_manager.web_data_manager;

import com.madness.codingchallange.utils.data_object.ConfigurationResponse;
import com.madness.codingchallange.utils.data_object.GenreResponse;
import com.madness.codingchallange.utils.data_object.UpComingMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Web service interface to use along with Retrofit 2
 */

public interface WebServiceInterface {

    /**
     * This method retrieves the list of upcoming movies
     * @param url dinamyc URL to see other pages
     */
    @GET
    Call<UpComingMoviesResponse> getUpcomingMovies(@Url String url);

    /**
     * This interface retrieves the list configuration data from API
     */
    @GET(Constants.BASE_URL + Constants.CONFIGURATION + Constants.API_KEY)
    Call<ConfigurationResponse> getConfiguration();

    /**
     * This method retrieves the list of genres
     */
    @GET(Constants.BASE_URL + Constants.GENRES_TYPE + Constants.MOVIES_TYPE + Constants.GENRES + Constants.API_KEY + Constants.MOVIE_LANGUAGE)
    Call<GenreResponse> getGenreList();
}
