package com.cs330.pz_katarina_stojkovic;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "foodOrder.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
