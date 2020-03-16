package com.qqlive.aphone.fortest.background.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;



public class PreferenceUtilites {

    private static final String TAG = PreferenceUtilites.class.getSimpleName()+"background";

    public static final String KEY_WATER_COUNT = "water-count";
    public static final String KEY_CHARGING_REMINDER_COUNT = "charging-reminder-count";
    private static final int DEFAULT_COUNT =0;


    synchronized public static void incrementWaterCount(Context context) {
        int waterCount = PreferenceUtilites.getWaterCount(context);
        Log.i(TAG, "incrementWaterCount: "  + waterCount);
        PreferenceUtilites.setWaterCount(context,++waterCount);
        Log.i(TAG, "incrementWaterCount: "  + waterCount);

    }

    synchronized private static void setWaterCount(Context context, int glassesOFWater) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_WATER_COUNT,glassesOFWater);
        editor.apply();

    }

    public static int getWaterCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int glassesOfWater = prefs.getInt(KEY_WATER_COUNT,DEFAULT_COUNT);
        return glassesOfWater;
    }

    synchronized public static void incrementChargingReminderCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int chargingReminders = prefs.getInt(KEY_CHARGING_REMINDER_COUNT,DEFAULT_COUNT);

        SharedPreferences.Editor editor = prefs.edit();
        Log.i(TAG, "incrementChargingReminderCount: "+chargingReminders );
        editor.putInt(KEY_CHARGING_REMINDER_COUNT,++chargingReminders);
        editor.apply();
    }

    public static int getChargingReminderCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int chargingReminder = prefs.getInt(KEY_CHARGING_REMINDER_COUNT,DEFAULT_COUNT);
        return chargingReminder;
    }






}
