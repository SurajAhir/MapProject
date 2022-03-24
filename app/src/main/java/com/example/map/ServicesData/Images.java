package com.example.map.ServicesData;

import retrofit2.http.Url;

public class Images {
   String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Images(String url) {
        this.url = url;
    }
}
