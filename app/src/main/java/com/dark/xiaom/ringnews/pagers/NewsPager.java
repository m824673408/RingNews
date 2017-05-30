package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.GlobalContants;
import com.dark.xiaom.ringnews.domain.NoScrollViewPager;
import com.dark.xiaom.viewpagerindicator.TabPageIndicator;
import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaom on 2017/2/10.
 */

public class NewsPager extends BasePager {
    private List<String> titleList;
    private final int REFRESH_UI = 0;
    private TabPageIndicator tabPageIndicator;
    private NoScrollViewPager noScrollViewPager;
    private List<MenuPager> mPagerList;
    private ImageButton imageButton;

    public NewsPager(Activity activity) {
        super(activity);

    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.layout_news_viewpager,null);
        tabPageIndicator = (TabPageIndicator) view.findViewById(R.id.vpi);
        imageButton = (ImageButton) view.findViewById(R.id.img_btn);
        tabPageIndicator.setTypeFace(Typeface.createFromAsset(mActivity.getAssets(), "fonts/micro.ttf"));
        ShadowProperty sp = new ShadowProperty();
        ShadowViewHelper.bindShadowHelper(
                sp
                        .setShadowColor(0x77212121)
                        .setShadowDy(0)
                        .setShadowRadius(1)
                , view.findViewById(R.id.lay_line));
        noScrollViewPager = (NoScrollViewPager) view.findViewById(R.id.vp_news_list);
        initData();
        noScrollViewPager.setAdapter(new MyPagerAdapter());
        tabPageIndicator.setViewPager(noScrollViewPager, 0);
        return view;
    }


    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return titleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            MenuPager tabDetailPager = mPagerList.get(position);
            tabDetailPager.initData();
            container.addView(tabDetailPager.mRootView);
            return tabDetailPager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void initData() {
        titleList = new ArrayList<>();
//        titleList.add("头条");
//        titleList.add("社会");
//        titleList.add("国内");
//        titleList.add("国际");
//        titleList.add("娱乐");
//        titleList.add("体育");
//        titleList.add("军事");
//        titleList.add("科技");
//        titleList.add("财经");
//        titleList.add("时尚");
        titleList.add("头条");
        titleList.add("新闻");
        titleList.add("财经");
        titleList.add("体育");
        titleList.add("娱乐");
        titleList.add("军事");
        titleList.add("教育");
        titleList.add("科技");
        titleList.add("NBA");
        titleList.add("股票");
        titleList.add("星座");
        titleList.add("女性");
        titleList.add("健康");
        titleList.add("育儿");
        mPagerList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            MenuPager tabDetailPager = new MenuPager(mActivity, GlobalContants.jType[i]);
            mPagerList.add(tabDetailPager);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = noScrollViewPager.getCurrentItem();
                noScrollViewPager.setCurrentItem(++position, false);
            }
        });
    }


}
