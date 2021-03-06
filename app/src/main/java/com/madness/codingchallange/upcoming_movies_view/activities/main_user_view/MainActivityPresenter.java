package com.madness.codingchallange.upcoming_movies_view.activities.main_user_view;

import android.support.v4.app.Fragment;

/**
 * Presenter of the activity {@link MainActivity}
 */
public class MainActivityPresenter implements MainActivityContracts.MainActivityPresenter {

    private MainActivityContracts.MainActivityView view;

    MainActivityPresenter(MainActivityContracts.MainActivityView view) {
        this.view = view;
    }


    @Override
    public void loadFragment(Fragment fragment) {
        view.loadFragment(fragment);
    }
}
