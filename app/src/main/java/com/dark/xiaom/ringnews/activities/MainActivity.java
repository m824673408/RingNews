package com.dark.xiaom.ringnews.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.fragments.ContentFragment;
import com.dark.xiaom.ringnews.pagers.NewsPager;

import org.xutils.x;


public class MainActivity extends BaseActivity {
    private ContentFragment fragment;
    private RadioButton rb_explore;
    private RadioButton rb_collection;
    private RadioButton rb_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initView();
        initData();
    }

    private void initData() {
        rb_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNewsPager();
            }
        });


    }

    private void initNewsPager(){
        ContentFragment contentFragment = new ContentFragment();
        Log.d("viewpager","viewpager为空啦");
        getFragmentManager().beginTransaction().add(R.id.fl_news_content,contentFragment).commit();
    }

    private void initView() {
        rb_explore = (RadioButton) findViewById(R.id.rbtn_explore);
//        rb_collection = (RadioButton) findViewById(R.id.rbtn_collection);
        rb_setting = (RadioButton) findViewById(R.id.rbtn_setting);
        rb_explore.setChecked(true);
        initNewsPager();
    }


}
