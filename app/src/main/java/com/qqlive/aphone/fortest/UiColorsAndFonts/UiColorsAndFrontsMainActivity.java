package com.qqlive.aphone.fortest.UiColorsAndFonts;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.qqlive.aphone.fortest.R;

import com.qqlive.aphone.fortest.databinding.ActivityUiColorsAndFrontsMainBinding;

public class UiColorsAndFrontsMainActivity extends AppCompatActivity implements View.OnClickListener {
//    ActivityUiColorsAndFrontsMainBinding mainBinding;
    private TextView mColorFontTextView;
    private TextView mResLayoutTextView;
    private TextView mSelectorTextView;
    private TextView mStyleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_ui_colors_and_fronts_main);
        setContentView(R.layout.activity_ui_colors_and_fronts_main);
        mColorFontTextView = (TextView)findViewById(R.id.colorAndFont);
        mResLayoutTextView = (TextView)findViewById(R.id.responsiveLayouts);
        mStyleTextView = (TextView)findViewById(R.id.style);
        mSelectorTextView = (TextView)findViewById(R.id.touchSelector);

        mColorFontTextView.setOnClickListener(this);
        mResLayoutTextView.setOnClickListener(this);
        mSelectorTextView.setOnClickListener(this);
        mStyleTextView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.colorAndFont:
                Intent colorAndFontIntent = new Intent(this,ColorFontActivity.class);
                startActivity(colorAndFontIntent);
                break;
            case R.id.style:
                Intent styleIntent = new Intent(this,StyleActivity.class);
                startActivity(styleIntent);
                break;
            case R.id.responsiveLayouts:
                Intent responsiveLayoutIntent = new Intent(this,ResponsiveLayoutActivity.class);
                startActivity(responsiveLayoutIntent);
                break;
            case R.id.touchSelector:
                Intent touchSelectorIntent = new Intent(this,SelectorsActivity.class);
                startActivity(touchSelectorIntent);
                break;
            default:
                break;
        }
    }
}
