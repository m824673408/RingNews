package com.dark.xiaom.ringnews.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dark.xiaom.ringnews.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

/**
 * Created by xiaom on 2017/1/23.
 */
public class DetailActivity extends BaseActivity {
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private WebView webView;
    private WebChromeClient webChromeClient;
    private WebViewClient webViewClient;
    private CircularProgressView circularProgressView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Explode());
        setContentView(R.layout.layout_datail);
        initUI();
        initData();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

    }

    private void initUI() {
        webView = (WebView) findViewById(R.id.wb_news);
        circularProgressView = (CircularProgressView) findViewById(R.id.cpv);
    }

    private void initData() {
        Intent intent = getIntent();
        webView.loadUrl(intent.getStringExtra("url"));
        webView.getSettings().setBlockNetworkImage(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(checkWebClient());
        webView.setWebViewClient(checkWebviewClient());
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        webView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        Log.i("webview", "cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
        webView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        webView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webView.getSettings().setAppCacheEnabled(true);

    }

    private WebChromeClient checkWebClient(){
        if (webChromeClient == null){
            webChromeClient = new WebChromeClient(){

                @Override
                public void onProgressChanged(WebView view, int newProgress) {

                }
            };
            return webChromeClient;
        }
        else {
            return webChromeClient;
        }
    }

    private WebViewClient checkWebviewClient() {
        if (webViewClient == null) {
            webViewClient = new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    webView.getSettings().setBlockNetworkImage(false);
                    circularProgressView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {

                }
            };
            return webViewClient;
        } else {
            return webViewClient;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                webView.goBack();
                return true;
            }


        }
        return super.onKeyDown(keyCode, event);
    }



}
