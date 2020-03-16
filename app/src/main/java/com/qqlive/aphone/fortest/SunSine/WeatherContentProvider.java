package com.qqlive.aphone.fortest.SunSine;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.qqlive.aphone.fortest.datafrominternet.utilities.SunshineDateUtils;
import com.qqlive.aphone.fortest.mydb.Contract;
import com.qqlive.aphone.fortest.mydb.WeatherDataBaseHelper;

/**
 * manager weather data in this provider
 */

public class WeatherContentProvider extends ContentProvider {

    public static final String TAG = WeatherContentProvider.class.getSimpleName()+"Sunshine";

    public static final int WEATHER_DATA = 200;  //数据表

    public static final int WEATHER_DATE = 201; //clomn 天气日期

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    private WeatherDataBaseHelper mWeatherDBhelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(Contract.WeatherDataEntry.WEATHER_AUTHORITY, Contract.WeatherDataEntry.WEATHER_PATH,WEATHER_DATA);
        uriMatcher.addURI(Contract.WeatherDataEntry.WEATHER_AUTHORITY, Contract.WeatherDataEntry.WEATHER_PATH + "/#",WEATHER_DATE);

        return uriMatcher;

    }
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mWeatherDBhelper = new WeatherDataBaseHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.i(TAG, "query: ready query");

        final SQLiteDatabase db = mWeatherDBhelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch (match){
            case WEATHER_DATA:
                Log.i(TAG, "query: query weather_data");
                retCursor = db.query(Contract.WeatherDataEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case WEATHER_DATE:
                String normalizedUtcDateString = uri.getLastPathSegment();

                String[] selectionArguments = new String[]{normalizedUtcDateString};

                Log.i(TAG, "query: normalized utc date string:"+normalizedUtcDateString+"selection argument:"+selectionArguments);

                retCursor = db.query(Contract.WeatherDataEntry.TABLE_NAME,
                        projection,
                        Contract.WeatherDataEntry.COLUMN_DATE + "=? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("unknown uri"+uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new RuntimeException(
                "We are not implementing insert in Sunshine. Use bulkInsert instead");
    }

    @Nullable
    @Override
    public int bulkInsert(@NonNull Uri uri,@NonNull ContentValues[] values) {
        final SQLiteDatabase db = mWeatherDBhelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        //插入一堆数据
        switch (match) {
            case WEATHER_DATA:
                Log.i(TAG, "bulkInsert: db begin Transaction");
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long weatherDate =
                                value.getAsLong(Contract.WeatherDataEntry.COLUMN_DATE);
                        if (!SunshineDateUtils.isDateNormalized(weatherDate)) {
                            throw new IllegalArgumentException("date must be normalized to insert");
                        }
                        long _id = db.insert(Contract.WeatherDataEntry.TABLE_NAME,null,value);
                        if (_id != -1) {
                            Log.i(TAG, "bulkInsert: row Inserted times");
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                    Log.i(TAG, "bulkInsert: set transaction has Successful");
                }finally {
                    db.endTransaction();
                    Log.i(TAG, "bulkInsert: db end transaction");
                }
                if (rowsInserted > 0 ) {
                    Log.i(TAG, "bulkInsert: row Inserted > 0 ; so notifychange content resolver");
                    getContext().getContentResolver().notifyChange(uri,null);
                }
                return rowsInserted;
            default:
                return super.bulkInsert(uri,values);

        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mWeatherDBhelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int weatherDeleted;
        switch (match) {
            case WEATHER_DATA:

                weatherDeleted = db.delete(Contract.WeatherDataEntry.TABLE_NAME,selection,selectionArgs);
                break;

            case WEATHER_DATE:
                String id = uri.getPathSegments().get(1);
                weatherDeleted = db.delete(Contract.WeatherDataEntry.TABLE_NAME,"=id?",new String[]{id});
                break;


            default:
                throw new UnsupportedOperationException("unknown uri"+uri);

        }
        if (weatherDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return weatherDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
