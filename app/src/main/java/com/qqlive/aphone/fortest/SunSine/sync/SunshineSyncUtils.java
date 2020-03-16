package com.qqlive.aphone.fortest.SunSine.sync;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.LoginFilter;
import android.util.Log;

import com.qqlive.aphone.fortest.mydb.Contract;

import java.util.concurrent.TimeUnit;


public class SunshineSyncUtils {

    private static final String TAG = SunshineSyncUtils.class.getSimpleName()+"Sunshine";
    private static final int UPDATE_INTERVAL_MINUTES = 500;
    private static final int UPDATE_INTERVAL_SECONDS = (int)(TimeUnit.MINUTES.toSeconds(UPDATE_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = UPDATE_INTERVAL_SECONDS;

    private static int REMINDER_JOB_TAG = 0;
    private static boolean sInitialized;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void scheduleJobSync(@NonNull final Context context) {
        Log.i(TAG, "scheduleJobSync: ***********************do notification   "+SunshineJobSchedulerService.class.getName());
        Log.i(TAG, "scheduleJobSync: ********************minutes time:"+UPDATE_INTERVAL_MINUTES+"seconds time:"+SYNC_FLEXTIME_SECONDS);
        JobInfo.Builder builder = new JobInfo.Builder(REMINDER_JOB_TAG++,new ComponentName(context,SunshineJobSchedulerService.class.getName()))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(SYNC_FLEXTIME_SECONDS);
        JobScheduler mJobScheduler = (JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        mJobScheduler.schedule(builder.build());
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    synchronized public static void initialize(@NonNull final Context context) {
         if (sInitialized) return;
         sInitialized = true;

        scheduleJobSync(context);
         new AsyncTask<Void,Void,Void>() {

             @Override
             protected Void doInBackground(Void... params) {

                 Uri forecastQueryUri = Contract.WeatherDataEntry.CONTENT_URI;
                 String[] projectionColumns = {Contract.WeatherDataEntry._ID};
                 String selectionStatement = Contract.WeatherDataEntry.getSqlSelectForTodayOnwards();

                 Cursor cursor = context.getContentResolver().query(
                         forecastQueryUri,
                         projectionColumns,
                         selectionStatement,
                         null,
                         null
                 );
                 if (cursor == null || cursor.getCount() ==0 ) {
                     Log.i(TAG, "doInBackground: 同步数据");
                     //启动且数据为空的时候调用同步流程
                     startImmediateSync(context);
                 }
                 cursor.close();
                 return null;
             }
         }.execute();
    }

    public static void startImmediateSync (@NonNull final Context context) {
        Log.i(TAG, "startImmediateSync: ");
        Intent intentToSyncImmediately = new Intent(context,SunshineSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }




}
