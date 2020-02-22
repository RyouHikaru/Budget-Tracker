package com.example.budgettracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    Button viewExpenses;
    DatabaseHelper myDb;
    Spinner spinner;
    DatePickerDialog datePickerDialog;
    String[] list = {"", "Food and Groceries", "Home expenses", "Entertainment"};
    String typeOfExpense;
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
        costEditText = findViewById(R.id.costEditText);
        selectDateButton = findViewById(R.id.selectDateButton);
        addButton = findViewById(R.id.addContentButton);
        viewExpenses = findViewById(R.id.viewExpensesButton);
        myDb = new DatabaseHelper(this);
        spinner = findViewById(R.id.spinner);

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

        if (spinner != null) {
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_layout, list);
            spinner.setAdapter(adapter);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeOfExpense = parent.getSelectedItem().toString();
                if (typeOfExpense.isEmpty())
                    Toast.makeText(InputActivity.this, "No Item Selected", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(InputActivity.this, typeOfExpense, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertAdd();
            }
        });
        viewExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void alertAdd() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add");
        alertDialogBuilder.setMessage("Confirm to add these values?");

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String date = dateEditText.getText().toString();
                    if (date.isEmpty()) {
                        throw new NoDateInputException();
                    }
                    if (typeOfExpense.isEmpty()) {
                        Toast.makeText(InputActivity.this, "Please select type of expense", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int cost = Integer.parseInt(costEditText.getText().toString());
                    boolean isInserted = myDb.insertData(date, typeOfExpense, cost);

                    if (isInserted == true) {
                        Toast.makeText(InputActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        costEditText.setText("");
                    }
                    else {
                        Toast.makeText(InputActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(NumberFormatException nfe) {
                    Toast.makeText(InputActivity.this, "Please enter cost", Toast.LENGTH_SHORT).show();
                }
                catch(NoDateInputException nie) {
                    Toast.makeText(InputActivity.this, nie.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return;
            }
        });
        alertDialogBuilder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
