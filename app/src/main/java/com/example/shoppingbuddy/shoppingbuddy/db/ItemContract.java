package com.example.shoppingbuddy.shoppingbuddy.db;

/**
 * Created by gme50 on 2/7/2017.
 */

import android.provider.BaseColumns;

public class ItemContract {
    public static final String DB_NAME = "com.aziflaj.todolist.db";
    public static final int DB_VERSION = 1;

    public class ItemEntry implements BaseColumns {

        //TODO implement shopping list table
        public static final String SHOPPINGLIST = "ShoppingList";
        public static final String SL_COL_LIST_ID = "List_ID";
        public static final String SL_COL_DATE = "Date";
        public static final String SL_COL_NUMBERS = "Numbers_ID";

        //TODO implement link table
        public static final String NUMBERS = "Numbers";
        public static final String N_COL_NUM_ID = "Numbers_ID";
        public static final String N_COL_LIST_ID = "List ID";
        public static final String SL_COL_ITEM_ID = "Item_ID";

        //TODO implement full list item table
        public static final String LISTITEM = "ListItem";
        public static final String LI_COL_ITEM_ID = "Item_ID";
        public static final String LI_COL_NAME = "Name";
        public static final String LI_COL_AISLE = "Aisle";
        public static final String LI_COL_PRICE = "Price";

        //Code for temp table used to store data for sprint 1
        //Will implement the full datat structure durring next sprint
        //This is all that is required to test functioality and complete sprint 1
        public static final String TABLE = "List";
        public static final String COL_ITEM_NAME = "Name";


    }
}