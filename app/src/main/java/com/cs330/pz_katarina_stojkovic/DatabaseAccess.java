package com.cs330.pz_katarina_stojkovic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper oHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    public DatabaseAccess(Context context) {
        this.oHelper = new DatabaseOHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance==null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = oHelper.getWritableDatabase();
    }

    public void close() {
        if(db != null) {
            this.db.close();
        }
    }

    public void addUser(String idUser, String idRole, String token, String username) {
        System.out.println("addUser");
        System.out.println(idUser);
        System.out.println(idRole);
        System.out.println(token);
        System.out.println(username);
        ContentValues insertValues = new ContentValues();
        insertValues.put("idUser", idUser);
        insertValues.put("idRole", idRole);
        insertValues.put("token", token);
        insertValues.put("username", username);
        db.insert("User",null, insertValues);
    }

    public void truncateUserTable() {
        db.execSQL("DELETE FROM User");
    }

    public String[] getUser() {
        c=db.rawQuery("select idUser, idRole, token, username from User", new String[]{});
        String[] userData= new String[4];
        userData[0] = "0";
        while (c.moveToNext()){
            userData[0] = c.getString(0);
            userData[1] = c.getString(1);
            userData[2] = c.getString(2);
            userData[3] = c.getString(3);
        }
        return userData;
    }
}
