package com.qqlive.aphone.fortest.datafrominternet.utilities;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.qqlive.aphone.fortest.R;



public class SunshinePreferences {

    public static final String PREF_CITY_NAME = "city_name";

    public static final String PREF_COORD_LAT = "coord_lat";
    public static final String PREF_COORD_LONG = "coord_long";

    private static final String DEFAULT_WETHER_LOCATION = "94043,USA";
    private static final double[] DEFAULT_WETHER_COORDINATES = {37.4284, 122.0724};

    private static final String DEFAULT_MAP_LOCATION =
            "1600 Amphitheatre Parkway, Mountain View, CA 94043";



    public static double[] getLocationCoordinates(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        double[] preferredCoordinates = new double[2];
        preferredCoordinates[0] = Double.longBitsToDouble(sp.getLong(PREF_COORD_LAT,Double.doubleToRawLongBits(0.0)));
        preferredCoordinates[1] = Double.longBitsToDouble(sp.getLong(PREF_COORD_LONG,Double.doubleToRawLongBits(0.0)));

        return preferredCoordinates;
    }


    public static String getPreferredWeatherLocation(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String keyForLocation = context.getString(R.string.pref_location_key);
        String defaultLocation = context.getString(R.string.pref_location_default);
        return sharedPreferences.getString(keyForLocation,defaultLocation);
    }
    public static boolean isMetric(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String preferredUnits = preferences.getString(context.getString(R.string.pref_unit_key),context.getString(R.string.pref_unit_default));
        if (preferredUnits.equals(context.getString(R.string.pref_temp_fahrenheit_value))) {
            return true;
        }else {
            return false;
        }
    }


    public static String getDefaultWeatherLcoation(){
        return DEFAULT_WETHER_LOCATION;
    }

    public static double[] getDefaultWeatherCoordinates(){
        return DEFAULT_WETHER_COORDINATES;
    }

    public static boolean isLocationLatLonAvailable(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean spContainLatitude = sp.contains(PREF_COORD_LAT);
        boolean spContainLongitude = sp.contains(PREF_COORD_LONG);
        boolean spContainBothLatitudeAndLongitude = false;
        if (spContainLatitude && spContainLongitude) {
            spContainBothLatitudeAndLongitude = true;
        }
        return spContainBothLatitudeAndLongitude;
    }

    public static void setLocationDetails( Context context,double lat,double lon) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.putLong(PREF_COORD_LAT,Double.doubleToLongBits(lat));
        editor.putLong(PREF_COORD_LONG,Double.doubleToLongBits(lon));
        editor.apply();
    }


    public static void saveLastNotificationTime(Context context, long timeOfNotification) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        String lastNotificationKey = context.getString(R.string.pref_last_notification);
        editor.putLong(lastNotificationKey,timeOfNotification);
        editor.apply();
    }
}
