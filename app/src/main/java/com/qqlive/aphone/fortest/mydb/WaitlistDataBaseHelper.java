package com.qqlive.aphone.fortest.mydb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateUtils;
import android.util.Log;

import javax.xml.transform.sax.SAXTransformerFactory;


public class WaitlistDataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = WaitlistDataBaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "waitlist.db";
    private static final int DATABASE_VERSION = 1;

    public WaitlistDataBaseHelper(Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
        Log.i(TAG, "WaitlistDataBaseHelper: waitlist.db helper constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WAITLIST_TABLE_01 = "create table "+
                Contract.WaitlistEntry.TABLE_NAME+"("+
                Contract.WaitlistEntry._ID + " integer primary key, "+
                Contract.WaitlistEntry.COLUMN_GUEST_NAME + " text not null, "+
                Contract.WaitlistEntry.COLUMN_PARTY_SIZE + " integer not null,"+
                Contract.WaitlistEntry.COLUMN_TIMESTAMP + " timestamp default current_timestamp "+
                ");";
        Log.i(TAG, "onCreate: execSQL"+"("+SQL_CREATE_WAITLIST_TABLE_01+")");
        db.execSQL(SQL_CREATE_WAITLIST_TABLE_01);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists"+ Contract.WaitlistEntry.TABLE_NAME);
        onCreate(db);
    }
}
