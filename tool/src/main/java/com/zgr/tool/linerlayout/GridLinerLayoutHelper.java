package com.zgr.tool.linerlayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgr on 2019/12/25.
 * GradLinerLayout
 */

public class GridLinerLayoutHelper<T> {
    public static int VIEW_HOLDER_TAG = Integer.MAX_VALUE;

    private Context context;
    private LinearLayout content;
    private List<T> data;
    private int spanCount;
    private int itemViewResId;
    private OnBindViewHolderListener onBindViewHolderListener;
    private OnItemClickListener onItemClickListener;
    private int itemHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int itemGravity;
    private Drawable itemDivider;
    private int showItemDividers;

    public GridLinerLayoutHelper(Context context, LinearLayout content, int spanCount, int itemViewResId) {
        this.context = context;
        this.content = content;
        if (content.getOrientation() != LinearLayout.VERTICAL) {
            content.setOrientation(LinearLayout.VERTICAL);
        }
        this.spanCount = spanCount;
        this.itemViewResId = itemViewResId;
        data = new ArrayList<>();
        itemGravity = Gravity.CENTER_VERTICAL;
    }

    public void setData(List<T> data) {
        this.data.clear();
        if (data != null && !data.isEmpty()) {
            this.data.addAll(data);
        }
        notifyAllFormData();
    }

    public List<T> getData(){
        return data;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public int getItemCount() {
        return data.size();
    }

    public T getItem(int position) {
        if (position < 0 || position >= data.size()) return null;
        return data.get(position);
    }

    public void setOnBindViewHolderListener(OnBindViewHolderListener onBindViewHolderListener) {
        this.onBindViewHolderListener = onBindViewHolderListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setItemGravity(int itemGravity) {
        this.itemGravity = itemGravity;
    }

    public void setShowItemDividers(int showItemDividers) {
        this.showItemDividers = showItemDividers;
    }

    public void setItemDivider(Drawable itemDivider) {
        this.itemDivider = itemDivider;
    }

    public void notifyAllFormData() {
        if (content == null) return;
        int dataSize = data.size();
        if (dataSize <= 0) {
            content.removeAllViews();
            return;
        }
        if (spanCount <= 0) {
            spanCount = 1;
        }
        int remainder = dataSize % spanCount;
        int quotient = dataSize / spanCount;
        int targetLineCount = remainder > 0 ? quotient + 1 : quotient; //目标行数
        int contentChildCount = content.getChildCount(); //实际行数

        //增删行数
        if (contentChildCount > targetLineCount) {
            int removeLineCount = contentChildCount - targetLineCount;
            for (int i = 1; i <= removeLineCount; i++) {
                View childView = content.getChildAt(content.getChildCount() - 1);
                content.removeView(childView);
            }
        } else if (contentChildCount < targetLineCount) {
            int addLineCount = targetLineCount - contentChildCount;
            for (int i = 1; i <= addLineCount; i++) {
                View childView = getLineView();
                content.addView(childView);
            }
        }
        contentChildCount = content.getChildCount();
        //遍历每行，每行子View个数与SpanCount相等
        for (int i = 0; i < contentChildCount; i++) {
            LinearLayout lineView = (LinearLayout) content.getChildAt(i);
            int lineChildCount = lineView.getChildCount();
            if (lineChildCount > spanCount) {
                for (int j = 0; j < lineChildCount - spanCount; j++) {
                    View lastChildView = lineView.getChildAt(lineView.getChildCount() - 1);
                    lineView.removeView(lastChildView);
                }
            } else if (lineChildCount < spanCount) {
                for (int j = 0; j < spanCount - lineChildCount; j++) {
                    View childView = getLineChildView();
                    lineView.addView(childView);
                }
            }
        }

        //创建及绑定Holder
        int dataIndex = 0;
        for (int i = 0; i < contentChildCount; i++) {
            LinearLayout lineView = (LinearLayout) content.getChildAt(i);
            lineView.setGravity(itemGravity);
            if(lineView.getShowDividers() != showItemDividers){
                lineView.setShowDividers(showItemDividers);
            }
            if(lineView.getDividerDrawable() != itemDivider){
                lineView.setDividerDrawable(itemDivider);
            }

            for (int j = 0; j < lineView.getChildCount(); j++) {
                FrameLayout lineChildView = (FrameLayout) lineView.getChildAt(j);

                if (dataIndex >= data.size()) {
                    lineChildView.removeAllViews();
                    continue;
                }

                if (lineChildView.getChildCount() > 1) {
                    throw new RuntimeException("ItemView只能有一个根布局");
                }
                if (lineChildView.getChildCount() <= 0) {
                    View childView = LayoutInflater.from(context).inflate(itemViewResId, lineChildView, false);
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, itemHeight);
                    lp.gravity = Gravity.CENTER;
                    childView.setLayoutParams(lp);
                    lineChildView.addView(childView);
                }
                View childView = lineChildView.getChildAt(0);
                Object tag = childView.getTag(VIEW_HOLDER_TAG);
                ListLinerLayoutHelperViewHolder holder;
                if (tag instanceof ListLinerLayoutHelperViewHolder) {
                    holder = (ListLinerLayoutHelperViewHolder) tag;
                } else {
                    holder = onBindViewHolderListener.onCreateViewHolder(childView);
                    childView.setTag(VIEW_HOLDER_TAG, holder);
                }
                final int finalDataIndex = dataIndex;
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, finalDataIndex);
                        }
                    }
                });
                onBindViewHolderListener.onBindViewHolder(holder, childView, dataIndex, data.size());
                dataIndex++;
            }
        }

    }

    private FrameLayout getLineChildView() {
        FrameLayout frameLayout = new FrameLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        frameLayout.setLayoutParams(lp);
        return frameLayout;
    }

    private LinearLayout getLineView() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(lp);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        return linearLayout;
    }

    public interface OnBindViewHolderListener {
        ListLinerLayoutHelperViewHolder onCreateViewHolder(View itemView);

        void onBindViewHolder(ListLinerLayoutHelperViewHolder viewHolder, View itemView, int position, int count);
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }


}
