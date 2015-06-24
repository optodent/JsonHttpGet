package com.example.ivelinrusev.jsonhttpget.dataBaseComponents;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ivelin.rusev on 6/24/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jokes.db";
    public static final String TABLE_NAME = "jokes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_JOKE = "joke";
    public static final String COLUMN_CATEGORY = "category";

    public SQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table " + TABLE_NAME + "("+
                        COLUMN_ID + " integer primary key autoincrement, " +
                        COLUMN_JOKE + " text not null, " +
                        COLUMN_CATEGORY +" text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
