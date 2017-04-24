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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void createTables(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + ItemContract.ItemEntry.SHOPPINGLIST);
        db.execSQL("DROP TABLE IF EXISTS " + ItemContract.ItemEntry.LISTITEM);
        db.execSQL("DROP TABLE IF EXISTS " + ItemContract.ItemEntry.LINKTABLE);

        String createSLTable = "CREATE TABLE " + ItemContract.ItemEntry.SHOPPINGLIST + " ( " +
                ItemContract.ItemEntry.SL_COL_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemContract.ItemEntry.SL_COL_DATE + " TEXT" +
                ");";

        String createLITable = "CREATE TABLE " + ItemContract.ItemEntry.LISTITEM + " ( " +
                ItemContract.ItemEntry.LI_COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemContract.ItemEntry.LI_COL_NAME + " TEXT, " +
                ItemContract.ItemEntry.LI_COL_AISLE + " INTEGER, " +
                ItemContract.ItemEntry.LI_COL_PRICE + " DOUBLE" +
                ");";

        String createLTTable = "CREATE TABLE " + ItemContract.ItemEntry.LINKTABLE + " ( " +
                ItemContract.ItemEntry.SL_COL_LIST_ID + " INTEGER, " +
                ItemContract.ItemEntry.LI_COL_ITEM_ID + " INTEGER, " +
                "FOREIGN KEY(" + ItemContract.ItemEntry.SL_COL_LIST_ID +") REFERENCES " + ItemContract.ItemEntry.SHOPPINGLIST +
                "(" + ItemContract.ItemEntry.SL_COL_LIST_ID+ "), " +
                "FOREIGN KEY(" + ItemContract.ItemEntry.LT_COL_ITEM_ID +") REFERENCES " + ItemContract.ItemEntry.LISTITEM +
                "(" + ItemContract.ItemEntry.LI_COL_ITEM_ID+ ")" +
                ");";
        db.execSQL(createSLTable);
        db.execSQL(createLITable);
        db.execSQL(createLTTable);
    }
}
