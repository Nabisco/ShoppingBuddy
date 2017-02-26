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

//        String createTable = "CREATE TABLE " + ItemContract.ItemEntry.TABLE + " ( " +
//                ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                ItemContract.ItemEntry.COL_ITEM_NAME + " TEXT NOT NULL);";
//
//        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + ItemContract.ItemEntry.TABLE);
        onCreate(db);
    }

    public void createTables(SQLiteDatabase db){

        //db.execSQL("DROP TABLE IF EXISTS " + ItemContract.ItemEntry.TABLE);
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


//        String createSLTable = "CREATE TABLE " + ItemContract.ItemEntry.SHOPPINGLIST + " ( " +
//                ItemContract.ItemEntry.SL_COL_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                ItemContract.ItemEntry.SL_COL_DATE + "CURRENT_DATE, " +
//                ");";
//
//
//        String createLITable = "CREATE TABLE " + ItemContract.ItemEntry.LISTITEM + " ( " +
//                ItemContract.ItemEntry.LI_COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                ItemContract.ItemEntry.LI_COL_NAME + "NAME, " +
//                ItemContract.ItemEntry.LI_COL_AISLE + "INTEGER, " +
//                ItemContract.ItemEntry.LI_COL_PRICE + "DOUBLE" +
//                ");";
//
//
//        String createNUMTable = "CREATE TABLE " + ItemContract.ItemEntry.LINKTABLE + " ( " +
//                ItemContract.ItemEntry.SL_COL_LIST_ID + " INTEGER, " +
//                ItemContract.ItemEntry.LI_COL_ITEM_ID + " INTEGER, " +
//                "FOREIGN KEY(" + ItemContract.ItemEntry.SL_COL_LIST_ID +") REFERENCES " + ItemContract.ItemEntry.SHOPPINGLIST +
//                "(" + ItemContract.ItemEntry.SL_COL_LIST_ID+ ")" +
//                "FOREIGN KEY(" + ItemContract.ItemEntry.LT_COL_ITEM_ID +") REFERENCES " + ItemContract.ItemEntry.LISTITEM +
//                "(" + ItemContract.ItemEntry.LI_COL_ITEM_ID+ ")" +
//                ");";


/*

CREATE TABLE ShoppingList (List_ID INTEGER PRIMARY KEY AUTOINCREMENT, Date CURRENT_DATE);

CREATE TABLE ListItem (Item_ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Aisle INTEGER, Price DOUBLE);

CREATE TABLE LinkTable (List_ID INTEGER, Item_ID INTEGER,
                       FOREIGN KEY(List_ID) REFERENCES ShoppingList(List_ID),
                       FOREIGN KEY(Item_ID) REFERENCES ListItem(Item_ID));

 */