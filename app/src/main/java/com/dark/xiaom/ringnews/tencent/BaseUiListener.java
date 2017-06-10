package com.dark.xiaom.ringnews.tencent;

import android.widget.Toast;

import com.dark.xiaom.ringnews.MyApplication;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * Created by xiaom on 2017/6/3.
 */

public class BaseUiListener implements IUiListener {
    @Override
    public void onComplete(Object o) {
        Toast.makeText(MyApplication.getContext(),"分享完成",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(UiError uiError) {

    }

    @Override
    public void onCancel() {

    }
}
