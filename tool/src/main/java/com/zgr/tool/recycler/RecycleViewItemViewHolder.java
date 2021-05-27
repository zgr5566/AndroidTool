package com.zgr.tool.recycler;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zgr on 2018/9/4.
 * 跟RecycleViewBaseAdapter搭配使用的Item专用ViewHolder
 */

public class RecycleViewItemViewHolder extends RecyclerView.ViewHolder {

    public View.OnClickListener itemClickListener;
    public View.OnLongClickListener itemLongClickListener;
    public int position;

    public RecycleViewItemViewHolder(View itemView) {
        super(itemView);
    }

}
