package com.qqlive.aphone.fortest.SunSine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.qqlive.aphone.fortest.datafrominternet.utilities.NetworkUtils;
import com.qqlive.aphone.fortest.datafrominternet.utilities.OpenWeatherJsonUtuils;
import com.qqlive.aphone.fortest.mydb.Contract;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;


public class SunshineSyncTask {

    private static final String TAG = SunshineSyncTask.class.getSimpleName()+"SunShine";

    synchronized public static void syncWeather(Context context) {

        try {
            URL weatherRequestUrl = NetworkUtils.getUrl(context);

            String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

            Log.i(TAG, "syncWeather: jasonWeatherResponse:"+jsonWeatherResponse);

            ContentValues[] weatherValues = OpenWeatherJsonUtuils
                    .getWeatherContentValuesFromJson(context,jsonWeatherResponse);

            if (weatherValues != null && weatherValues.length != 0) {
                ContentResolver sunshineContentResolver = context.getContentResolver();
                sunshineContentResolver.delete(Contract.WeatherDataEntry.CONTENT_URI,
                        null,
                        null);

                sunshineContentResolver.bulkInsert(
                        Contract.WeatherDataEntry.CONTENT_URI,
                        weatherValues
                );


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
