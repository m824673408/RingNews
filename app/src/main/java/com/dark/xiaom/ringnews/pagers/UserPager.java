package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.activities.MainActivity;
import com.dark.xiaom.ringnews.utils.CacheSharepreferenceUtil;

import org.apache.log4j.chainsaw.Main;
import org.w3c.dom.Text;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by xiaom on 2017/5/30.
 */

public class UserPager extends BasePager {
    TextView tv_username;
    Button btn_exit;
    private View view;



    public UserPager(Activity activity) {
        super(activity);
    }


    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.layout_user_pager,null);
        tv_username = (TextView) view.findViewById(R.id.tv_username);
        btn_exit = (Button) view.findViewById(R.id.btn_exit);
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
    }
}
