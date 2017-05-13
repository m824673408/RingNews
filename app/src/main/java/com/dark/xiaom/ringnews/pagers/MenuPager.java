package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.dark.xiaom.ringnews.BuildConfig;
import com.dark.xiaom.ringnews.MyApplication;
import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.activities.DetailActivity;
import com.dark.xiaom.ringnews.utils.DensityUtil;
import com.dark.xiaom.ringnews.utils.JsonDetail;
import com.dark.xiaom.ringnews.utils.MyAdapter;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by xiaom on 2017/2/10.
 */

public class MenuPager extends BasePager implements SwipeRefreshLayout.OnRefreshListener {
    private LinearLayoutManager linearLayoutManager;
    private String type;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private final int REFRESH_UI = 0;
    ImageView imageView;
    TextView textView;
    private static SSLContext mSSLContext = null;
    public SwipeRefreshLayout swipeRefreshLayout;
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
        recyclerView.addItemDecoration(new MyItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        imageView = (ImageView) view.findViewById(R.id.img_above);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_srl);
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
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setListener() {
        myAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent = new Intent(mActivity, DetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                StringBuffer stringBuffer = new StringBuffer(data);
                String safeData = stringBuffer.insert(4, "s").toString();
                intent.putExtra("url", safeData);
                MyApplication.getContext().startActivity(intent);
            }
        });
    }

    public void getNewsDetails(String type) {
        String URLPATH = "https://v.juhe.cn/toutiao/index?type=" + type + "&key=d5a64087fc098f164e1dadf1b3550597";
        List<JsonDetail.NewContent> list;
        final RequestParams params = new RequestParams(URLPATH);
        Callback.CommonCallback cacheCallback = new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                JsonDetail jsonDetail = gson.fromJson(result, JsonDetail.class);
                final JsonDetail.result r = jsonDetail.getResult();
                myAdapter = new MyAdapter();
                r.getData().remove(0);
                StringBuffer stringBuffer = new StringBuffer(r.getData().get(0).getUrl());
                stringBuffer.insert(4, "s");
                final String headerUrl = stringBuffer.toString();
                Log.d("intent", headerUrl);
                View header = LayoutInflater.from(mActivity).inflate(R.layout.layout_headerview, recyclerView, false);
                header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mActivity, DetailActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("url", headerUrl);
                        MyApplication.getContext().startActivity(intent);
                    }
                });
                ImageView imageView = (ImageView) header.findViewById(R.id.img_above);
                TextView textView = (TextView) header.findViewById(R.id.tv_headerview);
                textView.setText(r.getData().get(0).getTitle());
                x.image().bind(imageView,r.getData().remove(0).getThumbnail_pic_s());
                myAdapter.addDatas(r.getData());
                myAdapter.setHeaderView(header);
                recyclerView.setAdapter(myAdapter);
                setListener();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("load failed", "xutils occured something bad");
                Toast.makeText(x.app(), "您的网络抛锚啦……", Toast.LENGTH_LONG).show();
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
//        SSLContext sslContext = getSSLContext(MyApplication.getContext());
//        if (null == sslContext) {
//            if (BuildConfig.DEBUG) {
//                Log.d(TAG, "Error:Can't Get SSLContext!");
//                return ;
//            }
//        }
//        params.setSslSocketFactory(sslContext.getSocketFactory());
        x.http().get(params, cacheCallback);
    }

//    private static SSLContext getSSLContext(Context context) {
//        CertificateFactory certificateFactory = null;
//        InputStream inputStream = null;
//        Certificate cer = null;
//        KeyStore keystore = null;
//        TrustManagerFactory trustManagerFactory = null;
//        try {
//            certificateFactory = CertificateFactory.getInstance("X.509");
//            inputStream = context.getAssets().open("baidu.cer");//这里导入SSL证书文件
//            try {
//                cer = certificateFactory.generateCertificate(inputStream);
////                LogManager.i(TAG, cer.getPublicKey().toString());
//            } finally {
//                inputStream.close();
//            }
//
//            //创建一个证书库，并将证书导入证书库
//            keystore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keystore.load(null, null); //双向验证时使用
//            keystore.setCertificateEntry("trust", cer);
//
//            // 实例化信任库
//            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keystore);
//
//            mSSLContext = SSLContext.getInstance("TLS");
//            mSSLContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
//
//            //信任所有证书 （官方不推荐使用）
////         s_sSLContext.init(null, new TrustManager[]{new X509TrustManager() {
////
////              @Override
////              public X509Certificate[] getAcceptedIssuers() {
////                  return null;
////              }
////
////              @Override
////              public void checkServerTrusted(X509Certificate[] arg0, String arg1)
////                      throws CertificateException {
////
////              }
////
////              @Override
////              public void checkClientTrusted(X509Certificate[] arg0, String arg1)
////                      throws CertificateException {
////
////              }
////          }}, new SecureRandom());
//
//            return mSSLContext;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


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
