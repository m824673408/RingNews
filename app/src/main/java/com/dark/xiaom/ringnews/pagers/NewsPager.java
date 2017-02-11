package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dark.xiaom.ringnews.R;

/**
 * Created by xiaom on 2017/2/10.
 */

public class NewsPager extends BasePager {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private String type;
    private final int REFRESH_UI = 0;

    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.layout_news_viewpager,null);

        return view;
    }
}
