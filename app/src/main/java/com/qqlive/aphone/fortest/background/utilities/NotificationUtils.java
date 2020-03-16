package com.qqlive.aphone.fortest.background.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.background.BackGroundMainActivity;
import com.qqlive.aphone.fortest.background.sync.ReminderTasks;
import com.qqlive.aphone.fortest.background.sync.WaterReminderIntentService;


public class NotificationUtils {
    private static final String TAG = NotificationUtils.class.getSimpleName()+"background";

    private static final int WATER_REMINDER_PENDING_INTENT_ID = 3417;
    private static final int WATER_REMINDER_NOTIFICATION_ID = 1138;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 520;
    private static final int ACTION_DRINK_PENDING_INTENT_ID = 521;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserBecauseCharging(Context context) {
        Log.i(TAG, "remindUserBecauseCharging: ");

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_drink_notification)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
                .setContentText(context.getString(R.string.charging_reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.charging_reminder_notification_body)
                ))
                .addAction(drinkWaterAction(context))
                .addAction(ignoreReminderAction(context))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID,notificationBuilder.build());

    }
    private static NotificationCompat.Action ignoreReminderAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, WaterReminderIntentService.class);
        ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);

        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.ic_cancel_black_24px,
                "no thanks",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }
    private static NotificationCompat.Action drinkWaterAction (Context context) {
        Intent incrementWaterCountIntent = new Intent(context,WaterReminderIntentService.class);
        incrementWaterCountIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        PendingIntent incrementWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_DRINK_PENDING_INTENT_ID,
                incrementWaterCountIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Action drinkWaterAction = new NotificationCompat.Action(R.drawable.ic_drink_notification,
                "i did it!",
                incrementWaterPendingIntent);
        return drinkWaterAction;

    }



    private static PendingIntent contentIntent(Context context) {

        Log.i(TAG, "contentIntent: ");

        Intent startActivityIntent = new Intent(context, BackGroundMainActivity.class);


        return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_cancel_black_24px);
        Log.i(TAG, "largeIcon: "+largeIcon);
        return largeIcon;

    }




}
