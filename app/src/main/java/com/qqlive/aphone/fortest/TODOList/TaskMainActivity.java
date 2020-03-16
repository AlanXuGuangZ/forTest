package com.qqlive.aphone.fortest.TODOList;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.mydb.Contract;
import com.qqlive.aphone.fortest.mydb.TaskDataBaseHelper;

public class TaskMainActivity extends AppCompatActivity implements
        View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = TaskMainActivity.class.getSimpleName();
    private static final int TASK_LOADER_ID = 0;
    private CustomCursorAdapter mAdapter;

    private SQLiteDatabase db;

    private Toast mToast;

    private RecyclerView mTaskRecyclerView;

    private Cursor mCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_main);
        findViewById(R.id.fab).setOnClickListener(this);

        mTaskRecyclerView = (RecyclerView)this.findViewById(R.id.recyclerViewTasks);

        mTaskRecyclerView.setHasFixedSize(true);

        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CustomCursorAdapter(this);
        mTaskRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int id = (int)viewHolder.itemView.getTag();

                String stringId = Integer.toString(id); //conver to string
                Uri uri = Contract.TaskContentEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();
                Log.i(TAG, "onSwiped: delected row from uri*******uri*****"+uri);
                getContentResolver().delete(uri,null,null);
                getSupportLoaderManager().restartLoader(TASK_LOADER_ID,null,TaskMainActivity.this);


            }
        }).attachToRecyclerView(mTaskRecyclerView);

        getSupportLoaderManager().initLoader(TASK_LOADER_ID,null,this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().initLoader(TASK_LOADER_ID,null,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent addIntent = new Intent(this,AddTaskActivity.class);
                startActivity(addIntent);
        }
    }

    private Cursor getAllTasks() {
        return getContentResolver().query(Contract.TaskContentEntry.CONTENT_URI,
                null,
                null,
                null,
                Contract.TaskContentEntry.COLUMN_PRORITY);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mTaskData = null;
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    deliverResult(mTaskData);
                }else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                   return getAllTasks();
                }catch (Exception e) {
                    Log.e(TAG, "loadInBackground: failed load data" );
                    e.printStackTrace();
                    return null;
                }
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
