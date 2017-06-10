package com.dark.xiaom.ringnews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.dark.xiaom.ringnews.activities.NewsDetailActivity;
import com.dark.xiaom.ringnews.domain.JiSuJson;
import com.dark.xiaom.ringnews.utils.CacheSharepreferenceUtil;
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
        String username = CacheSharepreferenceUtil.getUsername(MyApplication.getContext());
        System.out.println(username + "00000000000000000000000000");
    }
}
