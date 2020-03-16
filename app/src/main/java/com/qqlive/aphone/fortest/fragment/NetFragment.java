package com.qqlive.aphone.fortest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qqlive.aphone.fortest.ITabClickListener;
import com.qqlive.aphone.fortest.R;
import com.qqlive.aphone.fortest.ToyBoy;

public class NetFragment extends BaseFragment implements ITabClickListener {

    @Override
    public void fetchData() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net, container, false);
        return view;
    }
    @Override
    public void onMenuItemClick() {

    }
    @Override
    public BaseFragment getFragment() {
        return this;
    }
}

