package com.qqlive.aphone.fortest.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.background.sync.ReminderTasks;
import com.qqlive.aphone.fortest.background.sync.ReminderUtilities;
import com.qqlive.aphone.fortest.background.sync.WaterReminderIntentService;
import com.qqlive.aphone.fortest.background.utilities.NotificationUtils;
import com.qqlive.aphone.fortest.background.utilities.PreferenceUtilites;

public class BackGroundMainActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener,View.OnClickListener{

    private static final String TAG = BackGroundMainActivity.class.getSimpleName()+"background";

    private TextView mWaterCountDisplay;
    private TextView mChargingCountDisplay;
    private ImageView mChargingImageView;
    private Button mTestNotificationView;

    private Toast mToast;
    IntentFilter mChargingIntentFilter;
    ChargingBroadcastReceiver mChargingReceiver;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_ground_main);

        /** get the view **/
        mWaterCountDisplay = (TextView)findViewById(R.id.tv_water_count);
        mChargingCountDisplay = (TextView)findViewById(R.id.tv_charging_reminder_count);
        mChargingImageView = (ImageView)findViewById(R.id.iv_power_increment);
        mTestNotificationView =(Button)findViewById(R.id.btn_test_notification);
        ImageButton ddd = (ImageButton) findViewById(R.id.ib_water_increment);
        ddd.setOnClickListener(this);
        mTestNotificationView.setOnClickListener(this);

        /**set the original values in the UI**/
        updateWaterCount();
        updateChargingReminderCount();

        ReminderUtilities.scheduleChargingReminder(this);

        /**setup the shared preference listener**/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        mChargingIntentFilter = new IntentFilter();
        mChargingIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        mChargingIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        mChargingReceiver = new ChargingBroadcastReceiver();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            BatteryManager batteryManager = (BatteryManager)getSystemService(BATTERY_SERVICE);
            showCharging(batteryManager.isCharging());
        }else {
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent currentBatteryStatusIntent = registerReceiver(null,ifilter);
            int batteryStatus = currentBatteryStatusIntent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
            boolean isCharging = batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING || batteryStatus == BatteryManager.BATTERY_STATUS_FULL;
            showCharging(isCharging);
        }

        registerReceiver(mChargingReceiver,mChargingIntentFilter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mChargingReceiver);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    private void updateChargingReminderCount() {
        int chargingReminders = PreferenceUtilites.getChargingReminderCount(this);
        String formattedChargingReminders = getResources().getQuantityString(
                R.plurals.charge_notification_count,chargingReminders,chargingReminders
        );
        mChargingCountDisplay.setText(formattedChargingReminders);
    }

    private void showCharging(boolean isCharging) {
        if (isCharging) {
            mChargingImageView.setImageResource(R.drawable.ic_power_pink_80px);
        }else {
            mChargingImageView.setImageResource(R.drawable.ic_power_grey_80px);
        }
    }




    private void updateWaterCount() {
        int waterCount = PreferenceUtilites.getWaterCount(this);
        if(waterCount >= 10000) {
            mWaterCountDisplay.setText("9999");
        }else {
            mWaterCountDisplay.setText(waterCount+"");
        }



    }

    public void incrementWater(View view) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(this,R.string.water_chug_toast,Toast.LENGTH_SHORT);
        mToast.show();

        Intent incrementWaterCountIntent = new Intent(this, WaterReminderIntentService.class);
        incrementWaterCountIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        startService(incrementWaterCountIntent);
    }

    public void testNotification(View view) {
        Log.i(TAG, "testNotification: ");
        NotificationUtils.remindUserBecauseCharging(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PreferenceUtilites.KEY_WATER_COUNT.equals(key)) {
            updateWaterCount();
        }else if (PreferenceUtilites.KEY_CHARGING_REMINDER_COUNT.equals(key)) {
            updateChargingReminderCount();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_water_increment:
                incrementWater(v);
                break;
            case R.id.btn_test_notification:
                testNotification(v);
                break;
        }
    }

    private class  ChargingBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean isCharging = (action.equals(Intent.ACTION_POWER_CONNECTED));
            showCharging(isCharging);
        }
    }
}
