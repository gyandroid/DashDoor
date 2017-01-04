package com.notetake.core.model;

/**
 * Created by gyradhakrishnan on 1/3/17.
 */

public class RestaurantRequest {
    public RestaurantRequest(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String latitude;
    public String longitude;
}
