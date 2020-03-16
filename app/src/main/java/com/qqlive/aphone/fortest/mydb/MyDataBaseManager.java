package com.qqlive.aphone.fortest.mydb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qqlive.aphone.fortest.userInfo.User;

import java.util.ArrayList;
import java.util.List;


public class MyDataBaseManager {
    private MyDataBaseHelper helper;
    private SQLiteDatabase db;
    private final static String TAG="MyDataBaseHelper";

    public MyDataBaseManager(Context context){
        helper = new MyDataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void add(List<User> users){
        db.beginTransaction(); //开始事物
        try {
            for (User user :users){
                Log.i(TAG, "add: "+user.phone+"ss"+user.password);
                db.execSQL("INSERT INTO user VALUES(?,?)",new Object[]{user.phone,user.password});
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public List<User> query(){
        ArrayList<User> users = new ArrayList<User>();
        Cursor cursor = queryTheCursor();
        while (cursor.moveToNext()){
            User user = new User();
            user.password = cursor.getString(cursor.getColumnIndex("password"));
            user.phone = cursor.getString(cursor.getColumnIndex("phone"));
            users.add(user);
        }
        cursor.close();
        return users;


    }
    public Cursor queryTheCursor(){
        Cursor cursor = db.rawQuery("SELECT * FROM user",null);
        return cursor;
    }
    public void closeDB(){
        db.close();
    }

}
