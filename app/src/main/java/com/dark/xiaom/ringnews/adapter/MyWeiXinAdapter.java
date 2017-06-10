package com.dark.xiaom.ringnews.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dark.xiaom.ringnews.R;
import com.dark.xiaom.ringnews.domain.JiSuJson;
import com.dark.xiaom.ringnews.domain.WeiXinArticleJson;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
public class MyWeiXinAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_NO_PIC = 2;

    private List<WeiXinArticleJson.WeiXinContent> mDatas = new ArrayList<>();
//    private View mHeaderView;
    private View noPicView;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    List<JiSuJson.NewContent> data;


    public MyWeiXinAdapter() {

    }

    public MyWeiXinAdapter(List<WeiXinArticleJson.WeiXinContent> data) {
        mDatas = data;

    }

    ImageOptions options = new ImageOptions.Builder()
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        if (mHeaderView != null && viewType == TYPE_HEADER) {
//
//            mHeaderView.setOnClickListener(this);
//
//            return new ViewHolder(mHeaderView);
//        }


        if (viewType == TYPE_NO_PIC) {

            noPicView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_pic, parent, false);

            noPicView.setOnClickListener(this);

            return new ViewHolder(noPicView);

        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_weixinarticle, parent, false);

        view.setOnClickListener(this);





        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        final int pos = getRealPosition(holder);

        final WeiXinArticleJson.WeiXinContent data = mDatas.get(position);
        String date = data.getTime().substring(data.getTime().indexOf("-") + 1,data.getTime().length());
//        if (getItemViewType(position) == TYPE_HEADER){
//            ((ViewHolder) holder).text.setText(data.getTitle());
//            x.image().bind(((ViewHolder) holder).imageView, data.getPic(), options);
//        }else
        if(getItemViewType(position) == TYPE_NORMAL){
            if(date == ""){
                date =data.getTime().substring(data.getTime().indexOf(".") + 1,data.getTime().length());
            }
            if (date == "" || data.getWeixinname() == ""){
                ((ViewHolder) holder).authorNameText.setText("待认领的火星文章");
            }else{
                ((ViewHolder) holder).authorNameText.setText(data.getWeixinname() + " | " + date);
            }

            ((ViewHolder) holder).text.setText(data.getTitle());
            x.image().bind(((ViewHolder) holder).imageView, data.getPic(), options);
            //将数据保存在itemView的Tag中，以便点击时进行获取

        }else if (getItemViewType(position) == TYPE_NO_PIC){
            ((ViewHolder)holder).text.setText(data.getTitle());
            ((ViewHolder)holder).authorNameText.setText(data.getWeixinname() + " | " + date);

        }
        holder.itemView.setTag(data);
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
//        return mHeaderView == null ? position : position - 1;
        return 0;
    }



    @Override
    public int getItemCount() {
//        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
//        return mHeaderView == null ? mDatas.size() : mDatas.size();
        return mDatas.size();
    }



    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, ((WeiXinArticleJson.WeiXinContent) view.getTag()).getUrl());
        }
    }

    public void setHeaderView(View headerView) {
//        mHeaderView = headerView;
//        notifyItemInserted(0);
    }

//    public View getHeaderView() {
//        return mHeaderView;
//    }

    public void addDatas(List<WeiXinArticleJson.WeiXinContent> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
//        if (mHeaderView == null) return TYPE_NORMAL;
//        if (position == 0) return TYPE_HEADER;
//            if(mDatas.get(position).getPic() == null || mDatas.get(position).getPic() == ""){
//                return TYPE_NO_PIC;
//            }
        return TYPE_NORMAL;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String url);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView imageView;
        TextView authorNameText;
        public ViewHolder(View itemView) {
            // super这个参数一定要注意,必须为Item的根节点.否则会出现莫名的FC.
            super(itemView);
//            if(itemView == mHeaderView) {
//                text = (TextView) itemView.findViewById(R.id.tv_headerview);
//                imageView = (ImageView) itemView.findViewById(R.id.img_above);
//                return;
//            }else
//            if(itemView == noPicView){
//                text = (TextView) itemView.findViewById(R.id.tv_no_pic);
//                authorNameText = (TextView) itemView.findViewById(R.id.tv_no_pic_author_name);
//                return;
//            }
            authorNameText = (TextView) itemView.findViewById(R.id.tv_weixin_author_name);
            text = (TextView) itemView.findViewById(R.id.tv_weixin_type);
            imageView = (ImageView) itemView.findViewById(R.id.iv_weixin_aticle);
        }
    }

    public boolean isVisBottom(RecyclerView recyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }
    }
}

