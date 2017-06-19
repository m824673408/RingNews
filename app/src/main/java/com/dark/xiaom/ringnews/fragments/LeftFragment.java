package com.dark.xiaom.ringnews.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.activities.MainActivity;
import com.dark.xiaom.ringnews.pagers.SettingContentPager;
import com.dark.xiaom.ringnews.utils.CacheSharepreferenceUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by xiaom on 2017/3/1.
 * 设置碎片
 */

public class LeftFragment extends BaseFragment {

    private ListView lvList;
    private ArrayList<String> mMenuList;
    private ArrayList<Integer> drawableLeftFragment;
    private int mCurrentPos;// 当前被点击的菜单项
    private MenuAdapter mAdapter;
    private ImageView imgPortrait;
    private String portraitUrl;

    @Override
    public View initView() {
        View view = View.inflate(activity, R.layout.fragment_left__menu, null);
        lvList = (ListView) view.findViewById(R.id.lv_list);
        imgPortrait = (ImageView) view.findViewById(R.id.img_users_poratrait);
        return view;
    }

    @Override
    public void initData() {
        drawableLeftFragment = new ArrayList();
        mMenuList = new ArrayList<>();
        mAdapter = new MenuAdapter();
        drawableLeftFragment.add(R.drawable.ic_action_news_center);
        drawableLeftFragment.add(R.drawable.ic_action_user);
        drawableLeftFragment.add(R.drawable.ic_action_weixin);
        drawableLeftFragment.add(R.drawable.ic_action_setting);
        mMenuList.add("新闻中心");
        mMenuList.add("个人中心");
        mMenuList.add("微信热点");
        mMenuList.add("设置");
        lvList.setAdapter(mAdapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mCurrentPos = position;
                mAdapter.notifyDataSetChanged();
                switch (position) {
                    case 0:
                        ((MainActivity) activity).setNewsPage();
                        break;
                    case 1:
                        if (CacheSharepreferenceUtil.getLogin(activity)) {
                            ((MainActivity) activity).setUserPage();
                        } else {
                            ((MainActivity) activity).setSignPager();
                        }
                        break;
                    case 2:
                        ((MainActivity) activity).setWeiXinPager();
                        break;
                    case 3:
                        ((MainActivity) activity).setSettingPage();
                        break;
                }
                toggleSlidingMenu();// 隐藏
            }
        });
        getPortrait();
    }

    private void toggleSlidingMenu() {
        MainActivity mainUi = (MainActivity) activity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        slidingMenu.toggle();// 切换状态, 显示时隐藏, 隐藏时显示
    }



    public void setLeftImage(Bitmap bitmap){
        imgPortrait.setImageBitmap(bitmap);
    }

    public void getPortrait() {
        String portraitServelet = "http://120.25.105.125/mynews/servlet/PortratiServlelet";
        portraitUrl = null;
        final ImageOptions options = new ImageOptions.Builder()
                //设置加载过程中的图片
                .setLoadingDrawableId(R.drawable.img_default)
                //设置加载失败后的图片
//            .setFailureDrawableId(R.drawable.ic_launcher)
                //设置使用缓存
                .setUseMemCache(false)
                //设置显示圆形图片
                .setCircular(true)
                //设置支持gif
                .setIgnoreGif(true)
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                .build();
        RequestParams requestParams = new RequestParams(portraitServelet);
        requestParams.addQueryStringParameter("username", CacheSharepreferenceUtil.getUsername(activity));
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                portraitUrl = "http://120.25.105.125/" + result;
                System.out.println(portraitUrl + "aaaaaa");
                x.image().loadDrawable(portraitUrl, options, new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable result) {
                        imgPortrait.setImageDrawable(result);
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
                });
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
        });
    }

    class MenuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mMenuList.size();
        }

        @Override
        public Object getItem(int position) {
            return mMenuList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(activity, R.layout.list_menu_item, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_left_text);
            textView.setText(mMenuList.get(position));
            ImageView imageView = (ImageView) view.findViewById(R.id.left_image);
            imageView.setImageResource(drawableLeftFragment.get(position));
            return view;
        }
    }
}
