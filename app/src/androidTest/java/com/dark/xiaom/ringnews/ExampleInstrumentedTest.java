package com.dark.xiaom.ringnews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.dark.xiaom.ringnews.domain.JiSuJson;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.dark.xiaom.ringnews", appContext.getPackageName());
        RequestParams result = new RequestParams("http://api.jisuapi.com/news/get?channel=头条&start=0&num=10&appkey=c7e072ef5c43eb98");
        x.http().get(result, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                JiSuJson jiSuJson = gson.fromJson(result,JiSuJson.class);
                JiSuJson.Result result1 = jiSuJson.result;
                Log.d("解析解析！",result1.channel);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
