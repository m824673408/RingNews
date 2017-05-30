package com.dark.xiaom.ringnews.domain;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;


/**
 * Created by xiaom on 2017/3/24.
 */

public class NoScrollViewPager extends ViewPager {


    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}
