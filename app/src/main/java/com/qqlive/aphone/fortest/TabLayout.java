package com.qqlive.aphone.fortest;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qqlive.aphone.fortest.fragment.BaseFragment;

import java.util.ArrayList;


public class TabLayout extends LinearLayout implements View.OnClickListener {

    private ArrayList<TabItem> tabs;
    private OnTabClickListener listener;
    private View selectView;
    private int tabCount;
    private Toast mOnclickToast;


    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public TabLayout(Context context) {
        super(context);
        initView();
    }

    /*
    public TabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }
    */

    private void initView(){
        //setOrientation(HORIZONTAL);
    }
    public void setCurrentTab(int i) {
        if (i < tabCount && i >= 0) {
            View view = getChildAt(i);
            if (selectView != view) {
                view.setSelected(true);
                if (selectView != null) {
                    selectView.setSelected(false);
                }
                selectView = view;
            }
        }

    }

    public void onDataChanged(int i, int badgeCount) {
        if (i < tabCount && i >= 0) {
            tabView view = (tabView) getChildAt(i);
            view.onDataChanged(badgeCount);
        }
    }
    public void initData(ArrayList<TabItem>tabs,OnTabClickListener listener){
        this.tabs=tabs;
        this.listener=listener;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight=1;
        if(tabs!=null&&tabs.size()>0){
            tabCount=tabs.size();
            tabView mTabView=null;
            for(int i=0;i< tabs.size();i++){
                mTabView= new tabView(getContext());
                mTabView.setTag(tabs.get(i));
                mTabView.setOnClickListener(this);
                mTabView.initData(tabs.get(i));
                addView(mTabView,params);
            }

        }else{
            throw new IllegalArgumentException("tabs can not be empty");
        }

    }


    @Override
    public void onClick(View v) {
        Log.d("onTabClick: Tableye" , "v");

        listener.onTabClick((TabItem) v.getTag());

    }

    public interface OnTabClickListener{
        void onTabClick(TabItem tabItem);
    }
}
