package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.dark.xiaom.ringnews.GlobalContants;
import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.domain.NoScrollViewPager;
import com.dark.xiaom.viewpagerindicator.TabPageIndicator;
import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewHelper;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by xiaom on 2017/6/6.
 */

public class WeiXinPager extends BasePager {

    private View view;
    private NoScrollViewPager noScrollViewPager;
    private TabPageIndicator tabPageIndicator;
    private ArrayList<WeiXinArticlePager> weixinViewList;

    public WeiXinPager(Activity activity) {
        super(activity);
    }


    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.layout_weixin_pager,null);
        noScrollViewPager = (NoScrollViewPager) view.findViewById(R.id.vp_weixin);
        tabPageIndicator = (TabPageIndicator) view.findViewById(R.id.vip_weixin);
        ShadowProperty sp = new ShadowProperty();
        ShadowViewHelper.bindShadowHelper(
                sp
                        .setShadowColor(0x77212121)
                        .setShadowDy(0)
                        .setShadowRadius(1)
                , view.findViewById(R.id.lay_line));
        initData();
        noScrollViewPager.setAdapter(new MyWeiXinArticleViewPager());
        tabPageIndicator.setViewPager(noScrollViewPager);
        return view;
    }

    @Override
    public void initData() {

        weixinViewList = new ArrayList<WeiXinArticlePager>();
        for (int i = 1; i <= 20;i ++ ){
            WeiXinArticlePager weiXinArticlePager = new WeiXinArticlePager(mActivity,i);
            weixinViewList.add(weiXinArticlePager);
        }
    }

    private class MyWeiXinArticleViewPager extends PagerAdapter {
        @Override
        public int getCount() {
            return GlobalContants.WEIXINTYPE.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return GlobalContants.WEIXINTYPE[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            WeiXinArticlePager weiXinArticlePager = weixinViewList.get(position);
            weiXinArticlePager.initData();
            container.addView(weiXinArticlePager.mRootView);
            return weiXinArticlePager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
