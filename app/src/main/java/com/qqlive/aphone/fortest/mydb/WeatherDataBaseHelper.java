package com.qqlive.aphone.fortest.mydb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class WeatherDataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = WeatherDataBaseHelper.class.getSimpleName()+"Sunshine";
    private static final String DATABASE_NAME = "weatherdata.db";
    private static final int DATABASE_VERSION = 3;

    public WeatherDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i(TAG, "WeatherDataBaseHelper: weatherdata.db helper constructor");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WEATHERDATA_TABLE = "create table " +
                Contract.WeatherDataEntry.TABLE_NAME        + "(" +
                Contract.WeatherDataEntry._ID               + " integer primary key, "+
                Contract.WeatherDataEntry.COLUMN_DATE       + " integer not null, " +
                Contract.WeatherDataEntry.COLUMN_WEATHER_ID + " integer not null, " +
                Contract.WeatherDataEntry.COLUMN_MAX_TEMP   + " real not null, " +
                Contract.WeatherDataEntry.COLUMN_MIN_TEMP   + " real not null, " +
                Contract.WeatherDataEntry.COLUMN_HUMIDITY   + " real not null, " +
                Contract.WeatherDataEntry.COLUMN_PRESSURE   + " real not null, " +
                Contract.WeatherDataEntry.COLUMN_DEGREES    + " real not null, " +
                Contract.WeatherDataEntry.COLUMN_WIND_SPEED + " real not null, " +
                " unique (" + Contract.WeatherDataEntry.COLUMN_DATE + ") on conflict replace);";
        Log.i(TAG, "onCreate: SQL:"+SQL_CREATE_WEATHERDATA_TABLE);
        db.execSQL(SQL_CREATE_WEATHERDATA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists"+ Contract.WaitlistEntry.TABLE_NAME);
        onCreate(db);

    }
}
