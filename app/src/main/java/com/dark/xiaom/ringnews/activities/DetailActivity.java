package com.dark.xiaom.ringnews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.dark.xiaom.ringnews.R;

/**
 * Created by xiaom on 2017/1/23.
 */
public class DetailActivity extends BaseActivity {
    private WebView webView;
    public NumberProgressBar numberProgressBar;
    private WebChromeClient webChromeClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_datail);
        initUI();
        initData();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));

    }

    private void initUI() {
        webView = (WebView) findViewById(R.id.wb_news);
        numberProgressBar = (NumberProgressBar) findViewById(R.id.npb);
    }

    private void initData() {
        webView.getSettings().setJavaScriptEnabled(true);
        Intent intent = getIntent();
        webView.loadUrl(intent.getStringExtra("url"));
        webView.setWebChromeClient(checkWebClient());


    }

    private WebChromeClient checkWebClient(){
        if (webChromeClient == null){
            webChromeClient = new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    numberProgressBar.setProgress(newProgress);
                }
            };
            return webChromeClient;
        }
        else {
            return webChromeClient;
        }
    }


}
