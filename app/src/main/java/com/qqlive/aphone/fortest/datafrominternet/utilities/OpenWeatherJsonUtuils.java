package com.qqlive.aphone.fortest.datafrominternet.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.SunSine.SunShineMainActivity;
import com.qqlive.aphone.fortest.mydb.Contract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;

/**
 *
 * This method parses JSON from a web response and returns an array of Strings
 * describing the weather over various days from the forecast.
 * <p/>
 * Later on, we'll be parsing the JSON into structured data within the
 * getFullWeatherDataFromJson function, leveraging the data we have stored in the JSON. For
 * now, we just convert the JSON into human-readable strings.
 *
 *
 * @return Array of Strings describing weather data
 *
 * @throws JSONException If JSON data cannot be properly parsed
 *
 */

public final class OpenWeatherJsonUtuils {
    private final static String TAG = "OpenWeatherJsonUtuils";

    private final static String OWM_LIST = "list"; //天气信息，一个list数组
    //    温度信息
    private final static String OWM_TEMP = "temp";
    private final static String OWM_MIN = "min";
    private final static String OWM_MAX = "max";
    private final static String OWM_WEATHER = "weather";
    private static final String OWM_WEATHER_ID = "id";
    private final static String OWM_DESCREPTION = "main";
    private final static String OWM_MESSAGE_CODE = "cod";
    private final static String OWM_CITY = "city";
    private static final String OWM_COORD = "coord";
    private static final String OWM_LATITUDE = "lat";
    private static final String OWM_LONGITUDE = "lon";

    private static final String OWM_PRESSURE = "pressure";
    private static final String OWM_HUMIDITY = "humidity";
    private static final String OWM_WINDSPEED = "speed";
    private static final String OWM_WIND_DIRECTION = "deg";




    public static String[] getSimpleWeatherStringFromJson(Context context, String forcastJsonStr) throws JSONException {
        String[] parsedWeatherDate = null;
        JSONObject forcastJson = new JSONObject(forcastJsonStr);
        /*
        返回的Json是否有错误
         */
        if (forcastJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = forcastJson.getInt(OWM_MESSAGE_CODE);
            Log.i(TAG, "getSimpleWeatherStringFromJson: " + errorCode);
            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray weatherArray = forcastJson.getJSONArray(OWM_LIST);
        parsedWeatherDate = new String[weatherArray.length()];

        long locationDate = System.currentTimeMillis();
        long utcDate = SunshineDateUtils.getUTCDateFromLocal(locationDate);
        long starDay = SunshineDateUtils.normalizeDate(utcDate);

        for (int i = 0; i < weatherArray.length(); i++) {
            String date;
            String highAdnLow;

            long dateTimeMillis;
            double high;
            double low;
            String description;

            JSONObject dayForecast = weatherArray.getJSONObject(i);
            dateTimeMillis = starDay + SunshineDateUtils.DAY_IN_MILLIS * i;
            date = SunshineDateUtils.getFriendlyDateString(context, dateTimeMillis, false);
            JSONObject weatherObject =
                    dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCREPTION);


            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMP);
            high = temperatureObject.getDouble(OWM_MAX);
            low = temperatureObject.getDouble(OWM_MIN);
            highAdnLow = SunshineWeatherUtils.formatHighLows(context, high, low);
            parsedWeatherDate[i] = date + "-" + description + "-" + highAdnLow;

        }
        return parsedWeatherDate;
    }

    public static ContentValues[] getWeatherContentValuesFromJson(Context context, String jsonWeatherResponse) throws JSONException {
        JSONObject forcastJson = new JSONObject(jsonWeatherResponse);

        //判断Json对象是否错误
        if (forcastJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = forcastJson.getInt(OWM_MESSAGE_CODE);
            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }

        }
        JSONArray jsonWeatherArray = forcastJson.getJSONArray(OWM_LIST);

        JSONObject cityJson = forcastJson.getJSONObject(OWM_CITY);

        JSONObject cityCoord = cityJson.getJSONObject(OWM_COORD);
        double cityLatitude = cityCoord.getDouble(OWM_LATITUDE);
        double cityLongitude = cityCoord.getDouble(OWM_LONGITUDE);

        SunshinePreferences.setLocationDetails(context,cityLatitude,cityLongitude);
        ContentValues[] weatherContentValues = new ContentValues[jsonWeatherArray.length()];

        long normalizedUtcStartDay = SunshineDateUtils.getNormalizedUtcDateForToday();

        for (int i = 0 ; i < jsonWeatherArray.length();i++) {

            long dateTimeMillis;
            double pressure;
            int humidity;
            double windSpeed;
            double windDirection;

            double high;
            double low;

            int weatherId;

            JSONObject dayForecast = jsonWeatherArray.getJSONObject(i);
            dateTimeMillis = normalizedUtcStartDay + SunshineDateUtils.DAY_IN_MILLIS * i;
            pressure = dayForecast.getDouble(OWM_PRESSURE);
            humidity = dayForecast.getInt(OWM_HUMIDITY);
            windSpeed = dayForecast.getDouble(OWM_WINDSPEED);
            windDirection = dayForecast.getDouble(OWM_WIND_DIRECTION);

            JSONObject weatherObject =
                    dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            weatherId = weatherObject.getInt(OWM_WEATHER_ID);


            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMP);
            high = temperatureObject.getDouble(OWM_MAX);
            low = temperatureObject.getDouble(OWM_MIN);

            ContentValues weatherValues = new ContentValues();
            weatherValues.put(Contract.WeatherDataEntry.COLUMN_DATE,dateTimeMillis);
            weatherValues.put(Contract.WeatherDataEntry.COLUMN_HUMIDITY,humidity);
            weatherValues.put(Contract.WeatherDataEntry.COLUMN_PRESSURE,pressure);
            weatherValues.put(Contract.WeatherDataEntry.COLUMN_WIND_SPEED,windSpeed);
            weatherValues.put(Contract.WeatherDataEntry.COLUMN_DEGREES, windDirection);
            weatherValues.put(Contract.WeatherDataEntry.COLUMN_MAX_TEMP, high);
            weatherValues.put(Contract.WeatherDataEntry.COLUMN_MIN_TEMP, low);
            weatherValues.put(Contract.WeatherDataEntry.COLUMN_WEATHER_ID, weatherId);
            weatherContentValues[i] = weatherValues;

        }
        return weatherContentValues;




    }

}