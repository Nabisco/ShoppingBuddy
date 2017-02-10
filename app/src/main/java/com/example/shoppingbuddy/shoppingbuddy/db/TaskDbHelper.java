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
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.SHOPPINGLIST + " ( " +
                TaskContract.TaskEntry.SL_COL_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.SL_COL_DATE + "CUTTENT_DATE, " +
                TaskContract.TaskEntry.SL_COL_NUMBERS + "FORIEGN KEY " +
                ");";







        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }
}