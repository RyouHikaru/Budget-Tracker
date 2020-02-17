package com.example.budgettracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityList extends AppCompatActivity {
    ArrayList<String> aList;
    ArrayList<Integer> totalCostList;
    String totalCostText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button backButton = (Button) findViewById(R.id.backButton);

        Intent i = getIntent();
        if (i.hasExtra("LIST")) {
            aList = i.getStringArrayListExtra("LIST");
            totalCostList = i.getIntegerArrayListExtra("TOTAL_COST");

            String[] list = toStringArray(aList, aList.size());

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);

            TextView totalCostTextView = (TextView) findViewById(R.id.totalCostView);
            totalCostTextView.setText("Total Cost: " + Integer.toString(totalCost(totalCostList)));
        }
        else {
            Toast.makeText(this, "No data yet", Toast.LENGTH_SHORT).show();
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private String[] toStringArray (ArrayList<String> a, int length) {
        String[] ret = new String[length];
        for (int i = 0; i < length; i++) {
            ret[i] = a.get(i).toString();
        }
        return ret;
    }
    private int totalCost(ArrayList<Integer> a) {
        int length = a.size();
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += a.get(i);
        }
        return sum;
    }
}
