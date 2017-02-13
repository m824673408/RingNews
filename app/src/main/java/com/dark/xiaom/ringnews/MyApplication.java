package com.dark.xiaom.ringnews;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import org.xutils.x;

import uk.co.chrisjenx.calligraphy.*;

/**
 * Created by xiaom on 2017/2/11.
 */

public class MyApplication extends Application {

    private static Context context;
    public static Typeface typeFace;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        context = getApplicationContext();
        x.Ext.setDebug(uk.co.chrisjenx.calligraphy.BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/micro.ttf").setFontAttrId(R.attr.fontPath).build());
    }
    //获取全局上下文
    public static Context getContext() {
        return context;
    }
}
