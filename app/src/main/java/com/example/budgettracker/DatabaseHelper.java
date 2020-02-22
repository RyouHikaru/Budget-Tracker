package com.example.budgettracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Expenses.db";
    public static final String TABLE_NAME = "expenses_table";
    public static final String COL_1 = "date";
    public static final String COL_2 = "food_and_groceries";
    public static final String COL_3 = "home_expenses";
    public static final String COL_4 = "entertainment";
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + " (" + COL_1 + " TEXT PRIMARY KEY, "
                + COL_2 + " NUMBER, "
                + COL_3 + " NUMBER, "
                + COL_4 + " NUMBER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String date, String typeOfExpense, int cost) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, date);

        System.out.println(date + " " + typeOfExpense + " " + cost);

        switch (typeOfExpense) {
            case "Food and Groceries":
                contentValues.put(COL_2, cost);
                break;
            case "Home expenses":
                contentValues.put(COL_3, cost);
                break;
            case "Entertainment":
                contentValues.put(COL_4, cost);
                break;
        }

        long insertResult = db.insert(TABLE_NAME, null, contentValues);
        System.out.println(insertResult);
        if (insertResult == -1) {
            if (updateData(date, typeOfExpense, cost) == true) {
                return true;
            }
            return false;
        }
        else {
            return true;
        }
    }
    public boolean updateData(String date, String typeOfExpense, int cost) {
        ContentValues contentValues = new ContentValues();
        String where = "date = ?";
        String[] whereArgs = new String[] {date};

        switch (typeOfExpense) {
            case "Food and Groceries":
                System.out.println("updateData() - case 1");
                contentValues.put(COL_2, cost);
                break;
            case "Home expenses":
                System.out.println("updateData() - case 2");
                contentValues.put(COL_3, cost);
                break;
            case "Entertainment":
                System.out.println("updateData() - case 3");
                contentValues.put(COL_4, cost);
                break;
        }

        long result = db.update(TABLE_NAME, contentValues, where, whereArgs);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
    public Cursor getAllData() {
        db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
}
