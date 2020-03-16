package com.qqlive.aphone.fortest.background.sync;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class WaterReminderIntentService extends IntentService {

    private final static String TAG = WaterReminderIntentService.class.getSimpleName()+"background";


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WaterReminderIntentService() {
        super("WaterReminderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "WaterReminderIntentService********onHandleIntent: get intent"+intent);
        String action = intent.getAction();
        ReminderTasks.executeTask(this,action);

    }
}
