package com.example.budgettracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class InputDialog extends AppCompatDialogFragment {
    private Spinner spinner;
    private EditText costtEditText;
    private String[] list = {"", "Food and Groceries", "Home expenses", "Entertainment"};
    private String typeOfExpense;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.layout_input_dialog, null);

        builder.setView(v).setTitle("Add Expense")
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        spinner = v.findViewById(R.id.id_spinner);
        costtEditText = v.findViewById(R.id.id_costEditText);

        if (spinner != null) {
            ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner_layout, list);
            spinner.setAdapter(adapter);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeOfExpense = parent.getSelectedItem().toString();
                if (typeOfExpense.isEmpty())
                    Toast.makeText(getActivity().getApplicationContext(), "No Item Selected", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity().getApplicationContext(), typeOfExpense, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return builder.create();
    }
}
