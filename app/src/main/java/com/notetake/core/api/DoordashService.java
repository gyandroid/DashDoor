package com.notetake.core.api;

import com.notetake.core.model.RestaurantRespModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by gyradhakrishnan on 1/3/17.
 */

public interface DoordashService {

    @GET(ApiSettings.RESTAURANT_URL)
    Call<List<RestaurantRespModel>> loadRestaurants(@QueryMap Map<String, String> options);
}
