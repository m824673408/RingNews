package com.dark.xiaom.ringnews.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.domain.Comment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CommentActivity extends Activity {

    private ListView listView;
    private List<Comment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initUi();
        initData();
    }

    private void initData() {
        String url = "http://120.25.105.125/mynews/servlet/SubmitCommentServelet";
        RequestParams requestParams = new RequestParams(url);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
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
                //这里是判定评论为空的，服务端代码有问题
                if(result.equals("[]")){
                    Toast.makeText(CommentActivity.this, "此地空空如也，去别处看看吧！", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Gson gson = new Gson();
                    commentList = gson.fromJson(result, new TypeToken<List<Comment>>(){}.getType());
                    listView.setAdapter(new MyCommentAdapter());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(CommentActivity.this,ex.getMessage(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        };
        x.http().get(requestParams,commonCallback);
    }

    private void initUi() {
        listView = (ListView) findViewById(R.id.lv_comment);
    }

    class MyCommentAdapter extends BaseAdapter {
        ImageOptions imageOptions = new ImageOptions.Builder().setCircular(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setUseMemCache(true).build();

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
            View view = View.inflate(CommentActivity.this, R.layout.item_comment, null);
            TextView tvUsername = (TextView) view.findViewById(R.id.tv_comment_username);
            TextView tvCommentTime = (TextView) view.findViewById(R.id.tv_comment_time);
            TextView tvCommentBody = (TextView) view.findViewById(R.id.tv_comment_body);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_comment_user_por);
            tvUsername.setText(commentList.get(position).getUsername());
            tvCommentTime.setText(commentList.get(position).getTime().substring(0,10));
            tvCommentBody.setText(commentList.get(position).getComment());
            x.image().bind(imageView, "http://120.25.105.125/" + commentList.get(position).getPortrait(),imageOptions);
            Log.d("头像","12" + commentList.get(position).getPortrait());
            return view;
        }
    }
}
