package com.qqlive.aphone.fortest;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class tabView extends LinearLayout {
    private ImageView mTabImage;
    private TextView mTabLable;
    private TextView mTopTabLable;

    public tabView(Context context) {
        super(context);
        initView(context);
    }
    public tabView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public tabView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        initView(context);
    }

    public void initView(Context context){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.tab_view,this,true);
        mTabImage = (ImageView)findViewById(R.id.tab_image);
        mTabLable = (TextView)findViewById(R.id.tab_lable);
    }
    public void initData(TabItem tabItem){
        mTabImage.setImageResource(tabItem.imageResId);
        mTabLable.setText(tabItem.lableResId);
    }
    public void onDataChanged(int badgeCount){

    }

}
