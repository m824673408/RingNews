package com.dark.xiaom.ringnews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.dark.xiaom.ringnews.MyApplication;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by xiaom on 2017/5/21.
 */

public class CacheSharepreferenceUtil {

    public static final String NAME_PREF = "config";

    public static final String NAME_PRED_LOGIN = "login";

    public static void saveJson(Context context, String key,String value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREF,
                MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key,value);
            editor.commit();
        }

    public static String readJson(Context context,String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREF,
                MODE_PRIVATE);
        String json = preferences.getString(key, null);
        System.out.println("json数据为" + json);
        Log.d("key是",key + json);
        return json;
    }

    public static void saveBoolean(Context context, String key,String value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREF,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String readBoolean(Context context,String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREF,
                MODE_PRIVATE);
        String json = preferences.getString(key, "");
        return json;
    }

    public static void saveLogin(Context context,Boolean isLogin){
        SharedPreferences preferences = context.getSharedPreferences(NAME_PRED_LOGIN,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("login",isLogin);
        editor.commit();
    }

    public static Boolean getLogin(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_PRED_LOGIN,MODE_PRIVATE);
        Boolean isLogin = sharedPreferences.getBoolean("login",false);
        return isLogin;
    }

    public static void saveUsername(Context context,String username){
        SharedPreferences preferences = context.getSharedPreferences(NAME_PRED_LOGIN,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username",username);
        editor.commit();
    }

    public static String getUsername(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_PRED_LOGIN,MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        return username;
    }

    public static void clearUsername(Context context){
        SharedPreferences preferences = context.getSharedPreferences(NAME_PRED_LOGIN,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }


}
