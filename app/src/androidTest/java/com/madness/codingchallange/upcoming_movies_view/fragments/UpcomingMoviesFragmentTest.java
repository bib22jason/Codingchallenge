package com.madness.codingchallange.upcoming_movies_view.fragments;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.madness.codingchallange.R;
import com.madness.codingchallange.upcoming_movies_view.activities.main_user_view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class UpcomingMoviesFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateView() {
        //onView(withId(R.id.linear_container)).check(ViewAssertions.matches(isDisplayed()));
    }
}