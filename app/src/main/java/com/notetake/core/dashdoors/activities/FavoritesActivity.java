package com.notetake.core.dashdoors.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.notetake.core.adapter.ItemsListAdapter;
import com.notetake.core.dashdoors.R;
import com.notetake.core.db.FavoritesDataSource;
import com.notetake.core.model.RestaurantRespModel;

import java.util.ArrayList;

public class FavoritesActivity extends BaseActivity {
    private RecyclerView list;
    private ArrayList<RestaurantRespModel> allFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (RecyclerView)findViewById(R.id.items_list);

        ActionBar ab = getSupportActionBar();
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.title_activity_favorites);
        ab.show();

        ItemsListAdapter adapter = new ItemsListAdapter(this, allFavorites, new ArrayList<Long>(), null, false);
        list.setAdapter(adapter);
        list.setLayoutManager(adapter.createLayoutManager());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void register() {
    }

    @Override
    protected void unRegister() {
    }

    @Override
    protected void load() {
        FavoritesDataSource favDataSource = new FavoritesDataSource(this);
        favDataSource.open();
        allFavorites = (ArrayList)favDataSource.getAllFavorites();
        favDataSource.close();
    }

}
