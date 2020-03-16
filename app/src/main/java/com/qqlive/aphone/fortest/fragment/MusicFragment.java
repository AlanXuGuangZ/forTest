package com.qqlive.aphone.fortest.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qqlive.aphone.fortest.ITabClickListener;
import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.TabLayout;


public class MusicFragment extends BaseFragment implements ITabClickListener {
    private final static String TAG = "MusicFragment";
    private TabLayout mTopTabLayout;
    @Override
    public void fetchData() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_recciperec, container, false);
        return view;
    }

    public void onMenuItemClick() {

    }
    public BaseFragment getFragment() {
        return this;
    }

}
