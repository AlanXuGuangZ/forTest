package com.qqlive.aphone.fortest.datafrominternet.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Scanner;


public class NetworkUtils {
    private final static String TAG = NetworkUtils.class.getSimpleName();
    private final static String GITHUB_BASE_URL = "https://api.github.com/search/repositories";
    private final static String GITHUB_PARAM_QUERY = "q";
    private final static String GITHUB_PARAM_SORT = "sort";
    private final static String GITHUB_sortBy = "stars";
    private final static String DYNAMIC_WEATHER_URL =
            "https://andfun-weather.udacity.com/weather";
    private final static String STATIC_WEATHER_URL =
            "https://andfun-weather.udacity.com/staticweather";
    private final static String WEATHER_BASE_URL = DYNAMIC_WEATHER_URL;
    private final static String WEATHER_QUERY_PARAM = "q";
    private final static String WEATHER_LAT_PARAM = "lat";
    private final static String WEATHER_LON_PARAM = "lon";
    private final static String WETHER_FORMT_PARAM = "mode";
    private final static String WETHER_UNITS_PARAM = "units";
    private final static String WETHER_DAYS_PARAM = "cnt";
    private final static String format = "json";
    private final static String units = "metric";
    private final static int numDays = 14;

    private static final String FORECAST_BASE_URL = STATIC_WEATHER_URL;



    /**
     * Build the URL used to query Github.
     * @param githubSearchQuery the keyword that will be queried for.
     * @return the URL to use to query the weather server.
     *
     */
    public static URL buildGithubUrl(String githubSearchQuery){

        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(GITHUB_PARAM_QUERY,githubSearchQuery)
                .appendQueryParameter(GITHUB_PARAM_SORT,GITHUB_sortBy)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    /**
     * 参数locationQuery来生产天气服务器的url
     * @param locationQuery
     * @return URL 用来请求天气服务器
     */

    public static URL buildSunshineUrl(String locationQuery){
        Uri buildUri =Uri.parse(WEATHER_BASE_URL).buildUpon()
                .appendQueryParameter(WEATHER_QUERY_PARAM,locationQuery)
                .appendQueryParameter(WETHER_FORMT_PARAM,format)
                .appendQueryParameter(WETHER_UNITS_PARAM,units)
                .appendQueryParameter(WETHER_DAYS_PARAM,Integer.toString(numDays))
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "buildSunshineUrl: "+ url);
        return url;

    }
    public static URL buildSunshineUrl(double lat , Double lon){
        //当前位置生产天气数据
            return null;

    }

    public static URL getUrl(Context context) {
        if (SunshinePreferences.isLocationLatLonAvailable(context)) {
            double[] preferredCoordinates = SunshinePreferences.getLocationCoordinates(context);
            double latitude = preferredCoordinates[0];
            double longitude = preferredCoordinates[1];
            return buildUrlWithLatitudeLongitude(latitude,longitude);
        }else {
            String locationQuery = SunshinePreferences.getPreferredWeatherLocation(context);
            return buildUrlWithLocationQuery(locationQuery);
        }
    }

    private static URL buildUrlWithLocationQuery(String locationQuery) {
        Uri weatherQueryUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(WEATHER_QUERY_PARAM,locationQuery)
                .appendQueryParameter(WETHER_FORMT_PARAM,format)
                .appendQueryParameter(WETHER_UNITS_PARAM,units)
                .appendQueryParameter(WETHER_DAYS_PARAM,Integer.toString(numDays))
                .build();
        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            Log.i(TAG, "buildUrlWithLocationQuery: url"+weatherQueryUrl);
            return weatherQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }


    }

    private static URL buildUrlWithLatitudeLongitude(double latitude, double longitude) {

        Uri weatherQueryUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(WEATHER_LAT_PARAM,String.valueOf(latitude))
                .appendQueryParameter(WEATHER_LON_PARAM,String.valueOf(longitude))
                .appendQueryParameter(WETHER_FORMT_PARAM,String .valueOf(format))
                .appendQueryParameter(WETHER_UNITS_PARAM,String .valueOf(units))
                .appendQueryParameter(WETHER_DAYS_PARAM,Integer.toString(numDays))
                .build();
        try {
            URL weatherQueryUrl = new URL(weatherQueryUri.toString());
            Log.i(TAG, "buildUrlWithLatitudeLongitude: url"+weatherQueryUri);
            return weatherQueryUrl;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * this method returns the entire result form the http response.
     * @param url the URL to fetch the http response from
     * @return the contents of the http response
     * @throws IOException Related to network and stream reading
     */

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        Log.i(TAG, "getResponseFromHttpUrl: "+url);

        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            }else {
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }


    }

}

