package com.example.shoppingbuddy.shoppingbuddy;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by gme50 on 2/2/2017.
 */

public class ShoppingList {

    private ArrayList<ListItem> a_shoppingList;
    private String currentDate;
    private String listID;
    private String listName;

    public ShoppingList(ArrayList<ListItem> itemList, String theDate) {

        this.a_shoppingList = itemList;
        this.setListName("");
        setCurrentDate(theDate);

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

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getListID() {
        return listID;
    }

    public void setListID(String listID) {
        this.listID = listID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
