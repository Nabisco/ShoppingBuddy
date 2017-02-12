package com.example.shoppingbuddy.shoppingbuddy.db;

/**
 * Created by gme50 on 2/7/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    public TaskDbHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }
}

/*
String createSLTable = "CREATE TABLE " + TaskContract.TaskEntry.SHOPPINGLIST + " ( " +
                TaskContract.TaskEntry.SL_COL_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.SL_COL_DATE + "CURRENT_DATE, " +
                "FORIEGN KEY(" + TaskContract.TaskEntry.SL_COL_NUMBERS +") REFERENCES " + TaskContract.TaskEntry.NUMBERS +
                "(" + TaskContract.TaskEntry.N_COL_NUM_ID + ")" +
                ");";


        String createLITable = "CREATE TABLE " + TaskContract.TaskEntry.LISTITEM + " ( " +
                TaskContract.TaskEntry.LI_COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.LI_COL_AISLE + "INTEGER, " +
                TaskContract.TaskEntry.LI_COL_PRICE + "DOUBLE" +
                ");";


        String createNUMTable = "CREATE TABLE " + TaskContract.TaskEntry.SHOPPINGLIST + " ( " +
                TaskContract.TaskEntry.SL_COL_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.SL_COL_DATE + "CURRENT_DATE, " +
                TaskContract.TaskEntry.SL_COL_NUMBERS + "FORIEGN KEY " +
                ");";
 */