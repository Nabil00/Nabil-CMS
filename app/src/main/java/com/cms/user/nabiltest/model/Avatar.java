package com.cms.user.nabiltest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Nabil on 1/5/2017.
 */
public class Avatar {
    @SerializedName("url")
    private String url;

    public Avatar(String url) {
        this.url = url;
    }
    public String getUrl() { return this.url; }
}
