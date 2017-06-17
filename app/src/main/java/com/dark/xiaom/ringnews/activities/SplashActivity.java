package com.dark.xiaom.ringnews.activities;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.domain.SplashImageJson;
import com.google.gson.Gson;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.apache.log4j.chainsaw.Main;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class SplashActivity extends Activity {

    private ImageView imageView;
    private Shimmer shimmer;
    private ShimmerTextView shimmerTextView;
    private TextView textView;
    private static int num = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUi();
        initData();
    }

    private void initData() {
//        String url = "http://route.showapi.com/852-2?showapi_appid=40524&type=1003&page=1&showapi_sign=5fecf8d091a747ab91e9e4de15796f25";
//        RequestParams requestParams = new RequestParams(url);
//        x.http().post(requestParams, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
        shimmer
                .setRepeatCount(2)
                .setDuration(2000)
                .setDirection(Shimmer.ANIMATION_DIRECTION_LTR)
                .setAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intet = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intet);
                        num = 2;
                        finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        num --;
                        textView.setText(num + "");
                    }
                });
    }

    private void initUi() {
        shimmer = new Shimmer();
        shimmerTextView = (ShimmerTextView) findViewById(R.id.stv_splash);
        shimmer.start(shimmerTextView);
        textView = (TextView) findViewById(R.id.tv_splash_number);
        textView.setText(num + "");


    }
}
