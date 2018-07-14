package com.madness.codingchallange.upcoming_movies_view.activities.main_user_view;

import android.support.v4.app.Fragment;

/**
 * This Interface holds the contract list for the presenter and the view in the following Activity {@link MainActivity}
 */
public interface MainActivityContracts {

    interface MainActivityPresenter {
        void loadFragment(Fragment fragment);

    }

    interface MainActivityView {
        void loadFragment(Fragment fragment);
    }
}
