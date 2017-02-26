package com.example.shoppingbuddy.shoppingbuddy;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gme50 on 2/2/2017.
 */

public class ShoppingList {

    private ArrayList<ListItem> a_shoppingList;
    private String currentDate;

    public ShoppingList(ArrayList<ListItem> itemList, String theDate) {

        this.a_shoppingList = itemList;
        currentDate = theDate;

        Log.d("MainActivity", "Date: " + theDate);
    }

    public void addItemToList(ListItem newItem) {
        this.getH_shoppingList().add(newItem);
    }


    public ArrayList<ListItem> getH_shoppingList() {
        return a_shoppingList;
    }

    public void setH_shoppingList(ArrayList<ListItem> h_shoppingList) {
        this.a_shoppingList = h_shoppingList;
    }
}
