package com.dark.xiaom.ringnews.fragments;

import android.os.Bundle;
import android.view.View;

import com.dark.xiaom.ringnews.pagers.NewsPager;

/**
 * Created by xiaom on 2017/2/10.
 * 内容碎片
 */

public class ContentFragment extends BaseFragment {
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {

        view  = new NewsPager(activity).initView();
        return view;
    }

    @Override
    public void initData() {

    }
}
