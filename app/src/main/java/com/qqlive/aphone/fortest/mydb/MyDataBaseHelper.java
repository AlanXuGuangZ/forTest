package com.qqlive.aphone.fortest.mydb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 学习sqlite数据库的使用
 * Created by Marceil on 2017/2/21.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper{
    private final static String TAG = "MyDateBaseHelper";
    private final static String DATABASE_NAME = "users.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_USER = " users";
    private final static String KEY_PHONE = "phone";
    private final static String KEY_PASSWORD = "password";
    /*
    public static final String CREATE_USER = "create table user ("
            + "userid integer primary key autoincrement, "
            + "username text, "
            + "password text)";
            */
    public final static String CREATE_USER = "CREATE TABLE"
            +TABLE_USER
            +"("
            +"id integer primary key"
            +KEY_PHONE
            + " TEXT,"
            + KEY_PASSWORD
            + " TEXT"
            +")";

    public MyDataBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.i(TAG, "my database helper constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate:my database helper create ");
        db.execSQL("create table users(id integer primary key ,phone text,password text)");
//        String sql = "insert into users values (0,0,0)";
//        db.execSQL(sql);
    }
    //更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE users ADD COLUMN other STRING");
    }
}
