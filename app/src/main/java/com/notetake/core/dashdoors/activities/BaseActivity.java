package com.notetake.core.dashdoors.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataManager();
    }

    protected void initDataManager() {
        load();
        register();
    }

    @Override protected void onStop() {
        super.onStop();
        unRegister();
    }

    protected abstract void register();
    protected abstract void load();
    protected abstract void unRegister();

}
