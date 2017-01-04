package com.notetake.core.db;

/**
 * Created by gyradhakrishnan on 1/3/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RestaurantDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_FAVS = "favs";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RESTAURANT_NAME = "rest_name";
    public static final String COLUMN_RESTAURANT_IMAGE = "rest_image";


    private static final String DATABASE_NAME = "dashdoor.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_FAVS
            + "( " + COLUMN_ID + " integer primary key, "
            + COLUMN_RESTAURANT_NAME + " text not null,"
            + COLUMN_RESTAURANT_IMAGE + " text);";

    public RestaurantDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(RestaurantDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVS);
        onCreate(db);
    }

}