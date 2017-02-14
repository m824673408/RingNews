package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.utils.GlobalContants;
import com.dark.xiaom.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaom on 2017/2/10.
 */

public class NewsPager extends BasePager {
    private List<String> titleList;
    private final int REFRESH_UI = 0;
    private TabPageIndicator tabPageIndicator;
    private ViewPager viewPager;
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
        viewPager = (ViewPager) view.findViewById(R.id.vp_news_list);
        initData();
        viewPager.setAdapter(new MyPagerAdapter());
        tabPageIndicator.setViewPager(viewPager);
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
        titleList.add("头条");
        titleList.add("社会");
        titleList.add("国内");
        titleList.add("国际");
        titleList.add("娱乐");
        titleList.add("体育");
        titleList.add("军事");
        titleList.add("科技");
        titleList.add("财经");
        titleList.add("时尚");
        mPagerList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MenuPager tabDetailPager = new MenuPager(mActivity, GlobalContants.type[i]);
            mPagerList.add(tabDetailPager);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewPager.getCurrentItem();
                viewPager.setCurrentItem(++position);
            }
        });
    }


}
