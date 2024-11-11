package com.example.lap2try.Database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String NAME_DB="Monhoc.db";

    public static final int VERSION_DB = 2;

    public DBHelper(@Nullable Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable ="CREATE TABLE MONHOC(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TITLE TEXT NOT NULL," +
                "CONTENT TEXT NOT NULL," +
                "DATE TEXT NOT NULL," +
                "TYPE TEXT NOT NULL," +
                "STATUS INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS MONHOC");
            onCreate(sqLiteDatabase);
        }
    }
}

