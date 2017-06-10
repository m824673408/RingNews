package com.dark.xiaom.ringnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by xiaom on 2017/6/9.
 */

public class WebSetting {
    private static final String NAME_CONFIG = "config_Web";

    public static void saveWebFontSize(Context context,int fontSize){
        SharedPreferences preferences = context.getSharedPreferences(NAME_CONFIG,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("fontSize",fontSize);
        editor.commit();
    }

    public static int readWebFontSize(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_CONFIG,MODE_PRIVATE);
        int fontSize = sharedPreferences.getInt("fontSize",16);
        return fontSize;
    }
}
