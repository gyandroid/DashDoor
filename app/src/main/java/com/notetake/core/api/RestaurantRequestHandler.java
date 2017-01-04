package com.notetake.core.api;

import com.notetake.core.model.RestaurantRespModel;
import com.notetake.core.network.LoadRestaurantsEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gyradhakrishnan on 1/3/17.
 */

public class RestaurantRequestHandler extends ApiRequestHandler {
    private DoordashService doordashService;
    public RestaurantRequestHandler(Bus bus) {
        super(bus);
        doordashService = Client.createService(DoordashService.class);
    }

    @Subscribe
    public void onLoadingStart(LoadRestaurantsEvent.OnLoadingStart onLoadingStart){
        Map<String, String> options = new HashMap<>();
        options.put(ApiSettings.LAT, onLoadingStart.getRequest().latitude);
        options.put(ApiSettings.LONG, onLoadingStart.getRequest().longitude);
        doordashService
                .loadRestaurants(options)
                .enqueue(new Callback<List<RestaurantRespModel>>() {

                    @Override
                    public void onResponse(Call<List<RestaurantRespModel>> call, final Response<List<RestaurantRespModel>> response) {
                        if (response.isSuccessful()) {
                            mBus.post(new LoadRestaurantsEvent.OnLoaded(response.body()));
                        } else {
                            int statusCode = response.code();
                            ResponseBody errorBody = response.errorBody();
                            try {
                                mBus.post(new LoadRestaurantsEvent.OnLoadingError(errorBody.string(), statusCode));
                            } catch (IOException e) {
                                mBus.post(LoadRestaurantsEvent.FAILED);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RestaurantRespModel>> call, Throwable error) {
                        /*if (error != null && error.getMessage() != null) {
                            mBus.post(new LoadRestaurantsEvent.OnLoadingError(error.getMessage(), -1));
                        } else {*/
                            mBus.post(LoadRestaurantsEvent.FAILED);
                       /* }*/
                    }
                });
    }
}
