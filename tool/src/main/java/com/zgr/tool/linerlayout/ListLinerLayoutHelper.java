package com.zgr.tool.linerlayout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.zgr.tool.list.ListDataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgr on 2018/6/28.
 * 列表LinerLayout帮助类
 */

public class ListLinerLayoutHelper<T> {
    public static int VIEW_HOLDER_TAG = Integer.MAX_VALUE;

    protected Context context;
    protected LinearLayout linearLayout;
    protected List<T> data;
    protected List<View> viewCache;
    protected int itemViewResId;
    protected OnChildViewCreateListener onChildViewCreateListener;
    protected OnBindViewHolderListener onBindViewHolderListener;
    protected List<View> headViewList;
    protected List<View> footViewList;

    public ListLinerLayoutHelper(Context context, LinearLayout linearLayout, int itemViewResId) {
        this.context = context;
        this.linearLayout = linearLayout;
        data = new ArrayList<>();
        viewCache = new ArrayList<>();
        headViewList = new ArrayList<>();
        footViewList = new ArrayList<>();
        this.itemViewResId = itemViewResId;
    }

    public void addHeadView(View headView) {
        addHeadView(headView, false);
    }

    public void addHeadView(View headView, boolean isNotifyAll) {
        if (headView == null || headViewList.contains(headView)) return;
        headViewList.add(headView);
        linearLayout.addView(headView, headViewList.size() - 1);
        if (isNotifyAll) {
            notifyAllFormData();
        }
    }

    public void addFootView(View footView) {
        addFootView(footView, false, null);
    }

    public void addFootView(View footView, LinearLayout.LayoutParams lp) {
        addFootView(footView, false, lp);
    }

    public void addFootView(View footView, boolean isNotifyAll, LinearLayout.LayoutParams lp) {
        if (footView == null || footViewList.contains(footView)) return;
        footViewList.add(footView);
        linearLayout.addView(footView, lp);
        if (isNotifyAll) {
            notifyAllFormData();
        }
    }

    public void removeHeadView(View headView) {
        removeHeadView(headView, false);
    }

    public void removeHeadView(View headView, boolean isNotifyAll) {
        if (headView == null || !headViewList.contains(headView)) return;
        headViewList.remove(headView);
        linearLayout.removeView(headView);
        if (isNotifyAll) {
            notifyAllFormData();
        }
    }

    public void removeFootView(View footView) {
        removeFootView(footView, false);
    }

    public void removeFootView(View footView, boolean isNotifyAll) {
        if (footView == null || !footViewList.contains(footView)) return;
        footViewList.remove(footView);
        linearLayout.removeView(footView);
        if (isNotifyAll) {
            notifyAllFormData();
        }
    }

    public void setOnChildViewCreateListener(OnChildViewCreateListener onChildViewCreateListener) {
        this.onChildViewCreateListener = onChildViewCreateListener;
    }

    public void setOnBindViewHolderListener(OnBindViewHolderListener onBindViewHolderListener) {
        this.onBindViewHolderListener = onBindViewHolderListener;
    }

    public void setData(List<T> data) {
        this.data.clear();
        if (data != null && !data.isEmpty()) {
            this.data.addAll(data);
        }
        notifyAllFormData();
    }

    public void notifyAllFormData() {
        final int dataCount = data.size();
        final int viewCacheCount = viewCache.size();
        final int viewCount = linearLayout.getChildCount() - headViewList.size() - footViewList.size();

        notifyAllFromDataForSync(dataCount, viewCacheCount, viewCount);
    }

    private void notifyAllFromDataForSync(int dataCount, int viewCacheCount, int viewCount) {
        notifyForAddView(dataCount, viewCacheCount);
        notifyForViewChange(dataCount, viewCacheCount, viewCount);
    }

    private void notifyForAddView(int dataCount, int viewCacheCount) {
        if (dataCount > viewCacheCount) {
            int differenceCount = dataCount - viewCacheCount;
            for (int i = 0; i < differenceCount; i++) {
                View view = View.inflate(context, itemViewResId, null);
                viewCache.add(view);
            }
        }
    }

