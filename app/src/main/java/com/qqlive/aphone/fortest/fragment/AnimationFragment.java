package com.qqlive.aphone.fortest.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qqlive.aphone.fortest.R;

/**

 */
public class AnimationFragment extends BaseFragment {

    @Override
    public void fetchData() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animation, container, false);
        return view;
    }

    public void onMenuItemClick() {

    }
    public BaseFragment getFragment() {
        return this;
    }

}
