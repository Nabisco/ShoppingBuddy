package com.example.shoppingbuddy.shoppingbuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
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
                if(x.getI_itemAisle() == 0 && x.getD_itemPrice() == 0.0) {
                    currentList.set(currentList.indexOf(x), priceAndAisleCheck(x));
                    Log.d("MainActivity", x.getS_itemName() + " Aisle: " + x.getI_itemAisle());
                    updateUI();
                } else {
                    currentList.remove(x);
                    updateUI();
                }
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

    public ListItem priceAndAisleCheck(ListItem itemToChange) {
            final ListItem updatedItem = itemToChange;
            View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(final View v, boolean hasFocus) {
                    if (hasFocus) {
                        // Must use message queue to show keyboard
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager inputMethodManager= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.showSoftInput(v, 0);
                            }
                        });
                    }
                }
            };

            final EditText editTextAisle = new EditText(this);
            editTextAisle.setHint("Aisle");
            editTextAisle.setInputType(InputType.TYPE_CLASS_NUMBER);
            editTextAisle.setFocusable(true);
            editTextAisle.setClickable(true);
            editTextAisle.setFocusableInTouchMode(true);
            editTextAisle.setSelectAllOnFocus(true);
            editTextAisle.setSingleLine(true);
            editTextAisle.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            editTextAisle.setOnFocusChangeListener(onFocusChangeListener);

            final EditText editTextPrice = new EditText(this);
            editTextPrice.setHint("Price");
            editTextPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editTextPrice.setFocusable(true);
            editTextPrice.setClickable(true);
            editTextPrice.setFocusableInTouchMode(true);
            editTextPrice.setSelectAllOnFocus(true);
            editTextPrice.setSingleLine(true);
            editTextPrice.setImeOptions(EditorInfo.IME_ACTION_DONE);
            editTextPrice.setOnFocusChangeListener(onFocusChangeListener);

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(editTextAisle);
            linearLayout.addView(editTextPrice);

            DialogInterface.OnClickListener alertDialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            int newAisle = Integer.parseInt(editTextAisle.getText().toString());
                            double newPrice = Double.parseDouble(editTextPrice.getText().toString());
                            updatedItem.setD_itemPrice(newPrice);
                            updatedItem.setI_itemAisle(newAisle);
                            updateUI();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            // Cancel button clicked
                            break;
                    }
                }
            };
            final AlertDialog alertDialog = (new AlertDialog.Builder(this)).setMessage("Please enter name and password")
                    .setView(linearLayout)
                    .setPositiveButton("Done", alertDialogClickListener)
                    .setNegativeButton("Cancel", alertDialogClickListener)
                    .create();

            editTextAisle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    editTextPrice.requestFocus(); // Press Return to focus next one
                    return false;
                }
            });
            editTextPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    // Press Return to invoke positive button on alertDialog.
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
                    return false;
                }
            });

            alertDialog.show();
            return updatedItem;
        }
    }


