package com.example.budgettracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class InputActivity extends AppCompatActivity {
    EditText dateEditText;
    EditText costEditText;
    Button selectDateButton;
    Button addButton;
    DatabaseHelper myDb;
    Spinner spinner;
    DatePickerDialog datePickerDialog;
    String[] list = {"Food and Groceries", "Home expenses", "Leisure and Entertainment"};
    int selectedItem;
    final Calendar cldr = Calendar.getInstance();
    int day = cldr.get(Calendar.DAY_OF_MONTH);
    int month = cldr.get(Calendar.MONTH);
    int year = cldr.get(Calendar.YEAR);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);


        dateEditText = findViewById(R.id.dateEditText);
        selectDateButton = findViewById(R.id.selectDateButton);
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(InputActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        addButton = findViewById(R.id.addButton);

        myDb = new DatabaseHelper(this);
        addData();

        spinner = findViewById(R.id.spinner);
        if (spinner != null) {
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_layout, list);
            spinner.setAdapter(adapter);
        }

    }

    public void addData() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = spinner.getSelectedItemPosition();
                boolean isInserted = myDb.insertData(dateEditText.getText().toString(),
                        costEditText.getText().toString());
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
