package com.dark.xiaom.ringnews.fragments;

import android.view.View;

import com.dark.xiaom.ringnews.pagers.SettingContentPager;

/**
 * Created by xiaom on 2017/3/1.
 * 设置碎片
 */

public class SettingFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = new SettingContentPager(activity).initView();
        return view;
    }

    @Override
    public void initData() {

    }
}
