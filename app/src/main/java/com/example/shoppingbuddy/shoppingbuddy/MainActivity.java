package com.example.shoppingbuddy.shoppingbuddy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingbuddy.shoppingbuddy.db.ItemDbHelper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";    //For debugging purposes
    private ItemDbHelper mHelper;           //Database helper class
    private ListView mTaskListView;         //ListView for main activity screen
    private ItemAdapter mAdapter;  //Adapter to add list items to listview
    ArrayList<ListItem> currentItemList;
    HashMap<String, String[]> itemIndexFromDB;
    int theListID;
    String date;
    double cartTotal;
    DecimalFormat format;

    ShoppingList theShoppingList;
    ArrayList<String> itemIDList;

    int listTypeEnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sb);

        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null) {
            value = b.getInt("key");
        }
        itemIDList = new ArrayList<>();
        currentItemList = new ArrayList<>();
        ArrayList<ListItem> itemList = currentItemList;
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        mAdapter = new ItemAdapter(this, itemList);
        mTaskListView.setAdapter(mAdapter);
        mHelper = new ItemDbHelper(this);
        //mHelper.createTables();
        itemIndexFromDB = new HashMap<>();
        theListID = 0;
        cartTotal = 0.0;
        format = new DecimalFormat("#.00");
        date = "";
        mTaskListView.setLongClickable(true);
        mTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                priceAndAisleCheck(currentItemList.get(i));
                return true;
            }
        });


        switch(value) {
            case 0:
                createNewList();
                break;
            case 1:
                theListID = Integer.parseInt(b.getString("ID"));
                displayPreviousList();
                break;
            default:
                break;
        }
        listTypeEnum = value;
        Log.d(TAG, "List enum: " + listTypeEnum);

    }

    private void displayPreviousList() {

        SQLiteDatabase db = mHelper.getReadableDatabase();

        String listID = "";

//        String maxQuery = "SELECT List_ID FROM ShoppingList";
//        Cursor cursor = db.rawQuery(maxQuery, null);
//        while (cursor.moveToNext()) {
//            listID = cursor.getString(cursor.getColumnIndex("List_ID"));
//            if(Integer.parseInt(listID) >= theListID) {
//                theListID = Integer.parseInt(listID);
//                Log.d(TAG, "Shopping List ID: " + listID);
//            }
//        }
//        Log.d(TAG, "Shopping List ID: " + listID);
//        cursor.close();

        String dateQuery = "SELECT Date FROM ShoppingList WHERE List_ID = \"" + theListID + "\"";
        Cursor cue = db.rawQuery(dateQuery, null);
        while (cue.moveToNext()) {
            String theDate = cue.getString(cue.getColumnIndex("Date"));
            date = theDate;
        }
        theShoppingList = new ShoppingList(currentItemList, date);
        theShoppingList.setListID(date);
        cue.close();

        String query = "SELECT Name, Aisle, Price FROM ListItem LEFT JOIN LinkTable ON ListItem.Item_ID = LinkTable.Item_ID " +
                "WHERE LinkTable.List_ID = \"" + theListID + "\"";
        Cursor cur = db.rawQuery(query, null);
        while(cur.moveToNext()) {

            String name = cur.getString(cur.getColumnIndex("Name"));
            String aisle = cur.getString(cur.getColumnIndex("Aisle"));
            String price = cur.getString(cur.getColumnIndex("Price"));
            ListItem li = new ListItem(name);
            li.setI_itemAisle(Integer.parseInt(aisle));
            li.setD_itemPrice(Double.parseDouble(price));
            currentItemList.add(li);
        }
        cur.close();


        Log.d(TAG, "Current item list size: " + currentItemList.size());


        query = "SELECT Name, Aisle, Price FROM ListItem";
        Cursor c = db.rawQuery(query, null);
        while(c.moveToNext()) {
            //Log.d("MainActivity", "MoveToNext entered");
            String name = c.getString(c.getColumnIndex("Name"));
            String aisle = c.getString(c.getColumnIndex("Aisle"));
            String price = c.getString(c.getColumnIndex("Price"));
            String[] values = {aisle, price};
            itemIndexFromDB.put(name, values);
        }

        db.close();
        updateUI();
    }

    private void createNewList() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(cal.getTime());

        theShoppingList = new ShoppingList(currentItemList, formattedDate);

        SQLiteDatabase db = mHelper.getReadableDatabase();
        //mHelper.createTables(db);

        ContentValues cv1 = new ContentValues();

        cv1.put("Date", theShoppingList.getCurrentDate());

        db.insert("ShoppingList", null, cv1);
        String query = "SELECT List_ID FROM ShoppingList WHERE Date = \"" + theShoppingList.getCurrentDate() + "\"";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String listID = cursor.getString(cursor.getColumnIndex("List_ID"));
            theShoppingList.setListID(listID);
            Log.d(TAG, "Shopping List ID: " + listID);
        }
        cursor.close();

        query = "SELECT Name, Aisle, Price FROM ListItem";
        Cursor c = db.rawQuery(query, null);
        while(c.moveToNext()) {
            //Log.d("MainActivity", "MoveToNext entered");
            String name = c.getString(c.getColumnIndex("Name"));
            String aisle = c.getString(c.getColumnIndex("Aisle"));
            String price = c.getString(c.getColumnIndex("Price"));
            String[] values = {aisle, price};
            itemIndexFromDB.put(name, values);
        }
        Iterator it = itemIndexFromDB.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Log.d(TAG, pair.getKey().toString());

        }
        db.close();
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
                                ListItem newItem = new ListItem(task);
                                Log.d(TAG, "Before adding new item. size: "  + currentItemList.size());
                                currentItemList.add(newItem);
                                Log.d(TAG, "added task to list. Size is now: " + currentItemList.size());
                                //Log.d(TAG, "Task to add: " + task);

                                updateUI();
                            }
                        })
                        .setNeutralButton("Previously Indexed Items",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                                LayoutInflater inflater = getLayoutInflater();
                                View convertView = (View) inflater.inflate(R.layout.existing_items, null);
                                alertDialog.setView(convertView);
                                alertDialog.setTitle("Previous Items");
                                ListView lv = (ListView) convertView.findViewById(R.id.lv);
                                final ArrayList<String> itemIndexNames = new ArrayList<String>();
                                Iterator it = itemIndexFromDB.entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry pair = (Map.Entry)it.next();
                                    itemIndexNames.add(pair.getKey().toString());
                                    Log.d(TAG, pair.getKey().toString());

                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, itemIndexNames);
                                alertDialog.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int position)
                                    {
                                        String chosen = itemIndexNames.get(position);
                                        if(itemIndexFromDB.keySet().contains(chosen)) {
                                            String[] vals = itemIndexFromDB.get(chosen);
                                            Double li_Price = Double.parseDouble(vals[1]);
                                            int li_Aisle = Integer.parseInt(vals[0]);
                                            Log.d(TAG, "Adding: " + chosen + ", " + li_Aisle + ", " + li_Price );
                                            ListItem li = new ListItem(chosen);
                                            li.setI_itemAisle(li_Aisle);
                                            li.setD_itemPrice(li_Price);
                                            currentItemList.add(li);
                                            updateUI();

                                        }
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                            }
                        })
                .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
            case R.id.save_item:
                saveShoppingListToDB();
                return true;
            case R.id.cart:
                printCartTotal();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveShoppingListToDB() {

        SQLiteDatabase db = mHelper.getReadableDatabase();
        //First check to see if the items in the new list are already in the database
//        if(listTypeEnum == 1) {
//            Log.d(TAG, "Delete entered");
//            String removequery = "DELETE FROM LinkTable " +
//                    "WHERE List_ID in(" + theListID + ")";
//            db.execSQL(removequery);
//        }
        for(ListItem li : currentItemList) {
            if(itemIndexFromDB.keySet().contains(li.getS_itemName())) {
                if(li.getD_itemPrice() == 0.0 && li.getI_itemAisle() == 0) {
                    break;
                } else {
                    Log.d(TAG, "Updating price and aisle for " + li.getS_itemName());
                    Cursor c = db.rawQuery("SELECT Name, Price, Item_ID FROM ListItem WHERE Name =  \"" + li.getS_itemName() + "\"", null);
                    while(c.moveToNext()) {
                        String itemID = c.getString(c.getColumnIndex("Item_ID"));
                        String query = "UPDATE ListItem SET Aisle = " + li.getI_itemAisle() + ", Price = " + Double.toString(li.getD_itemPrice()) + " WHERE Item_ID =  \"" + itemID + "\"";
                        db.rawQuery(query, null);
                    }
                    c.close();
                }

            } else {
                ContentValues cv1 = new ContentValues();
                cv1.put("Name", li.getS_itemName());
                cv1.put("Aisle", li.getI_itemAisle());
                cv1.put("Price", li.getD_itemPrice());
                db.insert("ListItem", null, cv1);
                Log.d("MainActivity", "Inserted: " + li.getS_itemName() + " into ListItem database");
            }

            Cursor c = db.rawQuery("SELECT Item_ID, Name FROM ListItem", null);
            while(c.moveToNext()) {
                String name = c.getString(c.getColumnIndex("Name"));
                if(name.equalsIgnoreCase(li.getS_itemName())) {
                    String itemID = c.getString(c.getColumnIndex("Item_ID"));
                    Log.d(TAG, "Item ID in query: " + itemID);
                    itemIDList.add(itemID);
                }
            }
        }

        Log.d(TAG, "Populating link table, array size = " + itemIDList.size());
        for(String s : itemIDList) {
            ContentValues cv = new ContentValues();
            cv.put("List_ID", theShoppingList.getListID());
            cv.put("Item_ID", s);
            db.insert("LinkTable", null, cv);
            Log.d(TAG, "List ID: " + theShoppingList.getListID() + " Item ID: " + s + " added to list");
        }

        String query = "SELECT Name, Aisle, Price FROM ListItem";
        Cursor c = db.rawQuery(query, null);
        itemIndexFromDB.clear();
        while(c.moveToNext()) {
            //Log.d("MainActivity", "MoveToNext entered");
            String name = c.getString(c.getColumnIndex("Name"));
            String aisle = c.getString(c.getColumnIndex("Aisle"));
            String price = c.getString(c.getColumnIndex("Price"));
            String[] values = {aisle, price};
            itemIndexFromDB.put(name, values);
        }
        db.close();
        Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_LONG).show();
    }

    public void initComponents() {
        mHelper = new ItemDbHelper(this);
    }

    private void updateUI() {

        Log.d(TAG, "Current item list size in updateUI: " + currentItemList.size());
        ArrayList<ListItem> itemList = currentItemList;
        theShoppingList.setH_shoppingList(currentItemList);

        if (mAdapter == null) {
            mAdapter = new ItemAdapter(this, itemList);
            mTaskListView.setAdapter(mAdapter);
            Log.d("MainActivity", "madapter was null");
        } else {
            mAdapter.notifyDataSetChanged();
            Log.d("MainActivity", "UpdateUI add all executed");
        }
    }

    public void deleteItem(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.item_title);
        String task = String.valueOf(taskTextView.getText());
        for(ListItem x : currentItemList) {
            if(x.getS_itemName().equalsIgnoreCase(task)) {
                if(x.getI_itemAisle() == 0 && x.getD_itemPrice() == 0.0) {
                    priceAndAisleCheck(x);
                    Log.d("MainActivity", x.getS_itemName() + " Aisle: " + x.getI_itemAisle());

                } else {
                    cartTotal += x.getD_itemPrice();
                    Log.d(TAG,"Cart total: " + cartTotal);
                    printCartTotal();
                    currentItemList.remove(x);
                    updateUI();
                }
                break;
            }
        }

        updateUI();
    }

    private void printCartTotal() {
        Toast.makeText(getApplicationContext(), "Cart Total: $" + format.format(cartTotal), Toast.LENGTH_LONG).show();
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
            final AlertDialog alertDialog = (new AlertDialog.Builder(this)).setMessage("Please enter the aisle and price:")
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


