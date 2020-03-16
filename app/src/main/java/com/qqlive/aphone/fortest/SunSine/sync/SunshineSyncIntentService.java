package com.qqlive.aphone.fortest.SunSine.sync;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class SunshineSyncIntentService extends IntentService{
    
    private static final String TAG = SunshineSyncIntentService.class.getSimpleName()+ "Sunshine";
    
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public SunshineSyncIntentService() {
        super("SunshineSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent: reading syncWeather***********from Service");
        SunshineSyncTask.syncWeather(this);

    }
}
