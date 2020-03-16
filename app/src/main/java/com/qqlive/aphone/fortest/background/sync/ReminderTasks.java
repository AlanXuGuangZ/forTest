package com.qqlive.aphone.fortest.background.sync;

import android.content.Context;
import android.util.Log;

import com.qqlive.aphone.fortest.background.utilities.NotificationUtils;
import com.qqlive.aphone.fortest.background.utilities.PreferenceUtilites;



public class ReminderTasks {
    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";

    private static final String TAG = ReminderTasks.class.getSimpleName()+"background";
    public static final String ACTION_CHARGING_REMINDER = "charging-reminder";

    public static void executeTask(Context context,String action) {
        Log.i(TAG, "executeTask: ready executeTask");
        
        if(ACTION_INCREMENT_WATER_COUNT.equals(action)) {
            incrementWaterCount(context);
        }else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        }else if (ACTION_CHARGING_REMINDER.equals(action)) {
            issueChargingReminder(context);
        }
    }

    private static void incrementWaterCount(Context context) {

        Log.i(TAG, "incrementWaterCount: ");

        PreferenceUtilites.incrementWaterCount(context);
        NotificationUtils.clearAllNotifications(context);

    }

    private static void issueChargingReminder(Context context) {
        PreferenceUtilites.incrementChargingReminderCount(context);
        NotificationUtils.remindUserBecauseCharging(context);

    }


}
