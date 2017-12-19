package com.example.linm.lab8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by ACER on 2017/12/19.
 */

public class MyDB extends SQLiteOpenHelper{

    private static final String DB_NAME = "Contacts.db";
    public static final String TABLE_NAME = "Contacts";
    private static final int DB_VERSION = 1;

    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MyDB(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE if not exists "
                + TABLE_NAME
                + "(name TEXT PRIMARY KEY,"
                + "birth TEXT,"
                + "gift TEXT)";
        db.execSQL(CREATE_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void insert(String name, String birth, String gift){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("birth",birth);
        values.put("gift",gift);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void update(String name, String birth, String gift){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name = ?" ;
        String[] whereArgs = {name};
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("birth",birth);
        values.put("gift",gift);
        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    public void delete(String name){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name = ?";
        String[] whereArgs = {name};
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public Cursor ifexit(String name){
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from Contacts where name = \"" +name+"\"";
        Log.e("debug",query);
        return db.rawQuery(query,null);
    }

    public Cursor selectall( ){
        SQLiteDatabase db = getWritableDatabase();
        String query = "select * from Contacts ";
        return db.rawQuery(query,null);
    }

}
