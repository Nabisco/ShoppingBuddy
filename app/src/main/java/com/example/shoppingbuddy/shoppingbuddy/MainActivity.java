package com.example.shoppingbuddy.shoppingbuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shoppingbuddy.shoppingbuddy.db.ItemDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";    //For debugging purposes
    private ItemDbHelper mHelper;           //Database helper class
    private ListView mTaskListView;         //ListView for main activity screen
    private ItemAdapter mAdapter;  //Adapter to add list items to listview
    ArrayList<ListItem> currentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sb);

        currentList = new ArrayList<>();
        ArrayList<ListItem> itemList = currentList;
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        mAdapter = new ItemAdapter(this, itemList);
        mTaskListView.setAdapter(mAdapter);
        mHelper = new ItemDbHelper(this);

//        SQLiteDatabase db = mHelper.getReadableDatabase();
//        mHelper.createTable(db);
//        Cursor cursor = db.query(ItemContract.ItemEntry.LISTITEM,
//                new String[]{ItemContract.ItemEntry.LI_COL_NAME},
//                null, null, null, null, null);
//
//
//        while(cursor.moveToNext()) {
//            int idx = cursor.getColumnIndex(ItemContract.ItemEntry.LI_COL_NAME);
//            Log.d(TAG, "Item: " + cursor.getString(idx));
//        }

//        cursor.close();
//        db.close();
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new item")
                        .setMessage("What do you want to add?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
//
//                                SQLiteDatabase db = mHelper.getWritableDatabase();
//                                ContentValues values = new ContentValues();
//                                values.put(ItemContract.ItemEntry.LI_COL_NAME, task);
//                                values.put(ItemContract.ItemEntry.LI_COL_AISLE, 0);
//                                values.put(ItemContract.ItemEntry.LI_COL_PRICE, 0.0);
//                                db.insertWithOnConflict(ItemContract.ItemEntry.LI_COL_NAME,
//                                        null,
//                                        values,
//                                        SQLiteDatabase.CONFLICT_REPLACE);
//                                db.close()
                                ListItem newItem = new ListItem(task);
                                Log.d(TAG, "Before adding new item. size: "  + currentList.size());
                                currentList.add(newItem);
                                Log.d(TAG, "added task to list. Size is now: " + currentList.size());
                                //Log.d(TAG, "Task to add: " + task);

                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initComponents() {
        mHelper = new ItemDbHelper(this);
    }

    private void updateUI() {
//        Log.d("MainActivity", "Updating");
//        SQLiteDatabase db = mHelper.getReadableDatabase();
//        Cursor cursor = db.query(ItemContract.ItemEntry.TABLE,
//                new String[]{ItemContract.ItemEntry._ID, ItemContract.ItemEntry.COL_ITEM_NAME},
//                null, null, null, null, null);
//        while (cursor.moveToNext()) {
//            int idx = cursor.getColumnIndex(ItemContract.ItemEntry.COL_ITEM_NAME);
//            itemList.add(cursor.getString(idx));
//        }
        ArrayList<ListItem> itemList = currentList;

        if (mAdapter == null) {
            mAdapter = new ItemAdapter(this, itemList);
            mTaskListView.setAdapter(mAdapter);
            Log.d("MainActivity", "madapter was null");

        } else {
            mAdapter.notifyDataSetChanged();
            Log.d("MainActivity", "UpdateUI add all executed");

        }
//        cursor.close();
//        db.close();
    }

    public void deleteItem(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.item_title);
        String task = String.valueOf(taskTextView.getText());
        for(ListItem x : currentList) {
            if(x.getS_itemName().equalsIgnoreCase(task)) {
                currentList.remove(x);
                break;
            }
        }
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        db.delete(ItemContract.ItemEntry.TABLE,
//                ItemContract.ItemEntry.COL_ITEM_NAME + " = ?",
//                new String[]{task});
//        db.close();
        updateUI();
    }

}
