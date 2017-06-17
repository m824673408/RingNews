package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.utils.CacheSharepreferenceUtil;
import com.dark.xiaom.ringnews.utils.DataCleanManager;
import com.dark.xiaom.ringnews.utils.WebSetting;

import java.io.File;

/**
 * Created by xiaom on 2017/3/1.
 */

public class SettingContentPager extends BasePager implements SeekBar.OnSeekBarChangeListener{

    private TextView textView;
    private SeekBar seekBar;
    private Button btn_clear;

    public SettingContentPager(Activity activity) {
        super(activity);
    }


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.layout_setting,null);
        textView = (TextView) view.findViewById(R.id.tv_font_size);
        seekBar = (SeekBar) view.findViewById(R.id.sb_setting_textsize);
        btn_clear = (Button) view.findViewById(R.id.btn_clearCache);
        seekBar.setOnSeekBarChangeListener(this);
        btn_clear.setOnClickListener(new View.OnClickListener() {

            private String clearSize;

            @Override
            public void onClick(View v) {
                DataCleanManager.cleanApplicationData(mActivity,"/mnt/sdcard/Android/data/com.dark.xiaom.ringnews/cache");
                File file = new File("/mnt/sdcard/Android/data/com.dark.xiaom.ringnews/cache");
                try {
                    clearSize = DataCleanManager.getCacheSize(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(mActivity,"已清理" + clearSize + "缓存", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
  
        if(progress > 0 && progress <=25){
            textView.setTextSize(16);
        }else if (progress >25 && progress <= 50){
            textView.setTextSize(18);
        }else if(progress > 50 && progress <= 75){
            textView.setTextSize(20);
        }else if(progress > 75 && progress <= 100){
            textView.setTextSize(22);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        if(progress > 0 && progress <=25){
            WebSetting.saveWebFontSize(mActivity,16);
        }else if (progress >25 && progress <= 50){
            WebSetting.saveWebFontSize(mActivity,18);
        }else if(progress > 50 && progress <= 75){
            WebSetting.saveWebFontSize(mActivity,20);
        }else if(progress > 75 && progress <= 100){
            WebSetting.saveWebFontSize(mActivity,24);
        }
    }
}
