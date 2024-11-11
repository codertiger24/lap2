package com.example.lap2try.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lap2try.Database.DBHelper;
import com.example.lap2try.Model.Monhoc;

import java.util.ArrayList;

//import fpoly.hieuttph56046.lab2hieuttph56046.Database.DBHelper;
//import fpoly.hieuttph56046.lab2hieuttph56046.Model.Monhoc;

public class MonhocDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public MonhocDAO(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean addMonhoc(Monhoc monhoc){
        ContentValues values = new ContentValues();
        values.put("TITLE",monhoc.getTitle());
        values.put("CONTENT",monhoc.getContent());
        values.put("DATE",monhoc.getDate());
        values.put("TYPE",monhoc.getType());
        values.put("STATUS",monhoc.getStatus());
        long result = database.insert("MONHOC",null,values);
        if (result>0)
            return true;
        return false;
    }

    public boolean deleteMonhoc(Monhoc monhoc){
        long check = database.delete("MONHOC","ID=?", new String[]{String.valueOf(monhoc.getId())});
        if (check>0)
            return true;
        return false;
    }

    public boolean updateMonhoc(Monhoc monhoc){
        ContentValues values = new ContentValues();
        values.put("TITLE",monhoc.getTitle());
        values.put("CONTENT",monhoc.getContent());
        values.put("DATE",monhoc.getDate());
        values.put("TYPE",monhoc.getType());
        long checker = database.update("MONHOC",values,"ID=?",new String[]{String.valueOf(monhoc.getId())});
        if (checker>0)
            return true;
        return false;
    }

    public boolean updateStatus(Monhoc monhoc){
        ContentValues values = new ContentValues();
        values.put("STATUS",monhoc.getStatus());
        long row = database.update("MONHOC",values,"ID=?",new String[]{String.valueOf(monhoc.getId())});
        if (row>0)
            return true;
        return false;
    }

    public ArrayList<Monhoc> getMonhoc(){
        ArrayList<Monhoc> monhocArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM MONHOC",null);
        while (cursor.moveToNext()){
            Monhoc monhoc = new Monhoc(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5));
            monhoc.setId(cursor.getInt(0));
            monhocArrayList.add(monhoc);
        }
        return monhocArrayList;
    }
}

