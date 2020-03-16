package com.qqlive.aphone.fortest.LeakCanary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class LeakCanaryApplication extends Application {
    private static RefWatcher mRefWatcher;
    private static final String TAG = "LeakCanaryApplication";

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: do leakcanary");
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        mRefWatcher = LeakCanary.install(this);

    }
    public static RefWatcher getRefWatcher() {
        Log.i(TAG, "getRefwatcher: 监听");
        return mRefWatcher;
    }


}
