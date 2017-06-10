package com.dark.xiaom.ringnews.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.fragments.ContentFragment;
import com.dark.xiaom.ringnews.fragments.LeftFragment;
import com.dark.xiaom.ringnews.pagers.MenuPager;
import com.dark.xiaom.ringnews.pagers.NewsPager;
import com.dark.xiaom.ringnews.pagers.SettingContentPager;
import com.dark.xiaom.ringnews.pagers.SignUpPager;
import com.dark.xiaom.ringnews.pagers.UserPager;
import com.dark.xiaom.ringnews.pagers.WeiXinPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Mainactivity各种新闻列表主要陈列
 *
 *
 */

public class MainActivity extends BaseActivity {
    public static MainActivity instance = null;
    Intent intent;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    private FrameLayout frameLayout;
//    @ViewInject(R.id.imb_menu)
//    private ImageView imb_menu;
    private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private static final String FRAGMENT_CONTENT = "fragment_content";
    private SlidingMenu slidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        frameLayout = (FrameLayout) findViewById(R.id.fl_news_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setBehindContentView(R.layout.left_menu);// 设置侧边栏
        // 获取侧边栏对象
        slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置全屏触摸
        int width = getWindowManager().getDefaultDisplay().getWidth();// 获取屏幕宽度
        slidingMenu.setBehindOffset(width * 100 / 320);// 设置预留屏幕的宽度
        slidingMenu.setFadeDegree(0.35f);
        initView();
        initData();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        FragmentManager fm = getSupportFragmentManager();
//
//        FragmentTransaction transaction = fm.beginTransaction();
//
//        transaction.replace(R.id.fl_news_content,new ContentFragment(),FRAGMENT_CONTENT);
//
//        transaction.replace(R.id.fl_left_menu,new LeftFragment(),FRAGMENT_LEFT_MENU);
//
//        transaction. commitAllowingStateLoss();
//
//        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("周期","onResume");
        Log.d("周期",intent + "");
//        Log.d("周期",intent.getBooleanExtra("neesLogin",false) + "");
        if (intent != null && intent.getBooleanExtra("needLogin",false)){
            System.out.println("能不能执行");
            setSignPager();
            intent = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.intent = intent;
    }

    private void initData() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
    }

    private void initNewsPager(){
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fl_news_content,new ContentFragment(),FRAGMENT_CONTENT);

        transaction.replace(R.id.fl_left_menu,new LeftFragment(),FRAGMENT_LEFT_MENU);

        transaction.commit();
    }

    private void initView() {
        initNewsPager();
    }

    // 获取侧边栏fragment
    public LeftFragment getLeftMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        LeftFragment fragment = (LeftFragment) fm
                .findFragmentByTag(FRAGMENT_LEFT_MENU);
        return fragment;
    }

    // 获取主页面fragment
    public ContentFragment getContentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ContentFragment fragment = (ContentFragment) fm
                .findFragmentByTag(FRAGMENT_CONTENT);
        return fragment;
    }
//跳转登录页面


    public void setUserPage(){
        UserPager userPager = new UserPager(this);
        frameLayout.removeAllViews();
        toolbar.setTitle("个人中心");
        frameLayout.addView(userPager.mRootView);
    }

    public void setNewsPage(){
        NewsPager newsPager= new NewsPager(this);
        frameLayout.removeAllViews();
        toolbar.setTitle("新闻小站");
        frameLayout.addView(newsPager.mRootView);
    }

    public void setSettingPage(){
        SettingContentPager settingContentPager = new SettingContentPager(this);
        toolbar.setTitle("设置");
        frameLayout.removeAllViews();
        frameLayout.addView(settingContentPager.mRootView);
    }

    public void setSignPager(){
        SignUpPager signUpPager = new SignUpPager(this);
        toolbar.setTitle("注册");
        frameLayout.removeAllViews();
        frameLayout.addView(signUpPager.mRootView);
    }


    public void setWeiXinPager() {
        WeiXinPager weiXinPager = new WeiXinPager(this);
        toolbar.setTitle("微信热点");
        frameLayout.removeAllViews();
        frameLayout.addView(weiXinPager.mRootView);

    }
}
