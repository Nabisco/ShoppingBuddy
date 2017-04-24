package com.example.shoppingbuddy.shoppingbuddy;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.resource;

/**
 * Created by gme50 on 2/22/2017.
 */

public class ItemAdapter extends BaseAdapter {
    ArrayList<ListItem> theList;
    LayoutInflater mInflater;

    public ItemAdapter(Context c, ArrayList<ListItem> itemList) {
        this.theList =  itemList;
        mInflater = LayoutInflater.from(c);
        Log.d("MainActivity", "Custom adapter created");
    }

    @Override
    public int getCount() {
        return theList.size();
    }

    @Override
    public Object getItem(int i) {
        return theList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Log.d("MainActivity", "getView entered");

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_todo, parent, false);
        }

        ListItem listItem = theList.get(position);

        if(listItem != null) {

            // Lookup view for data population
            TextView itemName = (TextView) convertView.findViewById(R.id.item_title);
            TextView itemAisle = (TextView) convertView.findViewById(R.id.tv_aisle_val);
            TextView itemPrice = (TextView) convertView.findViewById(R.id.tv_price_val);

            // Populate the data into the template view using the data object
            itemName.setText(String.valueOf(listItem.getS_itemName()));
            itemAisle.setText(String.valueOf(listItem.getI_itemAisle()));
            itemPrice.setText(String.valueOf(listItem.getD_itemPrice()));

        } else {

        }
        return convertView;
    }
}

