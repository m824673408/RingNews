package com.dark.xiaom.ringnews.utils;

import android.content.Context;
import android.content.res.Resources;

import com.dark.xiaom.ringnews.R;

/**
 * Created by xiaom on 2017/3/24.
 */

public class ADFilterTool {
    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }
}
