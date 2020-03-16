package com.qqlive.aphone.fortest.WaitList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.mydb.Contract;
import com.qqlive.aphone.fortest.mydb.WaitlistDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class WaitListMainActivity extends AppCompatActivity implements GuestListAdapter.WaitlistClickListener{
    private static final String TAG = WaitListMainActivity.class.getSimpleName();
    private GuestListAdapter mAdapter;
    private SQLiteDatabase db;
    private Toast mToast;

    private EditText mGuestNameEditText;
    private EditText mPartySizeEditText;
    private TextView mNoGuestTextView;
    private RecyclerView waitlistRecyclerView;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_list_main);
        mNoGuestTextView = (TextView)findViewById(R.id.tv_show_empty_guest);
        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guest_list_view);
        loadGuestData();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long)viewHolder.itemView.getTag();
                removeWaitlist(id);
                if (getAllGuests().getCount() == 0){
                    Log.i(TAG, "onSwiped: getAllGuests ==============null");
                    showNoGuestView();
                }else {
                    mAdapter.swapCursor(getAllGuests());
                }

            }
        }
        ).attachToRecyclerView(waitlistRecyclerView);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    public void loadGuestData(){
        Log.i(TAG, "loadGuestData: ");
        waitlistRecyclerView.setHasFixedSize(true);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        WaitlistDataBaseHelper dbHelper = new WaitlistDataBaseHelper(this);

        db = dbHelper.getWritableDatabase();

        Cursor cursor = getAllGuests();
        if (cursor.getCount()==0) {
            Log.i(TAG, "loadGuestData: cursor =============== 0");
            showNoGuestView();
        }else {
            showHasGuestView();
            // Create an adapter for that cursor to display the data
            mAdapter = new GuestListAdapter(this,cursor,this);

            waitlistRecyclerView.setAdapter(mAdapter);
        }

    }
    public void addToWaitlist(View view) {
        mGuestNameEditText = (EditText)findViewById(R.id.et_person_name);
        mPartySizeEditText = (EditText)findViewById(R.id.et_party_count);
        List<ContentValues> list = new ArrayList<ContentValues>();
        ContentValues cv = new ContentValues();
        if (mGuestNameEditText.getText().toString().isEmpty() || mPartySizeEditText.getText().toString().isEmpty()){
            Toast.makeText(WaitListMainActivity.this, "输入有误，小心拿小圈圈捶你胸口哦:" ,Toast.LENGTH_SHORT).show();
        }else {
            cv.put(Contract.WaitlistEntry.COLUMN_GUEST_NAME,mGuestNameEditText.getText().toString());
            cv.put(Contract.WaitlistEntry.COLUMN_PARTY_SIZE,mPartySizeEditText.getText().toString());
            list.add(cv);
            try {
                db.beginTransaction();
                db.insert(Contract.WaitlistEntry.TABLE_NAME,null,cv);
                db.setTransactionSuccessful();
            }catch (Exception dbe){
                dbe.printStackTrace();
            }finally {
                db.endTransaction();
                Toast.makeText(WaitListMainActivity.this, "添加成功" ,Toast.LENGTH_SHORT).show();
                mPartySizeEditText.setText(null);
                mGuestNameEditText.setText(null);
                loadGuestData();
            }
        }
    }
    public boolean removeWaitlist(long id){
        return db.delete(Contract.WaitlistEntry.TABLE_NAME,Contract.WaitlistEntry._ID + "=" + id,null) > 0;

    }


    /**
     * Query the mDb and get all guests from the waitlist table
     *
     * @return Cursor containing the list of guests
     */
    private Cursor getAllGuests() {
        return db.query(
                Contract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }

    @Override
    public void onWaitlistClick(int clickWaitlist) {
        Log.i(TAG, "onWaitlistClick: ");
        if (mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(this,"Item #"+clickWaitlist,Toast.LENGTH_LONG);
        mToast.show();
    }

    public void showNoGuestView(){
        mNoGuestTextView.setVisibility(View.VISIBLE);
        waitlistRecyclerView.setVisibility(View.INVISIBLE);
    }
    public void showHasGuestView() {
        mNoGuestTextView.setVisibility(View.INVISIBLE);
        waitlistRecyclerView.setVisibility(View.VISIBLE);
    }
}
