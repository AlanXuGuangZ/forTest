package com.qqlive.aphone.fortest.visualizerpreferences;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.visualizerpreferences.AudioVisuals.AudioInputReader;
import com.qqlive.aphone.fortest.visualizerpreferences.AudioVisuals.VisualizerView;

public class VisualizerMainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private static final int MY_PERMISSION_RECORD_AUDIO_REQUEST_DODE = 88;
    private static final String TAG = VisualizerMainActivity.class.getSimpleName();

    private VisualizerView mVisualizerView ;
    private AudioInputReader mAudioInputReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizer_main);
        mVisualizerView = (VisualizerView)findViewById(R.id.activity_visualizer);

//        Log.i(TAG, "onCreate: mVisualizerView=null");
        setSharedPreferences();
        setupPermissions();

    }
    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
        if (mAudioInputReader != null){
            Log.i(TAG, "onPause: mAudioInputReader is finishing");
            mAudioInputReader.shutdown(isFinishing());
        }
    }
    @Override
    protected void onResume(){
        Log.i(TAG, "onResume: ");
        super.onResume();
        if (mAudioInputReader != null) {
            Log.i(TAG, "onResume: mAudioInputReader != null");
            mAudioInputReader.restart();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();

       PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i(TAG, "onRestart: ");
        /**
         * 下面被注释的代码，是为了解决Action Bar点击back（非home）是，返回父Activity没有调用onCreate方法，导致设置没有作用而写的。
         * 之所以被注释，是因为使用了更好的处理，Activity实现SharedPreferences.OnSharedPreferenceChangeListener方法。实时监听sharedPreference文件的key—value值的改变
         */
//        setSharedPreferences();
    }
    public void  onRequestPermissionsResult(int requestCode,
                                            @NonNull String permissions[],@NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_RECORD_AUDIO_REQUEST_DODE:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.i(TAG, "onRequestPermissionsResult: AudioInputReader");
                    mAudioInputReader = new AudioInputReader(mVisualizerView,this);
                }else {
                    Toast.makeText(this,"Permission for audio not granted. Visualizer can't run",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }


    private void setupPermissions() {
        Log.i(TAG, "setupPermissions: ");
        //if we don't have the record audio permission...
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
                // Ask again, nicely, for the permissions.
                String[] permissionWeNeed = new  String[]{ Manifest.permission.RECORD_AUDIO};
                Log.i(TAG, "setupPermissions: requetPermissions");
                requestPermissions(permissionWeNeed,MY_PERMISSION_RECORD_AUDIO_REQUEST_DODE);
            }
        }else {
            Log.i(TAG, "setupPermissions: new AudioInputReader");
            mAudioInputReader = new AudioInputReader(mVisualizerView,this);
        }
    }

    private void setSharedPreferences() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i(TAG, "defaultSetup: "+sharedPreferences.getBoolean(getString(R.string.pref_show_bass_title),true));
        mVisualizerView.setShowBass(sharedPreferences.getBoolean(getString(R.string.pref_show_bass_key),getResources().getBoolean(R.bool.pref_show_bass_default)));
        mVisualizerView.setShowMid(sharedPreferences.getBoolean(getString(R.string.pref_show_mid_key),getResources().getBoolean(R.bool.pref_show_mid_default)));
        mVisualizerView.setShowTreble(sharedPreferences.getBoolean(getString(R.string.pref_show_treble_key),getResources().getBoolean(R.bool.pref_show_treble_default)));
        mVisualizerView.setMinSizeScale(Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_size_key),getString(R.string.pref_size_default))));
        mVisualizerView.setColor(sharedPreferences.getString(getString(R.string.pref_color_key),getString(R.string.pref_color_red_value)));
//        loadColorFromPrefrences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }


    public boolean onCreateOptionsMenu(Menu menu){
        Log.i(TAG, "onCreateOptionsMenu: click Action bar");
        getMenuInflater().inflate(R.menu.visualizer, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int menuItemThatWasSelected = item.getItemId();
        switch (menuItemThatWasSelected){
            case R.id.action_settings:
                Toast.makeText(VisualizerMainActivity.this,"intent Settings activity:"+menuItemThatWasSelected,Toast.LENGTH_LONG).show();
                Intent SettingsIntent = new Intent(this, SettingsVisualizerActivity.class);
                startActivity(SettingsIntent);
                return true;
            default:
                break;
        }
//        if (menuItemThatWasSelected == R.id.action_reset) {
//        Log.i(TAG, "onOptionsItemSelected: click setting bottom");
//        Toast.makeText(VisualizerMainActivity.this,"intent Settings activity:"+menuItemThatWasSelected,Toast.LENGTH_LONG).show();
//        Intent SettingsIntent = new Intent(this, SettingsVisualizerActivity.class);
//        startActivity(SettingsIntent);
//        return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case "show_bass":
                Log.i(TAG, "onSharedPreferenceChanged: changed the checkbox of bass value");
                 mVisualizerView.setShowBass(sharedPreferences.getBoolean(key,getResources().getBoolean(R.bool.pref_show_bass_default)));
                 break;
            case "show_mid":
                Log.i(TAG, "onSharedPreferenceChanged: changed the checkbox of mid value");
                 mVisualizerView.setShowMid(sharedPreferences.getBoolean(key,getResources().getBoolean(R.bool.pref_show_mid_default)));
                break;
            case "show_treble":
                Log.i(TAG, "onSharedPreferenceChanged: changed the checkbox of treble value");
                mVisualizerView.setShowTreble(sharedPreferences.getBoolean(key,getResources().getBoolean(R.bool.pref_show_treble_default)));
                break;
            case "color":
                Log.i(TAG, "onSharedPreferenceChanged: changed the list of color value:"+sharedPreferences.getString(key,getString(R.string.pref_color_red_value)));
                mVisualizerView.setColor(sharedPreferences.getString(key,getString(R.string.pref_color_red_value)));
                break;
            case "size":
                Log.i(TAG, "onSharedPreferenceChanged: changed the size of scale value:"+Float.parseFloat(sharedPreferences.getString(key,getString(R.string.pref_size_default))));
                mVisualizerView.setMinSizeScale(Float.parseFloat(sharedPreferences.getString(key,getString(R.string.pref_size_default))));
                break;
            default:
                break;
        }
//        if (key.equals(getString(R.string.pref_show_bass_key))) {
//            Log.i(TAG, "onSharedPreferenceChanged: changed the checkbox value");
//            mVisualizerView.setShowBass(sharedPreferences.getBoolean(key,getResources().getBoolean(R.bool.pref_show_bass_default)));
//
//        }
    }
}
