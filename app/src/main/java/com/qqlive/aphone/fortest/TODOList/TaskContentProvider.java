package com.qqlive.aphone.fortest.TODOList;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.UnsupportedSchemeException;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.qqlive.aphone.fortest.mydb.Contract;
import com.qqlive.aphone.fortest.mydb.TaskDataBaseHelper;


public class TaskContentProvider extends ContentProvider{

    private final static String TAG = TaskContentProvider.class.getSimpleName();

    public static final int TASKS = 100;//作为一个匹配
    public static final int TASK_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(Contract.TaskContentEntry.AUTHORITY, Contract.TaskContentEntry.PATH_TASKS,TASKS);
        uriMatcher.addURI(Contract.TaskContentEntry.AUTHORITY, Contract.TaskContentEntry.PATH_TASKS + "/#",TASK_WITH_ID);

        return uriMatcher;

    }

    private TaskDataBaseHelper mTaskDBHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();  //建立上下文
        mTaskDBHelper = new TaskDataBaseHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mTaskDBHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor;
        switch (match) {

            case TASKS:
                Log.i(TAG, "query: "+ TASKS);
                retCursor = db.query(Contract.TaskContentEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case TASK_WITH_ID:
                Log.i(TAG, "query: "+TASK_WITH_ID);

                //uri content://<authority>/tasks/#

                String id = uri.getPathSegments().get(1);

                String mSelection = "_id=?";

                String [] mSelectionArgs = new String[]{id};
                retCursor = db.query(Contract.TaskContentEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder
                        );
                break;
            default:
                throw new UnsupportedOperationException("unknow uri:"+uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        Log.i(TAG, "insert: "+retCursor);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mTaskDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {

            case TASKS:
                Log.i(TAG, "insert: "+TASKS);
                long id = db.insert(Contract.TaskContentEntry.TABLE_NAME,null,values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.TaskContentEntry.CONTENT_URI,id);
                }else {
                    throw new android.database.SQLException("failed to insert row into" + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG, "delete****uri:*****"+uri);


        final SQLiteDatabase db = mTaskDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int tasksDeleted;

        switch (match) {
            case TASK_WITH_ID:
                String id = uri.getPathSegments().get(1);

                Log.i(TAG, "delete: Get the task ID from the URI path:****"+id);
                tasksDeleted = db.delete(Contract.TaskContentEntry.TABLE_NAME,"_id=?",
                        new String[]{id});
                Log.i(TAG, "delete: get the tasksDeleted ID from db.delete function:****"+tasksDeleted);
                break;
            default:
                throw new UnsupportedOperationException("unknown uri:"+uri);
        }
        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri,null);;
        }

        return tasksDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
