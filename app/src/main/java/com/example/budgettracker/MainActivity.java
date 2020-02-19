package com.example.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String pStr;
    String cStr;
    String combi;
    ArrayList<String> aList = new ArrayList<String>();
    ArrayList<Integer> totalCost = new ArrayList<Integer>();
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText purposeEditText = (EditText) findViewById(R.id.purposeEditView);
        final EditText costEditText = (EditText) findViewById(R.id.costEditView);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button viewButton = (Button) findViewById(R.id.viewButton);

        myDb = new DatabaseHelper(this);

        final Intent i = new Intent(getApplicationContext(), ActivityList.class);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pStr = purposeEditText.getText().toString();
                cStr = costEditText.getText().toString();

                if (pStr.isEmpty() || cStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill up both before adding", Toast.LENGTH_SHORT).show();
                }
                else {
                    combi = pStr + " - " + cStr;

                    aList.add(combi);
                    totalCost.add(Integer.parseInt(cStr));

                    Toast.makeText(MainActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();

                    i.putStringArrayListExtra("LIST", aList);
                    i.putIntegerArrayListExtra("TOTAL_COST", totalCost);

                    purposeEditText.setText("");
                    costEditText.setText("");
                }
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
//                finish();
            }
        });
    }
}
