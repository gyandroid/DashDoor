package com.notetake.core.api;

import com.squareup.otto.Bus;

public class ApiRequestHandler {

    protected Bus mBus;

    public ApiRequestHandler(Bus bus) {
        mBus = bus;
    }

}
