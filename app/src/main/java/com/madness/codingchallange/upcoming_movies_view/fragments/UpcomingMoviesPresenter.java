package com.madness.codingchallange.upcoming_movies_view.fragments;

import com.madness.codingchallange.utils.data_manager.web_data_manager.WebDataManager;
import com.madness.codingchallange.utils.data_object.ConfigurationPojo;
import com.madness.codingchallange.utils.data_object.ConfigurationResponse;
import com.madness.codingchallange.utils.data_object.GenrePojo;
import com.madness.codingchallange.utils.data_object.GenreResponse;
import com.madness.codingchallange.utils.data_object.UpComingMoviesPojo;
import com.madness.codingchallange.utils.data_object.UpComingMoviesResponse;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * Presenter class for the fragment {@link UpcomingMoviesFragment}
 */
public class UpcomingMoviesPresenter implements UpcomingMoviesContracts.UpcomingMoviesPresenter {

    //Needed variables for setting the presenter and the WebDataManager for this ´presenter
    private WebDataManager dataManager = new WebDataManager(this);
    private UpcomingMoviesContracts.UpcomingMoviesView view;

    /**
     * @param view reference from fragment {@link UpcomingMoviesFragment} after implements {@link UpcomingMoviesContracts.UpcomingMoviesView}
     */
    @Override
    public void init(UpcomingMoviesContracts.UpcomingMoviesView view) {
        this.view = view;
    }

    /**
     * Retrieves configuration data from API
     */
    @Override
    public void getConfiguration() {
        view.showLoading();
        dataManager.getConfiguration();
    }

    /**
     * Method that recieves response from {@link WebDataManager} and then passes it to the presenter get the data
     */
    @Override
    public void getConfigurationSuccess(Response<ConfigurationResponse> response) {
        view.storeConfigurationData(response);
    }

    /**
     * If web service call fails {@link WebDataManager#getConfiguration()}, this will catch it
     */
    @Override
    public void getConfigurationFail(Throwable t) {
        view.hideLoading();
        view.getConfigurationFail(t);
    }

    /**
     * Web service call {@link WebDataManager#getGenreList()}
     */
    @Override
    public void getGenreList() {
        dataManager.getGenreList();
    }

    /**
     * Method that recieves response from {@link WebDataManager} and then passes it to the presenter get the data
     */
    @Override
    public void getGenreListSuccess(Response<GenreResponse> response) {
        view.storeGenreList(response);
    }

    /**
     * If web service call fails {@link WebDataManager#getGenreList()} }, this will catch it
     */
    @Override
    public void getGenreListFail(Throwable t) {
        view.hideLoading();
        view.getGenreListFail(t);
    }

    /**
     * Retrieves upcoming movie list data from API
     * @param page number of page
     */
    @Override
    public void getUpcomingMovies(Integer page, Integer scrollPosition) {
        dataManager.getUpcomingMovies(page);
        if(scrollPosition != 0){
            view.showLoading();
            view.showLastItem(scrollPosition);
        }
    }

    @Override
    public void getUpcomingMoviesSuccess(Response<UpComingMoviesResponse> response) {
        view.showList(response);
        view.hideLoading();
    }

    /**
     * If web service call fails {@link WebDataManager#getUpcomingMovies(Integer)}, this will catch it
     */
    @Override
    public void getUpcomingMoviesFail(Throwable t) {
        view.hideLoading();
        view.showListFail(t);
    }
}
