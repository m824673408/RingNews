package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dark.xiaom.ringnews.MyApplication;
import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.activities.DetailActivity;
import com.dark.xiaom.ringnews.adapter.MyItemDecoration;
import com.dark.xiaom.ringnews.utils.CacheSharepreferenceUtil;
import com.dark.xiaom.ringnews.domain.JiSuJson;
import com.dark.xiaom.ringnews.domain.JsonDetail;
import com.dark.xiaom.ringnews.adapter.MyAdapter;
import com.google.gson.Gson;


import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by xiaom on 2017/2/10.
 * 页面细则，包括RecyclerView以及headerView
 */



public class MenuPager extends BasePager implements SwipeRefreshLayout.OnRefreshListener{
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private final int REFRESH_UI = 0;
    private ImageView imageView;
    private TextView textView;
    private View header;
    private ImageView headerImageView;
    private TextView headerTextView;
    private String URLPATH = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static int start = 0;
    private static int num = 10;
    public MenuPager(Activity activity, String type) {
        super(activity,type);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.layout_menu_pager, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_newslist);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new MyItemDecoration(mActivity,
                MyItemDecoration.VERTICAL_LIST));
        recyclerView.addItemDecoration(new MyItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        imageView = (ImageView) view.findViewById(R.id.img_above);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_srl);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        header = LayoutInflater.from(mActivity).inflate(R.layout.layout_headerview, recyclerView, false);
        headerImageView = (ImageView) header.findViewById(R.id.img_above);
        headerTextView = (TextView) header.findViewById(R.id.tv_headerview);
//        initData();
        return view;
    }

    @Override
    public void initData() {
        URLPATH = "http://api.jisuapi.com/news/get?channel=" + type + "&start=" + start + "&num=" + num + "&appkey=c7e072ef5c43eb98";
        String cacheJson = CacheSharepreferenceUtil.readJson(mActivity,URLPATH);
        if(cacheJson != null){
            Log.d("cache","cache被使用！");
            showRecyclerView(cacheJson);
        }
        getNewsDetails(type);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 封装获取参数成功方法
     *
     * @param result
     */


    private void showRecyclerView(String result){
        Log.d("新数据",result);
        Gson gson = new Gson();
        JiSuJson jiSuJson = gson.fromJson(result, JiSuJson.class);
        final JiSuJson.Result r = jiSuJson.getResult();
        myAdapter = new MyAdapter();
        int headIndex = 0;
        for(int i = 0; i < 10; i++){
            if (r.getData().get(i).pic != "" || r.getData().get(i).pic != null){
                Log.d("pic","图片为空");
            }else {
                r.getData().remove(i);
                Log.d("第一个非空数据：",i+"");
                Log.d("我来看看非空数据","这是什么！" + r.getData().get(i).pic);
                headIndex = i;
                break;
            }
        }
        final String headerUrl = r.getData().get(headIndex).getContent();
        String title = r.getData().get(headIndex).getTitle();
        Log.d("intent", headerUrl);
        headerTextView.setText(r.getData().get(headIndex).getTitle());
        x.image().bind(headerImageView,r.getData().remove(headIndex).getPic());
        myAdapter.addDatas(r.getData());
        myAdapter.setHeaderView(header);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        setRecyclerItemListener();
        setHeaderViewListener(title,headerUrl);
        setScrollLisntener();
    }

    private void setScrollLisntener() {
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(myAdapter.isVisBottom(recyclerView)){
//                    if(num >= 40){
//                        start = num+1;
//                        num = 10;
//                    }else {
//                        num = num+10;
//                    }
//
//                    swipeRefreshLayout.setRefreshing(true);
//
//                }
//            }
//        });
    }

    private void setHeaderViewListener(final String title, final String headerUrl) {
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, DetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("content", headerUrl);
                intent.putExtra("title",title);
                mActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
            }
        });

    }

    private void setRecyclerItemListener() {
        myAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data,String title) {
                Intent intent = new Intent(mActivity, DetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("content", data);
                intent.putExtra("title",title);
                mActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
            }
        });
    }



    public void getNewsDetails(final String type) {
        List<JsonDetail.NewContent> list;
        URLPATH = "http://api.jisuapi.com/news/get?channel=" + type + "&start=" + start + "&num=" + num + "&appkey=c7e072ef5c43eb98";
        final RequestParams params = new RequestParams(URLPATH);
        params.setCacheMaxAge(1000*60);
        Callback.CacheCallback cacheCallback = new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return true;
            }

            @Override
            public void onSuccess(String result) {
                Log.d("类型",type);
                Log.d("数据",result);
                showRecyclerView(result);
                CacheSharepreferenceUtil.saveJson(mActivity,URLPATH,result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //处理请求异常
                ex.printStackTrace();

                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    Toast.makeText(x.app(), "网络抛锚啦！", Toast.LENGTH_SHORT).show();
                } else { // 其他错误
//                    Toast.makeText(x.app(), "您的网络抛锚啦！", Toast.LENGTH_SHORT).show();
                }
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
        x.http().get(params, cacheCallback);//执行get方法
    }



    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_UI:
                    getNewsDetails(type);
                    //刷新adapter
                    myAdapter.notifyDataSetChanged();
                    //关闭刷新动画
                    swipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_UI, 2000);
    }



}
