package com.madness.codingchallange.utils.data_object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class pojo for {@link UpComingMoviesResponse}
 */

class UpComingMoviesHelper {

    @SerializedName("maximun")
    @Expose
    private String maximun;
    @SerializedName("minimun")
    @Expose
    private String minimun;
}
