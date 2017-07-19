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

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.activities.NewsDetailActivity;
import com.dark.xiaom.ringnews.adapter.MyAdapter;
import com.dark.xiaom.ringnews.adapter.MyPriNewsAdapter;
import com.dark.xiaom.ringnews.domain.JiSuJson;
import com.dark.xiaom.ringnews.domain.JsonDetail;
import com.dark.xiaom.ringnews.domain.PriNews;
import com.dark.xiaom.ringnews.utils.CacheSharepreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by xiaom on 2017/6/22.
 */

public class LocalPager extends BasePager implements SwipeRefreshLayout.OnRefreshListener{

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private MyPriNewsAdapter myAdapter;
    private final int REFRESH_UI = 0;
    private ImageView imageView;
    private TextView textView;
    private View header;
    private String URLPATH = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static int start = 0;
    private static int num = 10;
    private static String mResult;
    private List<PriNews> list;
    private Gson gson;
    private final String DETAIL_TYPE = "news";
    private TextView textView1;
    private JumpingBeans jumpingBeans2;
    public LocalPager(Activity activity) {
        super(activity);
    }

    public LocalPager(Activity activity, String type) {
        super(activity, type);
    }

    public LocalPager(Activity activity, int channelId) {
        super(activity, channelId);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.layout_menu_pager, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_newslist);
        recyclerView.setHasFixedSize(true);
        //消除列表分割线
//        recyclerView.addItemDecoration(new MyItemDecoration(mActivity,
//                MyItemDecoration.VERTICAL_LIST));
//        recyclerView.addItemDecoration(new MyItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        imageView = (ImageView) view.findViewById(R.id.img_above);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_srl);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        textView1 = (TextView) view.findViewById(R.id.tv_loading);
        header = LayoutInflater.from(mActivity).inflate(R.layout.layout_headerview, recyclerView, false);
//        headerImageView = (ImageView) header.findViewById(R.id.img_above);
//        headerTextView = (TextView) header.findViewById(R.id.tv_headerview);
//        initData();
        return view;


    }

    @Override
    public void initData() {

        URLPATH = "http://120.25.105.125/mynews/servlet/NewsServlet" ;
        String cacheJson = CacheSharepreferenceUtil.readJson(mActivity,URLPATH);
        if(cacheJson != null){
            Log.d("cache","cache被使用！");
            showRecyclerView(cacheJson);
        }
        getNewsDetails(type);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.BcolorPrimary);
        jumpingBeans2 = JumpingBeans.with(textView1)
                .makeTextJump(0, textView1.getText().toString().indexOf('g'))
                .setIsWave(true)
                .setLoopDuration(1000)  // ms
                .build();
        super.initData();
    }

    private void showRecyclerView(String result){

        gson = new Gson();
        list = gson.fromJson(result, new TypeToken<List<PriNews>>(){}.getType());
//        final JiSuJson.Result r = priNews.getResult();
//        List<JiSuJson.NewContent> myNewsList = r.getData();
        myAdapter = new MyPriNewsAdapter(list);
        int headIndex = 0;
        final String headerUrl =list.get(headIndex).getContent();
        String title = list.get(headIndex).getTitle();
        Log.d("intent", headerUrl);
//        headerTextView.setText(r.getData().get(headIndex).getTitle());
//        x.image().bind(headerImageView,r.getData().remove(headIndex).getPic());
//        myAdapter.addDatas(r.getData());
        myAdapter.setHeaderView(header);
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        setRecyclerItemListener();
//        setHeaderViewListener(title,headerUrl);
        setScrollLisntener();
    }

    private void setScrollLisntener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(myAdapter.isVisBottom(recyclerView)){
//                    start = num + start + 1;
                    Log.d("开始序号",start+ "");
//                    textView1.setVisibility(View.VISIBLE);
                    getNewsDetails(type);
                }
            }
        });
    }

    private void setRecyclerItemListener() {
        myAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data,String title,String pic) {
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("detailType",DETAIL_TYPE);
                intent.putExtra("content", data);
                intent.putExtra("title",title);
                intent.putExtra("pic",pic);
                mActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
            }
        });
    }



    public void getNewsDetails(final String type) {

        URLPATH = "http://120.25.105.125/mynews/servlet/NewsServlet";
        final RequestParams params = new RequestParams(URLPATH);
        params.setCacheMaxAge(1000*60);
        Callback.CacheCallback cacheCallback = new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return true;
            }

            @Override
            public void onSuccess(String result) {
                //判定是否为刷新动作
//                if (start > 0){
//                    mResult = result;
//                    jiSuJson = gson.fromJson(mResult, JiSuJson.class);
//                    JiSuJson.Result r = jiSuJson.getResult();
//                    List<JiSuJson.NewContent> myNewsList = r.getData();
//                    myAdapter.addDatas(myNewsList);
//                    myAdapter.notifyDataSetChanged();
//                    textView1.setVisibility(View.GONE);
//                    return ;
//                }
                System.out.println(result);
                showRecyclerView(result);
                CacheSharepreferenceUtil.saveJson(mActivity,URLPATH,result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //处理请求异常
//                start = 0;
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
