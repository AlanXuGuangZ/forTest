package com.qqlive.aphone.fortest.SunSine;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.TabItem;
import com.qqlive.aphone.fortest.databinding.ActivityBoardPassMainBinding;
import com.qqlive.aphone.fortest.databinding.ActivityDetailBinding;
import com.qqlive.aphone.fortest.datafrominternet.utilities.SunshineDateUtils;
import com.qqlive.aphone.fortest.datafrominternet.utilities.SunshineWeatherUtils;
import com.qqlive.aphone.fortest.mydb.Contract;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    public static final String[] WEATHER_DETAIL_PROJECTION = {
            Contract.WeatherDataEntry.COLUMN_DATE,
            Contract.WeatherDataEntry.COLUMN_MAX_TEMP,
            Contract.WeatherDataEntry.COLUMN_MIN_TEMP,
            Contract.WeatherDataEntry.COLUMN_HUMIDITY,
            Contract.WeatherDataEntry.COLUMN_PRESSURE,
            Contract.WeatherDataEntry.COLUMN_WIND_SPEED,
            Contract.WeatherDataEntry.COLUMN_DEGREES,
            Contract.WeatherDataEntry.COLUMN_WEATHER_ID
    };


    public static final int INDEX_WEATHER_DATE = 0;
    public static final int INDEX_WEATHER_MAX_TEMP = 1;
    public static final int INDEX_WEATHER_MIN_TEMP = 2;
    public static final int INDEX_WEATHER_HUMIDITY = 3;
    public static final int INDEX_WEATHER_PRESSURE = 4;
    public static final int INDEX_WEATHER_WIND_SPEED = 5;
    public static final int INDEX_WEATHER_DEGREES = 6;
    public static final int INDEX_WEATHER_CONDITION_ID = 7;


    private String mForcast;
    TextView mWeatherDateTextView;
    TextView mWeatherDescriptionTextView;
    TextView mWeatherHighTempTextView;
    TextView mWeatherLowTempTextView;
    TextView mWeatherPressureTextView;
    TextView mWeatherHumidityTextView;
    TextView mWeatherWindTextView;
    TextView mWeatherDisplayTextView;

    private Toast mToast;
//    private int mDatailActivityNumber = 1 ; //首次进入Activity记1
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callBacks";
    private final static String TAG = DetailActivity.class.getSimpleName()+"Sunshine";
    private static final String ON_CREATE = "onCreate";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_DESTROY = "onDestroy";
    private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";

    private static final ArrayList<String> mLifecycleCallbacks = new ArrayList<>();
    private static final ArrayList<String>mForcasts = new ArrayList<>();

    private Uri mUri;

    private static final int ID_DETAIL_LOADER = 353;

    ActivityDetailBinding mBinding;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail);
//        mWeatherDateTextView = (TextView)findViewById(R.id.tv_date_weather);
//        mWeatherDescriptionTextView = (TextView)findViewById(R.id.tv_description_weather);
//        mWeatherHighTempTextView = (TextView)findViewById(R.id.tv_high_temp_weather);
//        mWeatherLowTempTextView = (TextView)findViewById(R.id.tv_low_temp_weather);
//        mWeatherHumidityTextView = (TextView)findViewById(R.id.tv_humidity_weather);
//        mWeatherPressureTextView = (TextView)findViewById(R.id.tv_pressure_weather);
//        mWeatherWindTextView = (TextView)findViewById(R.id.tv_wind_weather) ;
//       mWeatherDisplayTextView = (TextView)findViewById(R.id.tv_oldWeatherData);


        mUri = getIntent().getData();
        Log.i(TAG, "onCreate: ****************uri********"+mUri);

        if (mUri == null) throw new NullPointerException("URI for DetailActivity can not be null");

        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER,null,this);



//        Intent intentThatStartedThisActivity = getIntent();
//        if (intentThatStartedThisActivity!=null){
//            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
//                mForcast = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
//                mForcasts.add(0,mForcast);
//                mWeatherDisplayTextView.setText(mForcast);
//            }else {
//                Toast.makeText(this,"Intent没有内容",Toast.LENGTH_SHORT).show();
//            }
//        }else {
//            Toast.makeText(this,"没有Intent",Toast.LENGTH_SHORT).show();
//        }
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)) {
                String allPreviousLifecycleCallbacks = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
                mWeatherDisplayTextView.setText(allPreviousLifecycleCallbacks);
            }
        }
        //不是同一个界面的生命周期要清除mLifecycleCallbacks里面的数据
