package com.madness.codingchallange.movie_info_view.activities;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.madness.codingchallange.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ShowMovieInfoActivityTest {

    @Rule
    public ActivityTestRule<ShowMovieInfoActivity> activityActivityTestRule = new ActivityTestRule<>(ShowMovieInfoActivity.class);

    @Test
    public void validateView() {
        //Validate certain textview exist and write something on it
        onView(withId(R.id.over_text)).check(matches(isDisplayed()));
        onView(withId(R.id.over_text)).check(matches(withText("Overview:")));
    }

}