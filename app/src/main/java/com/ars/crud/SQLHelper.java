package com.ars.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "biodatadiri.db";
    private static final int DATABASE_VERSION = 1;
    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE IF EXISTS biodata");
        String sql = "create table biodata(id INTEGER PRIMARY KEY AUTOINCREMENT,nim text null , nama text null, tgl text null, jk text null, alamat text null);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){

    }
}