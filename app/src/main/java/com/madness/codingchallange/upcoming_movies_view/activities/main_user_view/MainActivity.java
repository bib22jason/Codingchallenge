package com.madness.codingchallange.upcoming_movies_view.activities.main_user_view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.madness.codingchallange.R;
import com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesFragment;

/**
 * This activity holds the fragment that contains the list of the upcoming movies
 */
public class MainActivity extends AppCompatActivity implements MainActivityContracts.MainActivityView {

    private MainActivityPresenter presenter = new MainActivityPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null) {
            //empty so the fragment can recover state after rotating device
        } else {
            UpcomingMoviesFragment fragment = UpcomingMoviesFragment.newInstance();
            presenter.loadFragment(fragment);
        }
    }

    /**
     * Destroy activity on back pressed, no needed for anything else
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Method that loads the fragment in the container
     * @param fragment to be load
     */
    @Override
    public void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
