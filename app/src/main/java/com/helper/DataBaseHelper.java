package com.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sathish on 2/25/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CardManager.db";
    Context context = null;
    SQLiteDatabase database = null;

    public DataBaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  IF NOT EXISTS CardUser(UserID Integer primary Key autoincrement, Name text not null, Email text not null, Password text not null,unique(Email) )");
        this.database = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(this.getClass().toString(),"I am in upgrade()");
    }

    public long addUser(ContentValues values){
        boolean flag = false;
        database = getWritableDatabase();
        return database.insert("CardUser", "UserID", values);

    }

    public boolean isValidLogin(String email,String password){
        boolean flag = false;
        database = getReadableDatabase();

        String[] columns = {"userId"};
        String selection = "Email=? AND Password=?";
        String[] selectionArgs = {email, password};
        Cursor c = null;

        try{
            c = database.query("CardUser", columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();
            c.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        int i = c.getCount();
        if(i > 0){
            flag = true;
        }
        return flag;
    }
}
