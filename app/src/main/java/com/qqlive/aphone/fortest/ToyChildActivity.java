package com.qqlive.aphone.fortest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ToyChildActivity extends AppCompatActivity {

    private final static String TAG = ToyChildActivity.class.getSimpleName();

    private TextView mToyDisplayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toy_child);
        mToyDisplayTextView = (TextView)findViewById(R.id.tv_display);
        Intent getPutExtreData = getIntent();
        if (getPutExtreData.hasExtra(Intent.EXTRA_TEXT)){
            mToyDisplayTextView.setText(getPutExtreData.getStringExtra(Intent.EXTRA_TEXT));
        }else {
            mToyDisplayTextView.setText("null");
        }
    }
}
