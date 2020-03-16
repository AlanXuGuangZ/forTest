package com.qqlive.aphone.fortest.background.sync;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.concurrent.TimeUnit;


public class ReminderUtilities {
    private static final int REMINDER_INTERVAL_MINUTES = 5;
    private static final int REMINDER_INTERVAL_SECONDS = (int)(TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static int REMINDER_JOB_TAG = 0;
    private  static boolean sInitialized;//跟踪job



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    synchronized public static void scheduleChargingReminder(@NonNull final Context context) {
        JobInfo.Builder builder = new JobInfo.Builder(REMINDER_JOB_TAG++,new ComponentName(context,WaterReminderJobSchedulerService.class.getName()))
                .setPeriodic(SYNC_FLEXTIME_SECONDS)
                .setPersisted(true)
                .setRequiresCharging(true);
//        JobScheduler mJobScheduler = (JobScheduler)context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        mJobScheduler.schedule(builder.build());
//        sInitialized = true;
    }



}
