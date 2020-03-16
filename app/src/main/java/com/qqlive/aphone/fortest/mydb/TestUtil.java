package com.qqlive.aphone.fortest.mydb;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class TestUtil {
    public  static void insertFakeData(SQLiteDatabase db) {
        if (db == null){
            return;
        }
        List<ContentValues> list = new ArrayList<ContentValues>();
        ContentValues cv = new ContentValues();
        cv.put(Contract.WaitlistEntry.COLUMN_GUEST_NAME,"John");
        cv.put(Contract.WaitlistEntry.COLUMN_PARTY_SIZE,"10");
        list.add(cv);

        cv = new ContentValues();
        cv.put(Contract.WaitlistEntry.COLUMN_GUEST_NAME, "Tim");
        cv.put(Contract.WaitlistEntry.COLUMN_PARTY_SIZE, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(Contract.WaitlistEntry.COLUMN_GUEST_NAME, "Jessica");
        cv.put(Contract.WaitlistEntry.COLUMN_PARTY_SIZE, 99);
        list.add(cv);

        cv = new ContentValues();
        cv.put(Contract.WaitlistEntry.COLUMN_GUEST_NAME, "Larry");
        cv.put(Contract.WaitlistEntry.COLUMN_PARTY_SIZE, 1);
        list.add(cv);

        cv = new ContentValues();
        cv.put(Contract.WaitlistEntry.COLUMN_GUEST_NAME, "Kim");
        cv.put(Contract.WaitlistEntry.COLUMN_PARTY_SIZE, 45);
        list.add(cv);

        try {
            db.beginTransaction();
            db.delete(Contract.WaitlistEntry.TABLE_NAME,null,null);
            for (ContentValues c:list) {
                db.insert(Contract.WaitlistEntry.TABLE_NAME,null,c);
            }
            db.setTransactionSuccessful();
            Log.i("TestUtil", "try do db insert ");
        }catch (SQLException e){
            Log.i("TestUtil", "insertFakeData foals ");
        }finally {
            db.endTransaction();
        }

    }

    public void kitchenData() {

    }
}
