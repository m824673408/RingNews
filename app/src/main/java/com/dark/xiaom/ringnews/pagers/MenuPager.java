package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.utils.JsonDetail;
import com.dark.xiaom.ringnews.utils.MyAdapter;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaom on 2017/2/10.
 */

public class MenuPager extends BasePager {
    private LinearLayoutManager linearLayoutManager;
    private String type;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    ImageView imageView;
    TextView textView;
    public MenuPager(Activity activity, String type) {
        super(activity);
        this.type = type;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.layout_menu_pager, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_newslist);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new MyItemDecoration(mActivity,
                MyItemDecoration.VERTICAL_LIST));
        imageView = (ImageView) view.findViewById(R.id.img_above);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        initData();
        return view;
    }

//    private void setHeader(RecyclerView view) {
//        View header = LayoutInflater.from(mActivity).inflate(R.layout.layout_headerview, view, false);
//        ImageView imageView = (ImageView) header.findViewById(R.id.img_above);
//        x.image().bind(imageView,);
//        myAdapter.setHeaderView(header);
//    }

    @Override
    public void initData() {
        getNewsDetails(type);
        setListener();
    }

    private void setListener() {
        myAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {

            }
        });
    }

    public void getNewsDetails(String type) {
        String URLPATH = "http://v.juhe.cn/toutiao/index?type=" + type + "&key=d5a64087fc098f164e1dadf1b3550597";
        List<JsonDetail.NewContent> list;
        final RequestParams params = new RequestParams(URLPATH);
        Callback.CommonCallback cacheCallback = new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                JsonDetail jsonDetail = gson.fromJson(result, JsonDetail.class);
                JsonDetail.result r = jsonDetail.getResult();
                myAdapter = new MyAdapter();
                r.getData().remove(0);
                View header = LayoutInflater.from(mActivity).inflate(R.layout.layout_headerview, recyclerView, false);
                ImageView imageView = (ImageView) header.findViewById(R.id.img_above);
                TextView textView = (TextView) header.findViewById(R.id.tv_headerview);
                textView.setText(r.getData().get(0).getTitle());
                x.image().bind(imageView,r.getData().remove(0).getThumbnail_pic_s());
                myAdapter.addDatas(r.getData());
                myAdapter.setHeaderView(header);
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
                Log.d("xutils", "网络数据传输完毕");
            }
        };
//        RequestParams params = new RequestParams(URLPATH);
        x.http().get(params, cacheCallback);
    }


}
