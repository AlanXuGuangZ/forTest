package com.qqlive.aphone.fortest.UiColorsAndFonts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.qqlive.aphone.fortest.R;

public class SelectorsActivity extends AppCompatActivity implements SelectorsAdapter.SelectorClickListener{
    private SelectorsAdapter mSelectorsAdapter;
    private RecyclerView mSelectorsRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectors);
        mSelectorsRecyclerView = (RecyclerView)this.findViewById(R.id.mainRecyclerView);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mSelectorsRecyclerView.setHasFixedSize(true);
        mSelectorsRecyclerView.setLayoutManager(layoutManager);
        mSelectorsAdapter = new SelectorsAdapter(this,this);
        mSelectorsRecyclerView.setAdapter(mSelectorsAdapter);



    }

    @Override
    public void onClickSelector(int position) {
        Toast.makeText(this,"666",Toast.LENGTH_SHORT).show();
    }
}
