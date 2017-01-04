package com.notetake.core.network;

import com.notetake.core.model.RestaurantRespModel;

import java.util.List;

/**
 * Created by gyradhakrishnan on 1/3/17.
 */

public class LoadFavoritesEvent extends BaseEvent {

    private static final String errorMessage = "Cannot load favorites at this moment.";
    public static final OnLoadingError FAILED = new OnLoadingError(errorMessage, UNHANDLED_CODE);

    public static class OnLoaded extends OnSuccess<List<RestaurantRespModel>> {
        public OnLoaded(List<RestaurantRespModel> list) {
            super(list);
        }
    }

    public static class OnLoadingStart extends OnRequest<Boolean> {
        public OnLoadingStart(Boolean alwaysTrue) {
            super(alwaysTrue);
        }
    }

    public static class OnLoadingError extends OnFailure {
        public OnLoadingError(String errorMessage, int code) {
            super(errorMessage, code);
        }
    }

}