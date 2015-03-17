package com.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

        db.execSQL("create table  IF NOT EXISTS Card(CardNumber Text primary Key , CardExpiryDate text not null, cvv text not null, balance Integer , status text not null, cardType text not null, Email text not null  )");
        this.database = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(this.getClass().toString(),"I am in upgrade()");
        db.execSQL("create table  IF NOT EXISTS CardUser(UserID Integer primary Key autoincrement, Name text not null, Email text not null, Password text not null,unique(Email) )");
        db.execSQL("create table  IF NOT EXISTS Card(CardNumber Text primary Key , CardExpiryDate text not null, cvv text not null, balance Integer , status text not null, cardType text not null ,Email text not null)");
        this.database = db;
    }

    public long addCard(ContentValues values){

        database = getWritableDatabase();
        database.execSQL("create table  IF NOT EXISTS Card(CardNumber Text primary Key , CardExpiryDate text not null, cvv text not null, balance Integer , status text not null, cardType text not null,Email text not null )");
        database = getWritableDatabase();
        return database.insert("Card", null, values);

    }

    public List FetchCards(String EmailID,String cardType){

        database = getReadableDatabase();
        Cursor cardCursor = database.rawQuery("select * from Card where Email = \"" +EmailID +"\" and cardType =\""+cardType+"\" and status = 'A'", null);
        List cardlist = new ArrayList();
        Card card =null;
        if (cardCursor!=null&& cardCursor.getCount()>0)
        if (cardCursor!=null && cardCursor.getCount()>0)
        {
            cardCursor.moveToFirst();
            do {
                card = new Card();
                card.setCardNumber(cardCursor.getString(0));
                card.setBalance(cardCursor.getInt(3));
                cardlist.add(card);
            } while (cardCursor.moveToNext());
        }
        return cardlist;
    }


    public long addUser(ContentValues values){
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


    public boolean isAmountValid(int userKeyedAmount, String email){
        boolean flag = false;
        database = getReadableDatabase();
        int inAmount = 0;
        Cursor cardCursor = database.rawQuery("select sum(balance) as Balance from Card where Email = \"" +email +"\" and cardType ='G' and status = 'A' ", null);

        Log.v("DBHelper","column names :"+cardCursor.getColumnNames().toString());
        Log.v("DBHelper","column names(0) :"+cardCursor.getColumnName(0));
        cardCursor.moveToFirst();
        inAmount = cardCursor.getInt(0);

        if(userKeyedAmount>inAmount){
            flag = false;
        }else
            flag = true;

        return flag;
    }
}
