package com.example.shoppingbuddy.shoppingbuddy;

/**
 * Created by gme50 on 2/2/2017.
 */

public class ListItem {

    private String s_itemName;
    private double d_itemPrice;
    private int i_itemAisle;


    public ListItem(String itemName) {

        this.s_itemName = itemName;
        this.d_itemPrice = 0.00;
        this.i_itemAisle = 0;

    }

    public String getS_itemName() {
        return s_itemName;
    }

    public void setS_itemName(String s_itemName) {
        this.s_itemName = s_itemName;
    }

    public double getD_itemPrice() {
        return d_itemPrice;
    }

    public void setD_itemPrice(double d_itemPrice) {
        this.d_itemPrice = d_itemPrice;
    }

    public int getI_itemAisle() {
        return i_itemAisle;
    }

    public void setI_itemAisle(int i_itemAisle) {
        this.i_itemAisle = i_itemAisle;
    }
}
