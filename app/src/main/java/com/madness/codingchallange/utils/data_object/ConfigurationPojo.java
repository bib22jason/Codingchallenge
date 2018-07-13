package com.madness.codingchallange.utils.data_object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Pojo class for {@link ConfigurationResponse})
 */

public class ConfigurationPojo implements Serializable {

    @SerializedName("base_url")
    @Expose
    private String base_url;
    @SerializedName("secure_base_url")
    @Expose
    private String secure_base_url;
    @SerializedName("backdrop_sizes")
    @Expose
    private String backdrop_sizes[];
    @SerializedName("logo_sizes")
    @Expose
    private String logo_sizes[];
    @SerializedName("poster_sizes")
    @Expose
    private String poster_sizes[];
    @SerializedName("profile_sizes")
    @Expose
    private String profile_sizes[];
    @SerializedName("stil_sizes")
    @Expose
    private String stil_sizes[];

    public String getBase_url() {
        return base_url;
    }

    public String getSecure_base_url() {
        return secure_base_url;
    }

    public String[] getBackdrop_sizes() {
        return backdrop_sizes;
    }

    public String[] getLogo_sizes() {
        return logo_sizes;
    }

    public String[] getPoster_sizes() {
        return poster_sizes;
    }

    public String[] getProfile_sizes() {
        return profile_sizes;
    }

    public String[] getStil_sizes() {
        return stil_sizes;
    }
}
