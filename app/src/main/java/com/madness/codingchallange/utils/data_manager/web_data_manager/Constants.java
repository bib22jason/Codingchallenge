package com.madness.codingchallange.utils.data_manager.web_data_manager;

/**
 * Class used a constants for web data manager
 */
public class Constants {

    //API KEY and base URL for movie API
    public static final String API_KEY = "api_key=1f54bd990f1cdfb230adb312546d765d";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    //Constants for getting upcoming movies
    public static final String MOVIES_TYPE = "movie/";
    public static final String UPCOMING_MOVIES = "upcoming?";
    public static final String MOVIE_LANGUAGE = "&language=";
    public static final String EN_US_LANG = "en-US";
    public static final String QUERY_PAGE = "&page=";

    //Constants for getting configuration data
    public static final String CONFIGURATION = "configuration?";

    //Constants for getting the list of genres
    public static final String GENRES_TYPE = "genre/";
    public static final String GENRES = "list?";

}
