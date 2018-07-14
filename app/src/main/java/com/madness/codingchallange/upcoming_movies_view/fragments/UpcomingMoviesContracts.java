package com.madness.codingchallange.upcoming_movies_view.fragments;

import com.madness.codingchallange.utils.data_object.ConfigurationResponse;
import com.madness.codingchallange.utils.data_object.GenreResponse;
import com.madness.codingchallange.utils.data_object.UpComingMoviesResponse;

import retrofit2.Response;

/**
 * Interface that hold the contract list for the presenter and the view in the following fragment {@link UpcomingMoviesFragment}
 */
public interface UpcomingMoviesContracts {

    interface UpcomingMoviesPresenter {
        void init(UpcomingMoviesView view);

        // hide-show alert dialog
        void showLoading();
        void hideLoading();

        void getUpcomingMovies(Integer page);
        void getUpcomingMoviesSuccess(Response<UpComingMoviesResponse> response);
        void getUpcomingMoviesFail(Throwable t);

        void getConfiguration();
        void getConfigurationSuccess(Response<ConfigurationResponse> response);
        void getConfigurationFail(Throwable t);

        void getGenreList();
        void getGenreListSuccess(Response<GenreResponse> response);
        void getGenreListFail(Throwable t);
    }

    interface UpcomingMoviesView {
        // hide-show alert dialog
        void showLoading();
        void hideLoading();

        void getUpcomingMoviesSuccess(Response<UpComingMoviesResponse> response);
        void getUpcomingMoviesFail(Throwable t);

        void getConfigurationSuccess(Response<ConfigurationResponse> response);
        void getConfigurationFail(Throwable t);

        void getGenreListSuccess(Response<GenreResponse> response);
        void getGenreListFail(Throwable t);
    }
}
