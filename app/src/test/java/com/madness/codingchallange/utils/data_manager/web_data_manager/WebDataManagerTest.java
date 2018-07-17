package com.madness.codingchallange.utils.data_manager.web_data_manager;

import com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesPresenter;
import com.madness.codingchallange.utils.data_object.UpComingMoviesResponse;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

public class WebDataManagerTest implements UpcomingMoviesPresenter.RetrieveMovieListCallback{

    private UpcomingMoviesPresenter presenter = new UpcomingMoviesPresenter(this);
    private UpComingMoviesResponse movieData;
    private final Object syncObject = new Object();

    @Test
    public void getUpcomingMovies() {
        Integer examplePage = 1, noScrolling = 0;
        presenter.getUpcomingMovies(examplePage, noScrolling);

        //Before asserting the object we need to wait for the web service response
        synchronized (syncObject){
            try {
                syncObject.wait();
                assertNotNull(movieData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getMovieList(UpComingMoviesResponse data) {
        movieData = data;
        synchronized (syncObject){
            syncObject.notify();
        }
    }
}