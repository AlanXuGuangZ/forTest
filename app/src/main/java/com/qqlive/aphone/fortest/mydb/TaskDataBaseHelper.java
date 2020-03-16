package com.qqlive.aphone.fortest.mydb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.AsyncTaskLoader;


public class TaskDataBaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "tasksDb.db";

    private static final int DATABASE_VERSION = 1;



    public TaskDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_TASKS_TABLE = "create table "+
                Contract.TaskContentEntry.TABLE_NAME + "(" +
                Contract.TaskContentEntry._ID + " integer primary key, " +
                Contract.TaskContentEntry.COLUMN_DESCRIPTION + " text not null, "+
                Contract.TaskContentEntry.COLUMN_PRORITY + " integer nou null);";
        db.execSQL(SQL_CREATE_TASKS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ Contract.TaskContentEntry.TABLE_NAME);
        onCreate(db);
    }
}
