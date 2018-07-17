package com.madness.codingchallange.utils.data_manager.web_data_manager;

import android.support.annotation.NonNull;

import com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesPresenter;
import com.madness.codingchallange.utils.data_object.ConfigurationResponse;
import com.madness.codingchallange.utils.data_object.GenreResponse;
import com.madness.codingchallange.utils.data_object.UpComingMoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is handled by the presenter. This class is in charge of making web services calls and return the data to the presenter
 */

public class WebDataManager {

    private static Retrofit retrofit;
    private static WebServiceInterface service;
    private UpcomingMoviesPresenter presenter;
    private MovieListCallBack callBack;

    public void setMovieList(UpComingMoviesResponse movieList) {
        this.movieList = movieList;
        callBack.callBack(movieList);
    }

    private UpComingMoviesResponse movieList;

    public WebDataManager(UpcomingMoviesPresenter presenter) {
        this.presenter = presenter;
    }

    public WebDataManager(UpcomingMoviesPresenter presenter, MovieListCallBack callBack) {
        this.presenter = presenter;
        this.callBack = callBack;
    }

    private static Retrofit getAdapter() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * This method retrieves te upcoming movies list and after getting the response it passes the data to the presenter
     *
     * @param page number of page the user want to go to. Used in {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesFragment}
     */
    public void getUpcomingMovies(Integer page) {
        Retrofit retrofit = getAdapter();
        if (retrofit != null) {
            service = retrofit.create(WebServiceInterface.class);
            String url = Constants.BASE_URL + Constants.MOVIES_TYPE + Constants.UPCOMING_MOVIES
                    + Constants.API_KEY + Constants.MOVIE_LANGUAGE + Constants.EN_US_LANG + Constants.QUERY_PAGE + page;
            Call<UpComingMoviesResponse> webServiceCall = service.getUpcomingMovies(url);
            webServiceCall.enqueue(new Callback<UpComingMoviesResponse>() {
                @Override
                public void onResponse(@NonNull Call<UpComingMoviesResponse> call, @NonNull Response<UpComingMoviesResponse> response) {
                    presenter.getUpcomingMoviesSuccess(response);
                }

                @Override
                public void onFailure(@NonNull Call<UpComingMoviesResponse> call, @NonNull Throwable t) {
                    presenter.getUpcomingMoviesFail(t);
                }
            });
        }
    }

    /**
     * This method retrieves the data configuracion list from API and after getting the response it passes the data to the presenter
     * Used in {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesFragment}
     */
    public void getConfiguration() {
        Retrofit retrofit = getAdapter();
        if (retrofit != null) {
            service = retrofit.create(WebServiceInterface.class);
            Call<ConfigurationResponse> webServiceCall = service.getConfiguration();
            webServiceCall.enqueue(new Callback<ConfigurationResponse>() {
                @Override
                public void onResponse(@NonNull Call<ConfigurationResponse> call, @NonNull Response<ConfigurationResponse> response) {
                    presenter.getConfigurationSuccess(response);
                }

                @Override
                public void onFailure(@NonNull Call<ConfigurationResponse> call, @NonNull Throwable t) {
                    presenter.getConfigurationFail(t);
                }
            });
        }
    }

    /**
     * This method retrieves the genre id list and after getting the response it passes the data to the presenter
     * Used in {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesFragment}
     */
    public void getGenreList() {
        Retrofit retrofit = getAdapter();
        if (retrofit != null) {
            service = retrofit.create(WebServiceInterface.class);
            Call<GenreResponse> webServiceCall = service.getGenreList();
            webServiceCall.enqueue(new Callback<GenreResponse>() {
                @Override
                public void onResponse(@NonNull Call<GenreResponse> call, @NonNull Response<GenreResponse> response) {
                    presenter.getGenreListSuccess(response);
                }

                @Override
                public void onFailure(@NonNull Call<GenreResponse> call, @NonNull Throwable t) {
                    presenter.getGenreListFail(t);
                }
            });
        }
    }

    public interface MovieListCallBack {
        void callBack(UpComingMoviesResponse data);
    }
}
