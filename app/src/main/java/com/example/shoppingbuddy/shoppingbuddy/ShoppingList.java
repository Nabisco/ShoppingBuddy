package com.example.shoppingbuddy.shoppingbuddy;

import java.util.ArrayList;

/**
 * Created by gme50 on 2/2/2017.
 */

public class ShoppingList {

    private ArrayList<ListItem> a_shoppingList;

    public ShoppingList() {
        this.a_shoppingList = new ArrayList<>();
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
