package com.qqlive.aphone.fortest.SunSine;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.qqlive.aphone.fortest.R;

public class SettingSunShineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_sun_shine);
        this.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);//保留上一个界面
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