    private void notifyForViewChange(int dataCount, int viewCacheCount, int viewCount) {
        if (dataCount > viewCount) {
            int differenceCount = dataCount - viewCount;
            for (int i = 0; i < differenceCount; i++) {
                View view = viewCache.get(viewCount + i);
                if (view.getParent() != null) {
                    ViewGroup parent = (ViewGroup) view.getParent();
                    if (linearLayout.equals(parent)) {
                        view = View.inflate(context, itemViewResId, null);
                    } else {
                        parent.removeView(view);
                    }
                }
                linearLayout.addView(view, headViewList.size() + viewCount + i);
            }
        } else if (dataCount < viewCount) {
            int differenceCount = viewCount - dataCount;
            int positionStart = headViewList.size() + dataCount - 1;
            if (positionStart < 0) {
                linearLayout.removeAllViews();
                for (View headView : headViewList) {
                    linearLayout.addView(headView);
                }
                for (View footView : footViewList) {
                    linearLayout.addView(footView);
                }
            } else {
                linearLayout.removeViews(positionStart, differenceCount);
            }
        }

        for (int i = 0; i < linearLayout.getChildCount() - headViewList.size() - footViewList.size(); i++) {
            View v = linearLayout.getChildAt(i + headViewList.size());
            if (onBindViewHolderListener != null) {
                Object viewHolder = v.getTag(VIEW_HOLDER_TAG);
                ListLinerLayoutHelperViewHolder holder;
                if (viewHolder instanceof ListLinerLayoutHelperViewHolder) {
                    holder = (ListLinerLayoutHelperViewHolder) viewHolder;
                } else {
                    holder = onBindViewHolderListener.onCreateViewHolder(v);
                    v.setTag(VIEW_HOLDER_TAG, holder);
                }
                onBindViewHolderListener.onBindViewHolder(holder, v, i, data.size());
            } else {
                if (onChildViewCreateListener != null) {
                    onChildViewCreateListener.onChildViewCreate(v, i, data.size());
                }
            }
        }
    }

    public View getItemView(int position) {
        position = position + headViewList.size();
        if (position < 0 || position >= linearLayout.getChildCount()) {
            return null;
        }
        return linearLayout.getChildAt(position);
    }

    public ListLinerLayoutHelperViewHolder getItemViewHolder(int position) {
        View view = getItemView(position);
        if (view == null) {
            return null;
        }
        Object viewHolder = view.getTag(VIEW_HOLDER_TAG);
        ListLinerLayoutHelperViewHolder holder = null;
        if (viewHolder instanceof ListLinerLayoutHelperViewHolder) {
            holder = (ListLinerLayoutHelperViewHolder) viewHolder;
        }
        return holder;
    }


    public void addData(T info) {
        if (info != null) {
            this.data.add(info);
            notifyAllFormData();
        }
    }

    public void addData(List<T> datas) {
        if (!ListDataUtil.isEmpty(datas)) {
            this.data.addAll(datas);
            notifyAllFormData();
        }
    }

    public void addTop(T info) {
        if (info != null) {
            this.data.add(0, info);
            notifyAllFormData();
        }
    }

    public void removeData(int position) {
        if (position < 0 || position >= data.size()) return;
        data.remove(position);
        notifyAllFormData();
    }

    public T getItem(int position) {
        if (position < 0 || position >= data.size()) return null;
        return data.get(position);
    }

    public List<T> getData() {
        return this.data;
    }

    public int getItemCount() {
        return data.size();
    }

    public interface OnChildViewCreateListener {
        void onChildViewCreate(View itemView, int position, int count);
    }


    public interface OnBindViewHolderListener {
        ListLinerLayoutHelperViewHolder onCreateViewHolder(View itemView);

        void onBindViewHolder(ListLinerLayoutHelperViewHolder viewHolder, View itemView, int position, int count);
    }

}
