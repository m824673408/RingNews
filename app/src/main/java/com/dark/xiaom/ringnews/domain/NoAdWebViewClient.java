package com.dark.xiaom.ringnews.domain;

import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dark.xiaom.ringnews.utils.ADFilterTool;

/**
 * Created by xiaom on 2017/3/24.
 */

public class NoAdWebViewClient extends WebViewClient {
    private String homeurl;
    private Context context;

    public NoAdWebViewClient(Context context, String homeurl) {
        this.context = context;
        this.homeurl = homeurl;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        url = url.toLowerCase();
        if (!url.contains(homeurl)) {
            if (!ADFilterTool.hasAd(context, url)) {
                return super.shouldInterceptRequest(view, url);
            } else {
                return new WebResourceResponse(null, null, null);
            }
        } else {
            return super.shouldInterceptRequest(view, url);
        }
    }
}
