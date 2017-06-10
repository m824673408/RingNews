package com.dark.xiaom.ringnews.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.domain.NoAdWebViewClient;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

/**
 * Created by xiaom on 2017/1/23.
 */
public class DetailActivity extends FragmentActivity {
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private WebView webView;
    private NoAdWebViewClient webViewClient;
    private CircularProgressView circularProgressView;
    private Toolbar toolbar;
    final String mimeType = "text/html";
    final String encoding = "utf-8";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.layout_datail);
        initUI();
        initData();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

    }



    private void initUI() {
        webView = (WebView) findViewById(R.id.wb_news);
        toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_detail_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        circularProgressView = (CircularProgressView) findViewById(R.id.cpv);
    }

    private void initData() {
        Intent intent = getIntent();
        String partionContent = intent.getStringExtra("content");
        String title = intent.getStringExtra("title");
        String paddingText = "<head>" + "<style type=\"text/css\">" + "body{padding-left: 10px;padding-right: 10px;padding-top: 10px }" + "</style>" + "</head>";
        String content = paddingText + "<h2>" + title + "</h2>" + partionContent;
        Log.d("加密后url", content);
        webView.loadDataWithBaseURL(null,content,mimeType,encoding,null);
//        webView.loadUrl(intent.getStringExtra("url"));
        webView.getSettings().setBlockNetworkImage(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(checkWebviewClient(content));
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDefaultFontSize(16);
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



    private WebViewClient checkWebviewClient(String url) {
        if (webViewClient == null) {
            webViewClient = new NoAdWebViewClient(this, url) {

                @Override
                public void onPageFinished(WebView view, String url) {
                    webView.getSettings().setBlockNetworkImage(false);
                    circularProgressView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {

                }

//                @Override
//                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest url) {
//                    String flipUrl = url.getUrl().toString().toLowerCase();
//                    if (!ADFilterTool.hasAd(DetailActivity.this, flipUrl)) {
//                        return super.shouldInterceptRequest(view, url);//正常加载
//                    } else {
//                        return new WebResourceResponse(null, null, null);//含有广告资源屏蔽请求
//                    }
//                }
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
