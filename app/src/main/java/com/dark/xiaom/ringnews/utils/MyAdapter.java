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

import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
public class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener{

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

        List<JsonDetail.NewContent> data;
        public MyAdapter(List<JsonDetail.NewContent> data) {
            this.data = data;
        }
    ImageOptions options=new ImageOptions.Builder()
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

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content,parent,false);

            view.setOnClickListener(this);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder)holder).authorNameText.setText(data.get(position).getAuthor_name());
            ((ViewHolder)holder).text.setText(data.get(position).getTitle());
            x.image().bind(((ViewHolder)holder).imageView,data.get(position).getThumbnail_pic_s(),options);
            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view,((JsonDetail.NewContent)view.getTag()).getUrl());
        }
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
            text = (TextView) itemView.findViewById(R.id.tv_type);
            imageView = (ImageView) itemView.findViewById(R.id.iv_new);
            authorNameText = (TextView) itemView.findViewById(R.id.tv_author_name);
        }




    }
}

