package com.dark.xiaom.ringnews.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.fragments.ContentFragment;
import com.dark.xiaom.ringnews.fragments.LeftFragment;
import com.dark.xiaom.ringnews.pagers.MenuPager;
import com.dark.xiaom.ringnews.pagers.NewsPager;
import com.dark.xiaom.ringnews.pagers.SettingContentPager;
import com.dark.xiaom.ringnews.pagers.SignUpPager;
import com.dark.xiaom.ringnews.pagers.UserPager;
import com.dark.xiaom.ringnews.pagers.WeiXinPager;
import com.dark.xiaom.ringnews.utils.CacheSharepreferenceUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    private TextView tv_toolbar;
    private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private static final String FRAGMENT_CONTENT = "fragment_content";
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
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

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                getLeftMenuFragment().setLeftImage(bitmap);
                upLoadPic(saveFile(bitmap));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void upLoadPic(String path) {
        String url = "http://120.25.105.125/mynews/servlet/ChangePortraitServelet";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("username",CacheSharepreferenceUtil.getUsername(this));
        params.addBodyParameter("file",new File(path));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("success")){
                    Toast.makeText(MainActivity.this, "您的头像更换成功！", Toast.LENGTH_SHORT).show();
                }else if (result.equals("failure")){
                    Toast.makeText(MainActivity.this, "更换失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private String saveFile(Bitmap bitmap) {
        String picName = CacheSharepreferenceUtil.getUsername(this);
        File f = new File("mnt/sdcard/Android/data/com.dark.xiaom.ringnews/portrait/", picName + ".png");
        if (f.exists()) {
            f.delete();
        }else {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        String picPath = "mnt/sdcard/Android/data/com.dark.xiaom.ringnews/portrait/" + picName + ".png";
        return picPath;

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
        tv_toolbar = (TextView) findViewById(R.id.tv_toolbar);
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
        tv_toolbar.setText("个人中心");
        frameLayout.addView(userPager.mRootView);
    }

    public void setNewsPage(){
        NewsPager newsPager= new NewsPager(this);
        frameLayout.removeAllViews();
        tv_toolbar.setText("新闻小站");
        frameLayout.addView(newsPager.mRootView);
    }

    public void setSettingPage(){
        SettingContentPager settingContentPager = new SettingContentPager(this);
        tv_toolbar.setText("设置");
        frameLayout.removeAllViews();
        frameLayout.addView(settingContentPager.mRootView);
    }

    public void setSignPager(){
        SignUpPager signUpPager = new SignUpPager(this);
        tv_toolbar.setText("注册");
        frameLayout.removeAllViews();
        frameLayout.addView(signUpPager.mRootView);
    }


    public void setWeiXinPager() {
        WeiXinPager weiXinPager = new WeiXinPager(this);
        tv_toolbar.setText("微信热点");
        frameLayout.removeAllViews();
        frameLayout.addView(weiXinPager.mRootView);

    }
}
