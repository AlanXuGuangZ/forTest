package com.qqlive.aphone.fortest.SunSine;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qqlive.aphone.fortest.LeakCanary.LeakCanaryApplication;
import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.SunSine.sync.SunshineSyncUtils;
import com.qqlive.aphone.fortest.datafrominternet.utilities.NetworkUtils;
import com.qqlive.aphone.fortest.datafrominternet.utilities.OpenWeatherJsonUtuils;
import com.qqlive.aphone.fortest.datafrominternet.utilities.SunshinePreferences;
import com.qqlive.aphone.fortest.mydb.Contract;


import java.net.URL;

public class SunShineMainActivity extends AppCompatActivity implements SunShineAdapter.forcastClickListener,
        LoaderManager.LoaderCallbacks<Cursor>,SharedPreferences.OnSharedPreferenceChangeListener {
    private final static String TAG = SunShineMainActivity.class.getSimpleName()+"Sunshine";
    private TextView mWeatherTextView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndictorProgressBar;
    private final static int WEATHER_DATA = 12;
//    private String[] dummyWeatherDatas = {
//            "Today, May 17 - Clear - 17°C / 15°C",
//            "Tomorrow - Cloudy - 19°C / 15°C",
//            "Thursday - Rainy- 30°C / 11°C",
//            "Friday - Thunderstorms - 21°C / 9°C",
//            "Saturday - Thunderstorms - 16°C / 7°C",
//            "Sunday - Rainy - 16°C / 8°C",
//            "Monday - Partly Cloudy - 15°C / 10°C",
//            "Tue, May 24 - Meatballs - 16°C / 18°C",
//            "Wed, May 25 - Cloudy - 19°C / 15°C",
//            "Thu, May 26 - Stormy - 30°C / 11°C",
//            "Fri, May 27 - Hurricane - 21°C / 9°C",
//            "Sat, May 28 - Meteors - 16°C / 7°C",
//            "Sun, May 29 - Apocalypse - 16°C / 8°C",
//            "Mon, May 30 - Post Apocalypse - 15°C / 10°C",
//    };
    private RecyclerView mRecyclerView;
    private SunShineAdapter mForcastAdapter;
    private Context context;
    private Toast mToast;
    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;
    private static final int FORECAST_LOADER_ID = 12;
    private int mPosition = RecyclerView.NO_POSITION;

    public static final String [] MAIN_FORECAST_PROJECTION = {
            Contract.WeatherDataEntry.COLUMN_DATE,
            Contract.WeatherDataEntry.COLUMN_MAX_TEMP,
            Contract.WeatherDataEntry.COLUMN_MIN_TEMP,
            Contract.WeatherDataEntry.COLUMN_WEATHER_ID,
    };

    public static final int INDEX_WEATHER_DATE = 0;
    public static final int INDEX_WETHER_MAX_TEMP = 1;
    public static final int INDEX_WETHER_MIN_TEMP = 2;
    public static final int INDEX_WEATHER_CONDITION_ID = 3;

    private static final int ID_FORECAST_LOADER = 44;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun_shine_main);
        getSupportActionBar().setElevation(0f);
        LeakCanaryApplication.getRefWatcher().watch(this);
        mWeatherTextView = (TextView)findViewById(R.id.data);
        mErrorMessageDisplay = (TextView)findViewById(R.id.tv_error_message_sunshine_display) ;
        mLoadingIndictorProgressBar = (ProgressBar)findViewById(R.id.pd_loading_sunshine_indicate);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycleview_forcast);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mForcastAdapter = new SunShineAdapter(this,this);
        mRecyclerView.setAdapter(mForcastAdapter);
        getSupportLoaderManager().initLoader(WEATHER_DATA,null,SunShineMainActivity.this);
//        initWeatherData();
//        loadWeatherData();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        SunshineSyncUtils.initialize(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onRestart(){
        super.onRestart();
        if (PREFERENCES_HAVE_BEEN_UPDATED) {
            Log.i(TAG, "onStart: preferences were updated");
            loadWeatherData();
            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }


//    private void initWeatherData(){
//        mWeatherTextView = (TextView)findViewById(R.id.tv_weather_data);
//        for (String dummyWeatherData : dummyWeatherDatas){
//            mWeatherTextView.append(dummyWeatherData+"\n\n\n");
//        }
//    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void loadWeatherData(){
        Log.i(TAG, "loadWeatherData: ");
        SunshineSyncUtils.initialize(this);
