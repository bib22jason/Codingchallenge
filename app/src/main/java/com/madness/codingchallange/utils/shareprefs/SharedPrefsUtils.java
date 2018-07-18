package com.madness.codingchallange.utils.shareprefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.madness.codingchallange.utils.data_object.ConfigurationResponse;
import com.madness.codingchallange.utils.data_object.GenreResponse;
import com.madness.codingchallange.utils.data_object.UpComingMoviesResponse;

public class SharedPrefsUtils {

    private static final String SHARED_FILE_NAME = "shared_data";
    private static final String MOVIES_DATA = "movie_data";
    private static final String GENRE_DATA = "genre";
    private static final String CONFIG_DATA = "config_data";

    public static void store(Context context, UpComingMoviesResponse data) {
        SharedPreferences.Editor prefsEditor = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefsEditor.putString(MOVIES_DATA, json);
        prefsEditor.apply();
    }

    public static void store(Context context, ConfigurationResponse data) {
        SharedPreferences.Editor prefsEditor = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefsEditor.putString(CONFIG_DATA, json);
        prefsEditor.apply();
    }

    public static void store(Context context, GenreResponse data) {
        SharedPreferences.Editor prefsEditor = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefsEditor.putString(GENRE_DATA, json);
        prefsEditor.apply();
    }

    public static UpComingMoviesResponse getMovieData(Context context) {
        Gson gson = new Gson();
        String json = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE).getString(MOVIES_DATA, null);

        return gson.fromJson(json, UpComingMoviesResponse.class);
    }

    public static ConfigurationResponse getConfigData(Context context){
        Gson gson = new Gson();
        String json = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE).getString(MOVIES_DATA, null);

        return gson.fromJson(json, ConfigurationResponse.class);
    }

    public static GenreResponse getGenreData(Context context){
        Gson gson = new Gson();
        String json = context.getSharedPreferences(SHARED_FILE_NAME, Context.MODE_PRIVATE).getString(MOVIES_DATA, null);

        return gson.fromJson(json, GenreResponse.class);
    }
}

