package com.madness.codingchallange.upcoming_movies_view.activities.main_user_view;

import android.support.v4.app.Fragment;

/**
 * Presenter of the activity that laods the fragmnet {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesFragment}
 */
public class MainActivityPresenter implements MainActivityContracts.MainActivityPresenter {

    private MainActivityContracts.MainActivityView view;

    public MainActivityPresenter(MainActivityContracts.MainActivityView view) {
        this.view = view;
    }


    @Override
    public void loadFragment(Fragment fragment) {
        view.loadFragment(fragment);
    }
}
