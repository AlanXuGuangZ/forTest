package com.qqlive.aphone.fortest.LeakCanary;

import android.widget.TextView;

public class LeakCanaryTestDataModel {

    private static LeakCanaryTestDataModel sInstance;
    private TextView mRetainedTextView;

    public static LeakCanaryTestDataModel getInstance() {
        if (sInstance == null) {
            sInstance = new LeakCanaryTestDataModel();
        }
        return sInstance;
    }

    public void setRetainedTextView(TextView textView) {
        this.mRetainedTextView = textView;
    }
}
