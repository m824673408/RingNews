package com.dark.xiaom.ringnews.domain;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentItem() != 0) {
            getParent().requestDisallowInterceptTouchEvent(true);// 用getParent去请求,
            // 不拦截
        } else {// 如果是第一个页面,需要显示侧边栏, 请求父控件拦截
            getParent().requestDisallowInterceptTouchEvent(false);// 拦截
        }
        return super.dispatchTouchEvent(ev);
    }
}