//        if ( mForcasts.size() != 0 && mForcasts.size() !=1){
//            if (!mForcasts.get(0).equals(mForcasts.get(1)))
//                mLifecycleCallbacks.clear();
//        }
//        for(int i = mLifecycleCallbacks.size()-1;i >= 0;i--) {
//            mWeatherDisplayTextView.append(mLifecycleCallbacks.get(i)+"\n");
//        }
//        mLifecycleCallbacks.clear();
        logAndAppend(ON_CREATE);

   }
    @Override
    protected void onStart(){
        super.onStart();
        logAndAppend(ON_START);
    }
    @Override
    protected void onResume(){
        super.onResume();
        logAndAppend(ON_RESUME);
    }
    @Override
    protected void onPause(){
        super.onPause();
        logAndAppend(ON_PAUSE);
    }
    @Override
    protected void onStop(){
        super.onStop();
        mLifecycleCallbacks.add(0,ON_STOP);
        logAndAppend(ON_STOP);
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        logAndAppend(ON_RESTART);
    }
    @Override
    protected void onDestroy(){
        if (mToast != null){
            mToast.cancel();
        }
        super.onDestroy();
        mLifecycleCallbacks.add(0,ON_DESTROY);
        logAndAppend(ON_DESTROY);
        mToast=Toast.makeText(this,ON_DESTROY,Toast.LENGTH_LONG);
        mToast.show();
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        logAndAppend(ON_SAVE_INSTANCE_STATE);
       // String lifecycleDisplayTextViewContents = mWeatherDisplayTextView.getText().toString();
      //  outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY,lifecycleDisplayTextViewContents);
    }
    private Intent createShareForcastIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setText(mForcast+FORECAST_SHARE_HASHTAG)
                .getIntent();
        return shareIntent;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForcastIntent());

        return true;
    }
    private void logAndAppend(String lifecycleEvent) {
        Log.d(TAG, "Lifecycle Event: " + lifecycleEvent);

//        mWeatherDisplayTextView.append(lifecycleEvent + "\n");
    }

    /**
     *
     * @param view The View that was clicked. In this case, it is the Button from our layout.
     */
    public void resetLifecycleDisplay(View view) {
        mWeatherDisplayTextView.setText("Lifecycle callbacks:\n");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ID_DETAIL_LOADER:
                return new CursorLoader(this,
                        mUri,
                        WEATHER_DETAIL_PROJECTION,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented:" + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            cursorHasValidData = true;
        }
        if (!cursorHasValidData) {
             /* No data to display, simply return and do nothing */
            return;
        }

        long localDateMidnightGmt = data.getLong(INDEX_WEATHER_DATE);
        String dataText = SunshineDateUtils.getFriendlyDateString(this,localDateMidnightGmt,true);
        Log.i(TAG, "onLoadFinished: mWeatherDateTextView***********datatext："+dataText);
        mBinding.tvDetailDateWeather.setText(dataText);

        int weatherId = data.getInt(INDEX_WEATHER_CONDITION_ID);
        String description = SunshineWeatherUtils.getStringForWeatherCondition(this,weatherId);
        mBinding.tvDetailWeatherDescription.setText(description);

        int weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId);
        mBinding.ivDetailWeatherIcon.setImageResource(weatherImageId);

        double highInCelsius = data.getDouble(INDEX_WEATHER_MAX_TEMP);
        String highString = SunshineWeatherUtils.formatTemperature(this,highInCelsius);
        mBinding.tvDetailMaxTemp.setText(highString);

        double lowInCelsius = data.getDouble(INDEX_WEATHER_MIN_TEMP);
        String lowString = SunshineWeatherUtils.formatTemperature(this,lowInCelsius);
        mBinding.tvDetailMinTemp.setText(lowString);

        float humidity = data.getFloat(INDEX_WEATHER_HUMIDITY);
        String humidityString = getString(R.string.format_humidity,humidity);
        mBinding.tvDetailHumidityData.setText(humidityString);

        float windSpeed = data.getFloat(INDEX_WEATHER_WIND_SPEED);
        float windDirection = data.getFloat(INDEX_WEATHER_DEGREES);
        String windString = SunshineWeatherUtils.getFormattedWind(this,windSpeed,windDirection);
        mBinding.tvDetailWindData.setText(windString);

        float pressure = data.getFloat(INDEX_WEATHER_PRESSURE);
        String pressureString = getString(R.string.format_pressure,pressure);
        mBinding.tvDetailPressureData.setText(pressureString);

        mForcast = String.format("%s - %s - %s/%s",
                dataText,description,highString,lowString);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
