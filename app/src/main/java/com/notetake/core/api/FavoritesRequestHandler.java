package com.notetake.core.api;

import android.content.Context;

import com.notetake.core.db.FavoritesDataSource;
import com.notetake.core.model.RestaurantRespModel;
import com.notetake.core.network.LoadFavoritesEvent;
import com.notetake.core.network.LoadRestaurantsEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by gyradhakrishnan on 1/3/17.
 */

public class FavoritesRequestHandler extends ApiRequestHandler {
    WeakReference<Context> mContext;

    public FavoritesRequestHandler(Bus bus, Context context) {
        super(bus);
        this.mContext = new WeakReference<>(context);
    }

    @Subscribe
    public void onLoadingStart(LoadFavoritesEvent.OnLoadingStart onLoadingStart) {
        try {
            FavoritesDataSource favDataSource = new FavoritesDataSource(mContext.get());
            favDataSource.open();
            List<RestaurantRespModel> allFavorites = favDataSource.getAllFavorites();
            favDataSource.close();
            mBus.post(new LoadFavoritesEvent.OnLoaded(allFavorites));
        } catch (Exception e) {
            mBus.post(LoadRestaurantsEvent.FAILED);
        }
    }
}
