package com.example.shoppingbuddy.shoppingbuddy.db;

/**
 * Created by gme50 on 2/7/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemDbHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public ItemDbHelper(Context context) {
        super(context, ItemContract.DB_NAME, null, ItemContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + ItemContract.ItemEntry.TABLE + " ( " +
                ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemContract.ItemEntry.COL_ITEM_NAME + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemContract.ItemEntry.TABLE);
        onCreate(db);
    }

    public void createTable(SQLiteDatabase db){

        String createTable = "CREATE TABLE " + ItemContract.ItemEntry.TABLE + " ( " +
                ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemContract.ItemEntry.COL_ITEM_NAME + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }
}

/*
String createSLTable = "CREATE TABLE " + ItemContract.ItemEntry.SHOPPINGLIST + " ( " +
                ItemContract.ItemEntry.SL_COL_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemContract.ItemEntry.SL_COL_DATE + "CURRENT_DATE, " +
                "FORIEGN KEY(" + ItemContract.ItemEntry.SL_COL_NUMBERS +") REFERENCES " + ItemContract.ItemEntry.NUMBERS +
                "(" + ItemContract.ItemEntry.N_COL_NUM_ID + ")" +
                ");";


        String createLITable = "CREATE TABLE " + ItemContract.ItemEntry.LISTITEM + " ( " +
                ItemContract.ItemEntry.LI_COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemContract.ItemEntry.LI_COL_AISLE + "INTEGER, " +
                ItemContract.ItemEntry.LI_COL_PRICE + "DOUBLE" +
                ");";


        String createNUMTable = "CREATE TABLE " + ItemContract.ItemEntry.SHOPPINGLIST + " ( " +
                ItemContract.ItemEntry.SL_COL_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemContract.ItemEntry.SL_COL_DATE + "CURRENT_DATE, " +
                ItemContract.ItemEntry.SL_COL_NUMBERS + "FORIEGN KEY " +
                ");";
 */