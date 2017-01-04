package com.notetake.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gyradhakrishnan on 1/3/17.
 */

public class RestaurantRespModel {

    public String id;
    public String name;
    @SerializedName("cover_img_url")
    public String coverImageUrl;
    public String status;
    public boolean isFavorite;
}
