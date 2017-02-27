package com.example.shoppingbuddy.shoppingbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.shoppingbuddy.shoppingbuddy.R;

public class LauncherActivity extends AppCompatActivity {
    private Button createNewListButton;
    private Button previousListButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_buddy_menu);
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
                Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
                Bundle b = new Bundle();
                b.putInt("key", 1);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

    }

}

//    Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
//    startActivity(intent);
