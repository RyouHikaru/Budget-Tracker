package com.example.budgettracker;

import androidx.appcompat.app.AppCompatActivity;
import com.example.budgettracker.DatabaseHelper;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.Calendar;
import java.util.LinkedList;

public class ViewExpensesActivity extends AppCompatActivity {
    private String[] column = {"Food: ", "Housing: ", "Transportation: ",
            "Utilities: ", "Insurance: ", "Health: ", "Debt and Investment: ",
            "Entertainment: "};
    DatePickerDialog datePickerDialog;
    DatabaseHelper myDb;
    final Calendar cldr = Calendar.getInstance();
    int day = cldr.get(Calendar.DAY_OF_MONTH);
    int month = cldr.get(Calendar.MONTH);
    int year = cldr.get(Calendar.YEAR);
    Button selectDate;
    Button returnButton;
    TextView dateTextView;
    ListView listView;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        dateTextView = findViewById(R.id.ve_dateTextView);
        selectDate = findViewById(R.id.dateButton);
        returnButton = findViewById(R.id.ve_returnButton);
        listView = findViewById(R.id.ve_listView);
        myDb = new DatabaseHelper(this);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(ViewExpensesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                dateTextView.setText(date);
                                accessData();
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
    }
    public void accessData() {
        Cursor r = myDb.getDataForDate(date);
        LinkedList<String> linkedList = new LinkedList();

        if (r.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{});
            listView.setAdapter(adapter);
            return;
        }
        else {
            while (r.moveToNext()) {
                for (int i = 0; i < 8; i++) {
                    if (r.getString(i) == null)
                        linkedList.add(column[i] + " ");
                    else
                        linkedList.add(column[i] + " " + r.getString(i));
                }
            }

            System.out.println(linkedList);

            String[] list = new String[linkedList.size()];

            for (int i = 0; i < linkedList.size(); i++) {
                list[i] = linkedList.get(i);
                System.out.println(list[i]);
            }

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
        }
    }
//    public void showData(String msg) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle("Data");
//        builder.setMessage(msg);
//        AlertDialog dialog = builder.show();
//        return;
//    }
}
