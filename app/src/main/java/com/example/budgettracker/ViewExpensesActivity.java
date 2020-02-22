package com.example.budgettracker;

import androidx.appcompat.app.AppCompatActivity;
import com.example.budgettracker.DatabaseHelper;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.Calendar;

public class ViewExpensesActivity extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    DatabaseHelper myDb;
    final Calendar cldr = Calendar.getInstance();
    int day = cldr.get(Calendar.DAY_OF_MONTH);
    int month = cldr.get(Calendar.MONTH);
    int year = cldr.get(Calendar.YEAR);
    Button selectDate;
    Button returnButton;
    TextView dateTextView;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        selectDate = findViewById(R.id.dateButton);
        returnButton = findViewById(R.id.ve_returnButton);
        dateTextView = findViewById(R.id.ve_dateTextView);
        scrollView = findViewById(R.id.ve_scrollView);
        myDb = new DatabaseHelper(this);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(ViewExpensesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateTextView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        accessData();
    }
    public void accessData() {
        Cursor r = myDb.getAllData();
        if (r.getCount() == 0) {
            System.out.println("No data");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(r.moveToNext()) {
            buffer.append("Date: " + r.getString(0) + "\n");
            buffer.append("Food expenses: " + r.getString(1) + "\n");
            buffer.append("Home expenses: " + r.getString(2) + "\n");
            buffer.append("Entertainment: " + r.getString(3) + "\n\n");
        }
        showData(buffer.toString());
    }
    public void showData(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Data");
        builder.setMessage(msg);
        AlertDialog dialog = builder.show();
        return;
    }
}
