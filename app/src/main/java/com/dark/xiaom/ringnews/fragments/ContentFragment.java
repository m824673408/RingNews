package com.dark.xiaom.ringnews.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dark.xiaom.ringnews.pagers.BasePager;
import com.dark.xiaom.ringnews.utils.JsonDetail;

import java.util.List;

/**
 * Created by xiaom on 2017/2/10.
 */

public class ContentFragment extends BaseFragment {
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView() {
        view  = new BasePager(activity).initView();
        return view;
    }

    @Override
    public void initData() {

    }
}
