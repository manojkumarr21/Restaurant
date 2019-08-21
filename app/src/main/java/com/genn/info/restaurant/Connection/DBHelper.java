package com.genn.info.restaurant.Connection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.genn.info.restaurant.Model.ModelClass;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    //    Cursor cursor;
    SQLiteDatabase database;

    public static final String DATABASE_NAME="MyuserDb.db";
    public static final String TABLE_NAME="login";

    public static final String COL_1="ID";
    public static final String COL_2="name";
    public static final String COL_3="pass";
    public static final String COL_4="userid";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER,ip TEXT,db TEXT,user TEXT,pass TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID TEXT,name TEXT,pass TEXT,userid TEXT)");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME); //Drop older table if exists

        onCreate(db);
    }

    public boolean insertData (String name, String pass,String userid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", ModelClass.id);
        contentValues.put("name", name);
        contentValues.put("pass", pass);
        contentValues.put("userid", userid);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getAllLoginData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res1 = db.rawQuery("select * from " + TABLE_NAME, null);
        return res1;
    }



    public Boolean checkForm(String name, String pass,String userid){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from "+TABLE_NAME +" where "+COL_2+" = ? and "+COL_3+" = ? and "+COL_4+" = ?",new String[] { name,pass,userid });
        if (res.getCount()>0) return true;
        else return false;

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }







    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
        db.close();

    }




}
