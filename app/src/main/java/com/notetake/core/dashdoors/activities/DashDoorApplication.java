package com.notetake.core.dashdoors.activities;

import android.app.Application;

import com.notetake.core.api.ApiRequestHandler;
import com.notetake.core.api.FavoritesRequestHandler;
import com.notetake.core.api.RestaurantRequestHandler;
import com.notetake.core.network.BusProvider;
import com.squareup.otto.Bus;

public class DashDoorApplication extends Application {

    private static DashDoorApplication instance;

    public static DashDoorApplication getInstance() {
        return instance;
    }
    private static ApiRequestHandler mApiRequestHandler;
    private static ApiRequestHandler mApiFavRequestHandler;
    private Bus mBus = BusProvider.getInstance();

    @Override public void onCreate() {
        super.onCreate();
        instance = this;
        mApiRequestHandler = new RestaurantRequestHandler(mBus);
        mApiFavRequestHandler = new FavoritesRequestHandler(mBus, getApplicationContext());
        registerRestaurants();
        registerFavorites();
    }

    public void registerRestaurants() {
        mBus.register(mApiRequestHandler);
    }

    public void registerFavorites() {
        mBus.register(mApiFavRequestHandler);
    }

    public void unregisterRestaurants() {
        mBus.unregister(mApiRequestHandler);
    }

    public void unregisterFavorites() {
        mBus.unregister(mApiFavRequestHandler);
    }

}