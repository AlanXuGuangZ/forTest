package com.qqlive.aphone.fortest.LeakCanary;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qqlive.aphone.fortest.R;

public class LeakCanaryTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "#LeakCanaryTestActivity";
    private static final String TOST = "Theading";
    private static Context sContext;


    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leakcanary_test);
        sContext = this;
        TextView textView = (TextView) findViewById(R.id.test_text_view);
        LeakCanaryApplication.getRefWatcher().watch(this);
        LeakCanaryTestDataModel.getInstance().setRetainedTextView(textView);
        Thread thread = new Thread(mThread);
        thread.start();


    }
     private  Runnable mThread = new Runnable() {
        @Override
        public void run() {
            try {
                Log.i(TAG, "run: before Thread.sleep ");

                Thread.sleep(100000);

                Log.i(TAG, "run:after Thread.sleep ");

            }catch (InterruptedException e) {
                Log.i(TAG, "InterruptedException ");
                e.printStackTrace();
            }
        }
    };
    @Override
    public void onClick(View v) {

    }
}
