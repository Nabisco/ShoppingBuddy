package com.example.shoppingbuddy.shoppingbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView txtView;
    private Button newListBtn;
    private Button oldListBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sb);
        initComponents();
    }


    public void initComponents() {

        txtView = (TextView) findViewById(R.id.textView);
        newListBtn = (Button) findViewById(R.id.newListBtn);
        newListBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                txtView.setText("\nCreate New List Selected\n");
            }
        });
        oldListBtn = (Button) findViewById(R.id.oldListsBtn);
        oldListBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                txtView.setText("\nView Old List Selected\n");
                txtView.append("List 1\n");
                txtView.append("List 2\n");
                txtView.append("List 3\n");
                txtView.append("List 4\n");
                txtView.append("List 5\n");
                txtView.append("List 6\n");
            }
        });
    }



}
