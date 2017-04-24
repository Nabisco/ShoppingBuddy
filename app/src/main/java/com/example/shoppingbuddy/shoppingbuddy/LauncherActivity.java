package com.example.shoppingbuddy.shoppingbuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.shoppingbuddy.shoppingbuddy.R;
import com.example.shoppingbuddy.shoppingbuddy.db.ItemDbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LauncherActivity extends AppCompatActivity {
    private Button createNewListButton;
    private Button previousListButton;
    private ItemDbHelper mHelper;
    private String listID;
    private String TAG = "LauncherActivity";
    private HashMap<String, String> previousLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_buddy_menu);
        mHelper = new ItemDbHelper(this);
        //mHelper.createTables(mHelper.getReadableDatabase());
        previousLists = new HashMap<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        if(db.isOpen()) {
            Log.d(TAG, "DB is open");
        } else {
            Log.d(TAG, "DB is not open");
        }

        String maxQuery = "SELECT * FROM ShoppingList";
        Cursor cur = db.rawQuery(maxQuery, null);
        if(cur != null) {
            while (cur.moveToNext()) {
                String listID = "";
                String listDate = "";
                if (cur.getString(cur.getColumnIndex("List_ID")) != null) {
                    listID = cur.getString(cur.getColumnIndex("List_ID"));
                }
                if (cur.getString(cur.getColumnIndex("Date")) != null) {
                    listDate = cur.getString(cur.getColumnIndex("Date"));
                }
                previousLists.put(listID, listDate);
            }
        }

        //getActionBar().setTitle("ShoppingBudy");
        createNewListButton = (Button) findViewById(R.id.newListButton);
        createNewListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", 0);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        previousListButton = (Button) findViewById(R.id.previousList);
        previousListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                choseFromPreviousLists();
            }
        });

    }
    public void choseFromPreviousLists() {
        Log.d(TAG, "Chose from previous list entered");
        if(!previousLists.isEmpty()) {
            Log.d(TAG, "List is not empty");
        }
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(LauncherActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.existing_items, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Previous Lists");
                final ListView lv = (ListView) convertView.findViewById(R.id.lv);
                final ArrayList<String> itemIndexNames = new ArrayList<String>();
                Iterator it = previousLists.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    itemIndexNames.add(pair.getKey().toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(LauncherActivity.this, android.R.layout.simple_list_item_1, itemIndexNames);
                alertDialog.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        String chosen = itemIndexNames.get(position);
                        Log.d(TAG, "Position: " + chosen);
                        listID = chosen;
                        launchMainActivity();
                        Log.d(TAG, "ListID: " + listID);
                        dialog.dismiss();
                    }
                });
                alertDialog.show();

    }
    public void initComponents() {
        mHelper = new ItemDbHelper(this);
    }

    public void launchMainActivity() {
        Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
        Bundle b = new Bundle();
        b.putInt("key", 1);
        b.putString("ID", listID);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}
