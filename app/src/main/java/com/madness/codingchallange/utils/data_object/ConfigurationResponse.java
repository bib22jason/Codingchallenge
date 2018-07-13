package com.madness.codingchallange.utils.data_object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Web services response for  {@link com.madness.codingchallange.utils.data_manager.web_data_manager.WebDataManager #getConfiguration}
 * used in {@link com.madness.codingchallange.upcoming_movies_view.fragments.UpcomingMoviesFragment}
 */

public class ConfigurationResponse implements Serializable{

    @SerializedName("images")
    @Expose
    private ConfigurationPojo images;

    @SerializedName("change_keys")
    @Expose
    private String[] change_keys;

    public ConfigurationPojo getImages() {
        return images;
    }

    public String[] getChange_keys() {
        return change_keys;
    }
}
