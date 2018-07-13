package com.madness.codingchallange.utils.data_manager.web_data_manager;

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

    public WebDataManager(UpcomingMoviesPresenter presenter) {
        this.presenter = presenter;
    }

    public static Retrofit getAdapter() {
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
                public void onResponse(Call<UpComingMoviesResponse> call, Response<UpComingMoviesResponse> response) {
                    presenter.getUpcomingMoviesSuccess(response);
                }

                @Override
                public void onFailure(Call<UpComingMoviesResponse> call, Throwable t) {
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
                public void onResponse(Call<ConfigurationResponse> call, Response<ConfigurationResponse> response) {
                    presenter.getConfigurationSucces(response);
                }

                @Override
                public void onFailure(Call<ConfigurationResponse> call, Throwable t) {
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
                public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                    presenter.getGenreListSuccess(response);
                }

                @Override
                public void onFailure(Call<GenreResponse> call, Throwable t) {
                    presenter.getGenreListFail(t);
                }
            });
        }
    }
}
