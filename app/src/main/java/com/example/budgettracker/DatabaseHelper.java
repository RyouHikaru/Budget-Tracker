package com.example.budgettracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Expenses.db";
    public static final String TABLE_NAME = "expenses_table";
    public static final String COL_1 = "date";
    public static final String COL_2 = "food_and_groceries";
    public static final String COL_3 = "housing";
    public static final String COL_4 = "transportation";
    public static final String COL_5 = "utilities";
    public static final String COL_6 = "insurance";
    public static final String COL_7 = "medical_and_healthcare";
    public static final String COL_8 = "debt_and_investment";
    public static final String COL_9 = "entertainment";
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
                + COL_4 + " NUMBER,"
                + COL_5 + " NUMBER,"
                + COL_6 + " NUMBER,"
                + COL_7 + " NUMBER,"
                + COL_8 + " NUMBER,"
                + COL_9 + " NUMBER)");
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
            case "Housing":
                contentValues.put(COL_3, cost);
                break;
            case "Transportation":
                contentValues.put(COL_4, cost);
                break;
            case "Utilities":
                contentValues.put(COL_5, cost);
                break;
            case "Insurance":
                contentValues.put(COL_6, cost);
                break;
            case "Medical and Healthcare":
                contentValues.put(COL_7, cost);
                break;
            case "Debt and Investment":
                contentValues.put(COL_8, cost);
                break;
            case "Entertainment":
                contentValues.put(COL_9, cost);
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
                contentValues.put(COL_2, cost);
                break;
            case "Housing":
                contentValues.put(COL_3, cost);
                break;
            case "Transportation":
                contentValues.put(COL_4, cost);
                break;
            case "Utilities":
                contentValues.put(COL_5, cost);
                break;
            case "Insurance":
                contentValues.put(COL_6, cost);
                break;
            case "Medical and Healthcare":
                contentValues.put(COL_7, cost);
                break;
            case "Debt and Investment":
                contentValues.put(COL_8, cost);
                break;
            case "Entertainment":
                contentValues.put(COL_9, cost);
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
    public Cursor getDataForDate(String date) {
        db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + COL_2 + ", " + COL_3 +
                ", " + COL_4 + ", " + COL_5 + ", " + COL_6 + ", " + COL_7 +
                ", " + COL_8 + ", " + COL_9 + " FROM " + TABLE_NAME +
                " WHERE date = ?", new String[] {date});
        return res;
    }
}
