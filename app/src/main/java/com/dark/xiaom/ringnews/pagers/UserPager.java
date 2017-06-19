package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.activities.MainActivity;
import com.dark.xiaom.ringnews.utils.CacheSharepreferenceUtil;

import org.apache.log4j.chainsaw.Main;
import org.w3c.dom.Text;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by xiaom on 2017/5/30.
 */

public class UserPager extends BasePager {
    TextView tv_username;
    Button btn_exit;
    private View view;
    private Button btn_change_por;
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果




    public UserPager(Activity activity) {
        super(activity);
    }


    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.layout_user_pager,null);
        tv_username = (TextView) view.findViewById(R.id.tv_username);
        btn_exit = (Button) view.findViewById(R.id.btn_exit);
        btn_change_por = (Button) view.findViewById(R.id.btn_change_por);
        initData();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheSharepreferenceUtil.clearUsername(mActivity);
                ((MainActivity)mActivity).setSignPager();
            }
        });

        btn_change_por.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
            }
        });

        tv_username.setText("用户名：" + CacheSharepreferenceUtil.getUsername(mActivity));

    }

    private void choseHeadImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        mActivity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

}
