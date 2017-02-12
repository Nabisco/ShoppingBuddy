package com.example.shoppingbuddy.shoppingbuddy.db;

/**
 * Created by gme50 on 2/7/2017.
 */

import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.aziflaj.todolist.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {

        public static final String SHOPPINGLIST = "ShoppingList";
        public static final String SL_COL_LIST_ID = "List_ID";
        public static final String SL_COL_DATE = "Date";
        public static final String SL_COL_NUMBERS = "Numbers_ID";

        public static final String NUMBERS = "Numbers";
        public static final String N_COL_NUM_ID = "Numbers_ID";
        public static final String N_COL_LIST_ID = "List ID";
        public static final String SL_COL_ITEM_ID = "Item_ID";

        public static final String LISTITEM = "ListItem";
        public static final String LI_COL_ITEM_ID = "Item_ID";
        public static final String LI_COL_NAME = "Name";
        public static final String LI_COL_AISLE = "Aisle";
        public static final String LI_COL_PRICE = "Price";

        public static final String TABLE = "tasks";
        public static final String COL_TASK_TITLE = "title";


    }
}