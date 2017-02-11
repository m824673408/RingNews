package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.utils.JsonDetail;
import com.dark.xiaom.ringnews.utils.MyAdapter;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by xiaom on 2017/2/10.
 */

public class MenuPager extends BasePager {
    private LinearLayoutManager linearLayoutManager;
    private String type;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    public MenuPager(Activity activity,String type) {
        super(activity);
        this.type = type;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.layout_menu_pager,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_newslist);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void initData() {
//        getDetails(type,recyclerView,mActivity);
        getNewsDetails(type);
    }

    public void getNewsDetails(String type){
        String URLPATH = "http://v.juhe.cn/toutiao/index?type=" + type + "&key=d5a64087fc098f164e1dadf1b3550597";
        List<JsonDetail.NewContent> list ;
        RequestParams params = new RequestParams(URLPATH);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                JsonDetail jsonDetail = gson.fromJson(result, JsonDetail.class);
                JsonDetail.result r = jsonDetail.getResult();
                myAdapter = new MyAdapter(r.getData());
                recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                Log.d("xutils","网络数据传输完毕");
            }
        });
    }



}
