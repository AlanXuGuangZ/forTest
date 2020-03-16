package com.qqlive.aphone.fortest;

import com.qqlive.aphone.fortest.fragment.BaseFragment;

public class TabItem {
    /**
     * icon
     */
    public int imageResId;
    /**
     * text
     */
    public int lableResId;
    public Class<? extends BaseFragment>tagFragmentClz;
    /**
     *  @param imageResId
     * @param lableResId
     * @param tagFragmentClz
     *
     */
    public TabItem(int imageResId, int lableResId) {
        this.imageResId = imageResId;
        this.lableResId = lableResId;
    }

    public TabItem(int lableResId) {
        this.lableResId = lableResId;
    }

    public TabItem(int lableResId,Class<? extends BaseFragment> tagFragmentClz) {
        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragmentClz;
    }
    public TabItem(int imageResId, int lableResId, Class<? extends BaseFragment> tagFragmentClz) {
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragmentClz;
    }

}
