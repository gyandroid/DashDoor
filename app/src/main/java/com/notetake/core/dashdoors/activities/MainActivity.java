package com.notetake.core.dashdoors.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.notetake.core.adapter.ItemsListAdapter;
import com.notetake.core.dashdoors.R;
import com.notetake.core.db.FavoritesDataSource;
import com.notetake.core.model.RestaurantRequest;
import com.notetake.core.model.RestaurantRespModel;
import com.notetake.core.network.BusProvider;
import com.notetake.core.network.LoadRestaurantsEvent;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView list;
    private FavoritesDataSource favDataSource;
    private List<Long> allFavoriteIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (RecyclerView)findViewById(R.id.items_list);
        favDataSource = new FavoritesDataSource(this);
        favDataSource.open();
        allFavoriteIds = favDataSource.getFavIds();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.favorites:
                Intent homeIntent = new Intent(this, FavoritesActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    protected void onResume() {
        favDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        favDataSource.close();
        super.onPause();
    }


    @Override
    protected void load() {
        BusProvider.getInstance().post(new
                LoadRestaurantsEvent.OnLoadingStart(new RestaurantRequest("37.422740", "-122.139956")));
    }

    @Override
    protected void register() {
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void unRegister() {
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onLoadingSuccessful(LoadRestaurantsEvent.OnLoaded onLoaded) {
        ArrayList<RestaurantRespModel> response = (ArrayList)onLoaded.getResponse();
        ItemsListAdapter adapter = new ItemsListAdapter(this, response, allFavoriteIds, favDataSource, true);
        list.setAdapter(adapter);
        list.setLayoutManager(adapter.createLayoutManager());
        adapter.notifyDataSetChanged();
    }
    @Subscribe public void onLoadingFailed(LoadRestaurantsEvent.OnLoadingError onLoadingFailed) {
        Toast.makeText(this, onLoadingFailed.getErrorMessage(), Toast.LENGTH_LONG).show();
    }
}
