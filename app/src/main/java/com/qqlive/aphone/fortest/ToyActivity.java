package com.qqlive.aphone.fortest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;



public class ToyActivity extends AppCompatActivity implements ToyGreenAdapter.ListItemClickListener{

    private final static int NUM_LIST_ITEMS = 100;
    private ToyGreenAdapter mAdapter;
    private RecyclerView mNumberList;
    private TextView mToysListTextView;
    private final static String TAG = "ToyActivity";
    private String mPuteExtraData;
    private Toast mToast;


    @Override
    public void onCreate(Bundle savedInstanceState){
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toy);
        initToyData();

    }
    private void initToyData() {
        mNumberList = (RecyclerView)findViewById(R.id.rv_numbers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumberList.setLayoutManager(layoutManager);

        mNumberList.setHasFixedSize(true);

        mAdapter = new ToyGreenAdapter(NUM_LIST_ITEMS,this);

        mNumberList.setAdapter(mAdapter);
////        mToysListTextView = (TextView) findViewById(R.id.tv_toys_name);
//        String[] toyNames = ToyBoy.getToyNames();
//        try{
//            for (String toyName : toyNames) {
//                mToysListTextView.append(toyName + "\n\n\n");
//            }
//        }catch (Exception e){
//            e.getStackTrace();
//        }

    }
    public boolean onCreateOptionsMenu(Menu menu){
        Log.i(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.toy,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Log.i(TAG, "onOptionsItemSelected: ");
        int itemWasSelected = item.getItemId();
        if (itemWasSelected == R.id.action_reset){
            mAdapter  = new ToyGreenAdapter(NUM_LIST_ITEMS,this);
            mNumberList.setAdapter(mAdapter);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {

        if (mToast != null){
            mToast.cancel();
        }
        Context context = ToyActivity.this;
        Class destinationActivity = ToyChildActivity.class;
        Intent intent =new Intent(context,destinationActivity);
        String toastMessage = "Item #" + clickedItemIndex +"_AND_"+context+"_TO_"+ destinationActivity+"clicked";
        mToast = Toast.makeText(this,toastMessage,Toast.LENGTH_LONG);
        mToast.show();
        String clickedItemIndexStr =""+ clickedItemIndex;
        if (clickedItemIndexStr.isEmpty()){
            Toast.makeText(this,"非法字符",Toast.LENGTH_LONG).show();
        }else {
            intent.putExtra(Intent.EXTRA_TEXT,"Item #" + clickedItemIndex);
            startActivity(intent);
        }

    }
}
