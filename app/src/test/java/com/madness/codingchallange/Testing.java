package com.madness.codingchallange;

import android.test.suitebuilder.annotation.SmallTest;

import com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesContracts;
import com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesPresenter;
import com.madness.codingchallange.utils.data_object.ConfigurationResponse;
import com.madness.codingchallange.utils.data_object.GenreResponse;
import com.madness.codingchallange.utils.data_object.UpComingMoviesResponse;


import org.junit.Before;
import org.junit.Test;

import retrofit2.Response;

import static junit.framework.Assert.assertNotNull;

/**
 * Test class for the web service response
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SmallTest
public class Testing  implements UpcomingMoviesContracts.UpcomingMoviesView{

    private UpcomingMoviesPresenter presenter = new UpcomingMoviesPresenter();
    private static final Integer EXAMPLE_PAGE = 1;
    private UpComingMoviesResponse movieData;
    private final Object syncObject = new Object();

    @Before
    public void setUp(){
        presenter.init(this);
        presenter.getUpcomingMovies(EXAMPLE_PAGE);
    }

    @Test
    public void validateUpComingMoviewResponse() throws InterruptedException {
        //Before asserting the object we need to wait for the web service response
        synchronized (syncObject){
            syncObject.wait();
            assertNotNull(movieData);
        }
    }


    //This is the response that comes from the server when success getting the upcoming movie list
    @Override
    public void showList(Response<UpComingMoviesResponse> response) {
        movieData = response.body();
        synchronized (syncObject){
            syncObject.notify();
        }
    }

    //This is the response from web service when it fails getting the upcoming movie list
    @Override
    public void showListFail(Throwable t) {
        movieData = null;
        synchronized (syncObject){
            syncObject.notify();
        }
    }

    /* THE FOLLOWING METHODS ARE NOT NEEDED BUT THEY COME WITH THE INTERFACE*/
    /* NOTE: These methods can be used to test the response from the other web service calls*/
    @Override
    public void storeConfigurationData(Response<ConfigurationResponse> response) {

    }

    @Override
    public void getConfigurationFail(Throwable t) {

    }

    @Override
    public void storeGenreList(Response<GenreResponse> response) {

    }

    @Override
    public void getGenreListFail(Throwable t) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}