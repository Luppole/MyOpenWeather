package com.example.weatherapptutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "register.db";

    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username, TEXT primary key, password TEXT)");
        sqLiteDatabase.execSQL("create table user_dates(username TEXT, date TEXT, value TEXT, primary key (username, date))");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists user_dates");
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = myDB.insert("users", null, contentValues);

        if(result == -1) {
            return false;
        }

        return true;
    }

    public boolean insertUserDate(String username, String date, String value) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("date", date);
        contentValues.put("value", value);
        long result = myDB.insert("user_dates", null, contentValues);

        return result != -1;
    }

    public boolean checkUsername(String user) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?", new String[]{user});
        if(cursor.getCount() > 0) {
            return true;
        }

        return false;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?", new String[]{username, password});
        if(cursor.getCount() > 0) {
            return true;
        }

        return false;
    }

    public String getValueForDate(String username, String date) {
        // Check if username is null
        if (username == null) {
            // Handle the null username case here, such as returning an error message or a default value
            return "Username is null";
        }

        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT value FROM user_dates WHERE username = ? AND date = ?", new String[]{username, date});

        String value = ""; // Default value if no data found

        if (cursor != null && cursor.moveToFirst()) {
            int valueIndex = cursor.getColumnIndex("value");
            if (valueIndex != -1) {
                value = cursor.getString(valueIndex);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return value;
    }


    public boolean removeUserDate(String username, String date) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        int result = myDB.delete("user_dates", "username = ? AND date = ?", new String[]{username, date});
        return result > 0;
    }
}
