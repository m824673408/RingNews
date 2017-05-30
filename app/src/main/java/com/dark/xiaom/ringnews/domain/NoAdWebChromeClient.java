package com.dark.xiaom.ringnews.domain;

import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

/**
 * Created by xiaom on 2017/5/18.
 */

public class NoAdWebChromeClient extends WebChromeClient {
    private String homeurl;
    private Context context;

    public NoAdWebChromeClient(Context context, String homeurl) {
        this.context = context;
        this.homeurl = homeurl;
    }


}
