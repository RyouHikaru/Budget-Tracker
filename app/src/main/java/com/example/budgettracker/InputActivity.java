package com.example.budgettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {
    TextView dateTextView;
    TextView foodTsxtView;
    Button button;
    DatabaseHelper myDb;
    Spinner spinner;
    String[] list = {"Food and Groceries", "Home expenses", "Leisure and Entertainment"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        dateTextView = findViewById(R.id.dateTextView);
        foodTsxtView = findViewById(R.id.foodTextView);
        button = findViewById(R.id.button);
        myDb = new DatabaseHelper(this);
        addData();

        spinner = findViewById(R.id.spinner);
        if (spinner != null) {
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_layout, list);
            spinner.setAdapter(adapter);
        }

    }

    public void addData() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(dateTextView.getText().toString(),
                        foodTsxtView.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(InputActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(InputActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
