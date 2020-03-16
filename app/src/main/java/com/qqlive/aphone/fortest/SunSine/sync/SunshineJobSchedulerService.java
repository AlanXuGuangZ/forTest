package com.qqlive.aphone.fortest.SunSine.sync;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.qqlive.aphone.fortest.background.utilities.NotificationUtils;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SunshineJobSchedulerService extends JobService {
    
    private static final String TAG = SunshineJobSchedulerService.class.getSimpleName() + "Sunshine"
;
    private AsyncTask mJobSchedulerTask;
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.i(TAG, "onStartJob: ");

        mJobSchedulerTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Context context = SunshineJobSchedulerService.this;
                SunshineSyncTask.syncWeather(context);
                com.qqlive.aphone.fortest.SunSine.NotificationUtils.notifyUserOfNewWeather(context);
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                jobFinished(jobParameters,false);
            }
        };
        mJobSchedulerTask.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG, "onStopJob: ");
        if (mJobSchedulerTask != null) mJobSchedulerTask.cancel(true);
        return true;
    }
}
