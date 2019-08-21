package com.genn.info.restaurant.Util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDBClass extends SQLiteOpenHelper {

    Context context;

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "Favourite";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_FAV = "fav";

    public SQLDBClass(Context mCtx){
        super(mCtx,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table Favourite " +
                        "(id integer primary key, name text,fav text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Favourite");
        onCreate(db);
    }

    public void addToFavourites(String favID){

        SQLiteDatabase db = getReadableDatabase();
        String qury =String.format("INSERT INTO Favourite(id) values ('%s');",favID);
        db.execSQL(qury);

    }

    public void removeFromFavourites(String favID){

        SQLiteDatabase db = getReadableDatabase();
        String qury =String.format("DELETE FROM Favourite WHERE id='%s';",favID);
        db.execSQL(qury);

    }

    public boolean isFavourites(String favID){

        SQLiteDatabase db = getReadableDatabase();
        String qury =String.format("SELECT * FROM Favourite WHERE id='%s';",favID);
        Cursor cursor = db.rawQuery(qury,null);
        if (cursor.getCount()<=0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }


}