//        LoaderManager loaderManager = getSupportLoaderManager();
//        Loader<String[]> weatherDataLoader = loaderManager.getLoader(WEATHER_DATA);
//        if (weatherDataLoader == null){
//            loaderManager.initLoader(WEATHER_DATA,null,this);
//        }else {
//            loaderManager.restartLoader(WEATHER_DATA,null,this);
//        }
//        new FetchWeatherTask().execute(location);
    }
    private void openPreferredLocationInMap() {
        double[] coords = SunshinePreferences.getLocationCoordinates(this);
        String posLat = Double.toString(coords[0]);
        String posLong = Double.toString(coords[1]);
        Uri geoLocation = Uri.parse("geo:" + posLat + " ," + posLong);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else {

            if (mToast != null){
                mToast.cancel();
            }else {
                mToast = Toast.makeText(this,"couldn't call "+geoLocation.toString() + ".no receiving app  installed!",Toast.LENGTH_LONG);
                mToast.show();
            }
        }
    }

    @Override
    public void onForcastClick(long clickedWeather) {
//        if (mToast != null){
//            mToast.cancel();
//        }
//        mToast = Toast.makeText(this,"Item #"+clickedWeather,Toast.LENGTH_LONG);
//        mToast.show();
        Intent DetailIntent = new Intent(this,DetailActivity.class);
        Uri uriForDateClicked = Contract.WeatherDataEntry.buildWeatherUriWithDate(clickedWeather);
        mToast = Toast.makeText(this,uriForDateClicked.toString(),Toast.LENGTH_SHORT);
        mToast.show();
        Log.i(TAG, "onForcastClick: *************uri************"+uriForDateClicked.toString());
        DetailIntent.setData(uriForDateClicked);
        startActivity(DetailIntent);
    }

    /**
     * 异步加载器实现网络请求
     * @param id
     * @param args
     * @return
     */

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle args) {

//        return new AsyncTaskLoader<String[]>(this) {
//            String[] mWeatherDate = null ;
//            @Override
//            public void onStartLoading(){
//                Log.i(TAG, "onStartLoading: ");
//                if (mWeatherDate != null) {
//
//                    deliverResult(mWeatherDate);
//                }else {
//                    mLoadingIndictorProgressBar.setVisibility(View.VISIBLE);
//                    forceLoad();
//                }
//            }
//            @Override
//            public String[] loadInBackground() {
//                String locationQuery = SunshinePreferences.getPreferredWeatherLocation(SunShineMainActivity.this);
//                URL weatherRequestUrl = NetworkUtils.buildSunshineUrl(locationQuery);
//                try {
//                    String jsonWeatherResponse = NetworkUtils
//                            .getResponseFromHttpUrl(weatherRequestUrl);
//                    String[] simpleJsonWeatherDate = OpenWeatherJsonUtuils
//                            .getSimpleWeatherStringFromJson(SunShineMainActivity.this,jsonWeatherResponse);
//                    Log.i(TAG, "doInBackground: return simp;eJsonWeatherdate"+simpleJsonWeatherDate);
//                    return simpleJsonWeatherDate;
//                }catch (Exception e){
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//            public void deliverResult(String[] weatherDate){
//                mWeatherDate = weatherDate;
//                super.deliverResult(weatherDate);
//            }
//        };
        switch (id) {
            case FORECAST_LOADER_ID:
                Log.i(TAG, "onCreateLoader: ");
                Uri forecaseQueryUri = Contract.WeatherDataEntry.CONTENT_URI;
                String sortOrder = Contract.WeatherDataEntry.COLUMN_DATE + " ASC";

                String selection = Contract.WeatherDataEntry.getSqlSelectForTodayOnwards();

                return new CursorLoader(this,
                        forecaseQueryUri,
                        MAIN_FORECAST_PROJECTION,
                        selection,
                        null,
                        sortOrder);
            default:
                throw new RuntimeException("Loader Nort Implemented: "+ id);


        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mForcastAdapter.setmWeatherData(data);
        if (mPosition == RecyclerView.NO_POSITION) {
            mPosition =0;
            Log.i(TAG, "onLoadFinished: mPosition == NO_POSITION");
        }
        mRecyclerView.smoothScrollToPosition(mPosition);
        if (data.getCount() !=0){
            showWeatherDataView();
            mLoadingIndictorProgressBar.setVisibility(View.INVISIBLE);
        }else {
            mLoadingIndictorProgressBar.setVisibility(View.INVISIBLE);
            showErrorMessageView();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mForcastAdapter.setmWeatherData(null);
    }

//    @Override
//    public void onLoadFinished(Loader<String[]> loader, String[] data) {
//        mLoadingIndictorProgressBar.setVisibility(View.INVISIBLE);
//        Log.i(TAG, "onPostExecute: weatherData:"+data);
//        if (data != null){
////                for (String weatherStr:weatherData){
////                    mWeatherTextView.append(weatherStr + "\n\n\n");
////
////                }
//            showWeatherDataView();
//            mForcastAdapter.setmWeatherData(data);
//        }else {
//            showErrorMessageView();
//        }
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<String[]> loader) {
//
//    }


//    public class FetchWeatherTask extends AsyncTask<String,Void,String[]> {
//
//        @Override
//        protected String[] doInBackground(String... params) {
//            if (params.length == 0 ){
//                return  null;
//            }
//            String location = params[0];
//            URL weatherRequestUrl = NetworkUtils.buildSunshineUrl(location);
//            Log.i(TAG, "doInBackground: "+weatherRequestUrl);
//            try {
//                String jsonWeatherResponse = NetworkUtils
//                        .getResponseFromHttpUrl(weatherRequestUrl);
//                String[] simpleJsonWeatherDate = OpenWeatherJsonUtuils
//                        .getSimpleWeatherStringFromJson(SunShineMainActivity.this,jsonWeatherResponse);
//                Log.i(TAG, "doInBackground: return simp;eJsonWeatherdate"+simpleJsonWeatherDate);
//                return simpleJsonWeatherDate;
//            }catch (Exception e){
//                e.printStackTrace();
//                return null;
//            }
//        }
//        @Override
//        protected void onPreExecute(){
//            Log.i(TAG, "onPreExecute: ");
//            super.onPreExecute();
//            mLoadingIndictorProgressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String[] weatherData){
//            mLoadingIndictorProgressBar.setVisibility(View.INVISIBLE);
//            Log.i(TAG, "onPostExecute: weatherData:"+weatherData);
//            if (weatherData != null){
////                for (String weatherStr:weatherData){
////                    mWeatherTextView.append(weatherStr + "\n\n\n");
////
////                }
//                showWeatherDataView();
//                mForcastAdapter.setmWeatherData(weatherData);
//            }else {
//                showErrorMessageView();
//            }
//        }
//
//    }

    public void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndictorProgressBar.setVisibility(View.VISIBLE);
    }

    public void showWeatherDataView(){
        Log.i(TAG, "showWeatherDataView: ");
       mRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
    }
    public void showErrorMessageView(){
        Log.i(TAG, "showErrorMessageView: ");
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Log.i(TAG, "onCreateOptionsMenu: 更新按钮");
        getMenuInflater().inflate(R.menu.forcast,menu);
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Log.i(TAG, "onOptionsItemSelected: click on item");
        int menuItemThatWasSelected = item.getItemId();
//        if (menuItemThatWasSelected == R.id.action_refresh){
//            Log.i(TAG, "onOptionsItemSelected: clickMenu==ture");
//            mForcastAdapter.setmWeatherData(null);
////            loadWeatherData();
////            getSupportLoaderManager().initLoader(WEATHER_DATA,null,SunShineMainActivity.this);
//            loadWeatherData();
//            return true;
//        }
        switch (menuItemThatWasSelected){
            case R.id.action_refresh:
                Log.i(TAG, "onOptionsItemSelected:  click refresh");
//                mForcastAdapter.setmWeatherData(null);
                loadWeatherData();
                return true;
            case R.id.action_forcast_settings:
                Log.i(TAG, "onOptionsItemSelected: click set");
                Intent settingIntent = new Intent(this,SettingSunShineActivity.class);
                startActivity(settingIntent);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFERENCES_HAVE_BEEN_UPDATED = true;
    }
}
