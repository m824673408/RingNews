package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.view.View;

/**
 * Created by xiaom on 2017/2/10.
 */

public class BasePager {
    public Activity mActivity;
    public View mRootView;
    public String type;
    public BasePager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    public BasePager(Activity activity,String type) {
        this.type = type;
        mActivity = activity;
        mRootView = initView();
    }

    public View initView() {

        return null;
    }


    public void initData(){

    }

}
