package com.dark.xiaom.ringnews.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dark.xiaom.ringnews.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
public class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private ArrayList<JsonDetail.NewContent> mDatas = new ArrayList<>();

    private View mHeaderView;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    List<JsonDetail.NewContent> data;

    public MyAdapter() {

    }

    public MyAdapter(List<JsonDetail.NewContent> data) {
        this.data = data;
    }

    ImageOptions options = new ImageOptions.Builder()
            //设置加载过程中的图片
//            .setLoadingDrawableId(R.drawable.)
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

        if (mHeaderView != null && viewType == TYPE_HEADER) return new ViewHolder(mHeaderView);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);

        view.setOnClickListener(this);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        final JsonDetail.NewContent data = mDatas.get(pos);
        ((ViewHolder) holder).authorNameText.setText(data.getAuthor_name());
        ((ViewHolder) holder).text.setText(data.getTitle());
        x.image().bind(((ViewHolder) holder).imageView, data.getThumbnail_pic_s(), options);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(data);
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, ((JsonDetail.NewContent) view.getTag()).getUrl());
        }
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(List<JsonDetail.NewContent> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView imageView;
        TextView authorNameText;

        public ViewHolder(View itemView) {
            // super这个参数一定要注意,必须为Item的根节点.否则会出现莫名的FC.
            super(itemView);
            if(itemView == mHeaderView) return;
            text = (TextView) itemView.findViewById(R.id.tv_type);
            imageView = (ImageView) itemView.findViewById(R.id.iv_new);
            authorNameText = (TextView) itemView.findViewById(R.id.tv_author_name);
        }
    }
}

