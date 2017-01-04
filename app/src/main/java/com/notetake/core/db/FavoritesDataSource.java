package com.notetake.core.db;

/**
 * Created by gyradhakrishnan on 1/3/17.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.notetake.core.model.RestaurantRespModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private RestaurantDbHelper dbHelper;
    private String[] allColumns = {RestaurantDbHelper.COLUMN_ID,
            RestaurantDbHelper.COLUMN_RESTAURANT_NAME, RestaurantDbHelper.COLUMN_RESTAURANT_IMAGE};
    private String[] keyCol = {RestaurantDbHelper.COLUMN_ID};

    public FavoritesDataSource(Context context) {
        dbHelper = new RestaurantDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean createFavorite(RestaurantRespModel favorite) {
        ContentValues values = new ContentValues();
        values.put(RestaurantDbHelper.COLUMN_ID, favorite.id);
        values.put(RestaurantDbHelper.COLUMN_RESTAURANT_NAME, favorite.name);
        values.put(RestaurantDbHelper.COLUMN_RESTAURANT_IMAGE, favorite.coverImageUrl);
        long insertId = database.insert(RestaurantDbHelper.TABLE_FAVS, null,
                values);
        return insertId > 0;
    }

    public void deleteFavorite(String id) {
        System.out.println("Favorite removed with id: " + id);
        database.delete(RestaurantDbHelper.TABLE_FAVS, RestaurantDbHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Long> getFavIds() {
        List<Long> favs = new ArrayList<>();

        Cursor cursor = database.query(RestaurantDbHelper.TABLE_FAVS,
                keyCol, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            favs.add(cursor.getLong(0));
            cursor.moveToNext();
        }
        cursor.close();
        return favs;
    }

    public List<RestaurantRespModel> getAllFavorites() {
        List<RestaurantRespModel> favs = new ArrayList<>();

        Cursor cursor = database.query(RestaurantDbHelper.TABLE_FAVS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RestaurantRespModel fav = mapToFav(cursor);
            favs.add(fav);
            cursor.moveToNext();
        }
        cursor.close();
        return favs;
    }

    private RestaurantRespModel mapToFav(Cursor cursor) {
        RestaurantRespModel fav = new RestaurantRespModel();
        fav.id  = String.valueOf(cursor.getLong(0));
        fav.name = cursor.getString(1);
        fav.coverImageUrl = cursor.getString(2);
        fav.isFavorite = true;
        return fav;
    }
}