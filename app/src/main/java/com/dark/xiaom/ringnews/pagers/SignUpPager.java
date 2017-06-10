package com.dark.xiaom.ringnews.pagers;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.activities.MainActivity;
import com.dark.xiaom.ringnews.utils.CacheSharepreferenceUtil;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by xiaom on 2017/6/1.
 */

public class SignUpPager extends BasePager {

    private View view;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button btn_signup;
    private Button btn_signin;

    public SignUpPager(Activity activity) {
        super(activity);
    }

    public SignUpPager(Activity activity, String type) {
        super(activity, type);
    }

    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.layout_signup,null);
        editTextUsername = (EditText) view.findViewById(R.id.et_username);
        editTextPassword = (EditText) view.findViewById(R.id.et_password);
        btn_signup = (Button) view.findViewById(R.id.btn_sign_up);
        btn_signin = (Button) view.findViewById(R.id.btn_sign_in);
        setOnListener();
        return view;
    }

    private void setOnListener() {
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                    String url = "http://120.25.105.125/mynews/servlet/SignUpServelet";
                    RequestParams requestParams = new RequestParams(url);
                    requestParams.addQueryStringParameter("username",username);
                    requestParams.addQueryStringParameter("password",password);
                    Log.d("用户名",username);
                    Log.d("地址",requestParams.getUri());

                    Callback.CommonCallback<String> commonCallback = new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            if(result.equals("OK")){
                                Toast.makeText(mActivity,"注册成功",Toast.LENGTH_SHORT).show();
                                CacheSharepreferenceUtil.saveLogin(mActivity,true);
                                CacheSharepreferenceUtil.saveUsername(mActivity,username);
                                ((MainActivity)mActivity).setNewsPage();
                            }else{
                                Toast.makeText(mActivity,"用户名已被注册！",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

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
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                Log.d("用户名",username);
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                    String url = "http://120.25.105.125/mynews/servlet/SignInServelet";
                    RequestParams requestParams = new RequestParams(url);
                    requestParams.addQueryStringParameter("username",username);
                    requestParams.addQueryStringParameter("password",password);

                    Log.d("地址",requestParams.getUri());

                    Callback.CommonCallback<String> commonCallback = new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            if(result.equals("0")){
                                Toast.makeText(mActivity,"用户名或密码不正确！",Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(mActivity,"登录成功！",Toast.LENGTH_SHORT).show();
                                CacheSharepreferenceUtil.saveLogin(mActivity,true);
                                CacheSharepreferenceUtil.saveUsername(mActivity,username);
                                ((MainActivity)mActivity).setNewsPage();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

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
            }
        });

    }

    @Override
    public void initData() {
        super.initData();



    }
}
