package com.dark.xiaom.ringnews.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dark.xiaom.ringnews.MyApplication;
import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.domain.Comment;
import com.dark.xiaom.ringnews.tencent.BaseUiListener;
import com.dark.xiaom.ringnews.utils.CacheSharepreferenceUtil;
import com.dark.xiaom.ringnews.utils.DensityUtil;
import com.dark.xiaom.ringnews.utils.WebSetting;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class NewsDetailActivity extends FragmentActivity {
    private WebView webView;
    ImageView imageView;
    final String mimeType = "text/html";
    final String encoding = "utf-8";
    private EditText editText;
    private ImageButton imgSubComment;
    private String title;
    private String username;
    private ListView listView;
    private List<Comment> commentList;
    private ImageButton imb_share;
    private String APP_ID="101406000";
    private Tencent mTencent;
    private Intent intent;
    private ImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        if (intent.getStringExtra("detailType").equals("news")){
            setContentView(R.layout.layout_detail_real);
            mTencent = Tencent.createInstance(APP_ID, this);
            initUI();
            initData();
        } else {
            setContentView(R.layout.layout_weixin_detail);
            initWeiXinUI();
            initWeiXinData();
        }
    }

    private void initWeiXinData() {
        String url = intent.getStringExtra("url");
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultFontSize(WebSetting.readWebFontSize(this));
    }

    private void initWeiXinUI() {
        webView = (WebView) findViewById(R.id.web_weixin);
    }

    private void initData() {
        title = intent.getStringExtra("title");
        final String pic = intent.getStringExtra("pic");
        String partionContent = intent.getStringExtra("content");
        username = CacheSharepreferenceUtil.getUsername(NewsDetailActivity.this);
        //设置分享按钮点击事件
        imb_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                //这条分享消息被好友点击后的跳转URL。需要修改URL
                bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, pic);
                //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
                bundle.putString(QQShare.SHARE_TO_QQ_TITLE, title);
                //分享的图片URL
                bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, pic);
                //分享的消息摘要，最长50个字
                bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "新闻");
                //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
                bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "??我在测试");
                //标识该消息的来源应用，值为应用名称+AppId。
                bundle.putString(QQShare.SHARE_TO_QQ_SITE, "星期几" + MyApplication.APP_id);

                mTencent.shareToQQ(NewsDetailActivity.this, bundle ,new BaseUiListener());
            }
        });
        WebViewClient webViewClient = new WebViewClient(){

        };
        String url = "http://120.25.105.125/mynews/servlet/SubmitCommentServelet";
        RequestParams requestParams = new RequestParams(url);
        try {
            String newsUrl = new String(title.getBytes(), "UTF-8");
            requestParams.addQueryStringParameter("newsUrl",newsUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("title",title);
        //获取评论请求
        Callback.CommonCallback<String> commonCallback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("评论测试",result);
                if(result.equals("no comment")){
                    Toast.makeText(NewsDetailActivity.this,"评论出了一些问题……",Toast.LENGTH_LONG).show();
                }else{
                    Gson gson = new Gson();
                    commentList = gson.fromJson(result, new TypeToken<List<Comment>>(){}.getType());
                    listView.setAdapter(new MyCommentAdapter());
                    Toast.makeText(NewsDetailActivity.this,"申请评论成功",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(NewsDetailActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        };
        x.http().get(requestParams,commonCallback);
        webView.setWebViewClient(webViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        Log.d("正文字号","" + WebSetting.readWebFontSize(this));
        webSettings.setDefaultFontSize(WebSetting.readWebFontSize(this));
        imageView = (ImageView) findViewById(R.id.img_detail_real);
        StringBuffer stringBuffer = new StringBuffer(partionContent);
        String deviceSetting = "<meta name=\"viewport\" content=\"target-densitydpi=high-dpi, width=device-width\" />";
        partionContent = stringBuffer.toString();
        String paddingText = "<head>"+deviceSetting + "<style type=\"text/css\">" + "body{padding-left: 10px;padding-right: 10px;}" + "</style>"  + "</head>";
        String content = paddingText + "<h2>" + title + "</h2>" + partionContent;
        Log.d("加密后url", content);
        webView.loadDataWithBaseURL(null,content,mimeType,encoding,null);
        x.image().bind(imageView,pic,options);
        setClickListener();
    }

    private void setClickListener() {
        imgSubComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CacheSharepreferenceUtil.getLogin(NewsDetailActivity.this)){
                    String comment = editText.getText().toString();
                    Log.d("用户名",username + "这是啥");
                    if (!TextUtils.isEmpty(comment)){
                        String url = "http://120.25.105.125/mynews/servlet/SubmitCommentServelet";
                        RequestParams requestParams = new RequestParams(url);
                        requestParams.addBodyParameter("username",username);
                        Log.d("username",username);
                        requestParams.addBodyParameter("comment",comment);
                        requestParams.addBodyParameter("newsUrl",title);
                        Callback.CommonCallback<String> commonCallback = new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.d("评论测试",result);
                                if(result.equals("0")){
                                    Toast.makeText(NewsDetailActivity.this,"评论成功",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(NewsDetailActivity.this,"您的评论出了一些问题……",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Toast.makeText(NewsDetailActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        };
                        x.http().post(requestParams,commonCallback);
                    }
                }else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewsDetailActivity.this);
                    alertDialogBuilder.setTitle("提示")
                            .setMessage("您尚未登录！是否现在就去登录？")
                            .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(NewsDetailActivity.this,MainActivity.class);
                                        intent.putExtra("needLogin",true);
                                        startActivity(intent);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                }
                            }).create().show();
                }
            }
        });

    }

    private void initUI() {
        webView = (WebView) findViewById(R.id.web_real);
        editText = (EditText) findViewById(R.id.et_comment);
        imgSubComment = (ImageButton) findViewById(R.id.imb_comment_submit);
        listView = (ListView) findViewById(R.id.lv_comment);
        imb_share = (ImageButton) findViewById(R.id.imb_share);
        options = new ImageOptions.Builder()
                //设置加载过程中的图片
                .setLoadingDrawableId(R.drawable.img_default)
                //设置加载失败后的图片
//            .setFailureDrawableId(R.drawable.ic_launcher)
                //设置使用缓存
                .setUseMemCache(true)
                //设置显示圆形图片
                .setCircular(false)
                //设置支持gif
                .setIgnoreGif(false)
                .build();

    }
//QQ回调接口
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != mTencent)
            mTencent.onActivityResult(requestCode, resultCode, data);

    }

    class MyCommentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return commentList.size() ;
        }

        @Override
        public Object getItem(int position) {
            return commentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(NewsDetailActivity.this, R.layout.item_comment, null);
            TextView tvUsername = (TextView) view.findViewById(R.id.tv_comment_username);
            TextView tvCommentTime = (TextView) view.findViewById(R.id.tv_comment_time);
            TextView tvCommentBody = (TextView) view.findViewById(R.id.tv_comment_body);
            tvUsername.setText(commentList.get(position).getUsername());
            tvCommentTime.setText(commentList.get(position).getTime().substring(0,10));
            tvCommentBody.setText(commentList.get(position).getComment());

            return view;
        }
    }


}
