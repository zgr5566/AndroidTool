package com.zgr.tool.recycler;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by zgr on 2019/4/19.
 * notifyItemChanged检查目标item是否可见
 */

public abstract class RecycleViewNotifyCheckBaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected Handler osHandler;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        layoutManager = recyclerView.getLayoutManager();
        osHandler = new Handler();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
        layoutManager = null;
        osHandler = null;
    }

    public void notifyItemChange(final int position) {
        handlerPost(new Runnable() {
            @Override
            public void run() {
                if (recyclerView == null) return;
                notifyItemChanged(position);
            }
        });
    }

    public void notifyItemChange(final int position, final Object payload) {
        handlerPost(new Runnable() {
            @Override
            public void run() {
                if (recyclerView == null) return;
                notifyItemChanged(position, payload);
            }
        });
    }

    public void notifyItemRangeChange(final int positionStart, final int itemCount) {
        handlerPost(new Runnable() {
            @Override
            public void run() {
                if (recyclerView == null) return;
                int finalPositionStart = positionStart;
                if (finalPositionStart < 0) {
                    finalPositionStart = 0;
                }
                notifyItemRangeChanged(finalPositionStart, itemCount);
            }
        });
    }

    public void notifyItemRangeChange(final int positionStart, final int itemCount, final Object payload) {
        handlerPost(new Runnable() {
            @Override
            public void run() {
                if (recyclerView == null) return;
                int finalPositionStart = positionStart;
                if (finalPositionStart < 0) {
                    finalPositionStart = 0;
                }
                notifyItemRangeChanged(finalPositionStart, itemCount, payload);
            }
        });
    }

    public void notifyItemMove(final int fromPosition, final int toPosition) {
        handlerPost(new Runnable() {
            @Override
            public void run() {
                if (recyclerView == null) return;
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }


    public void notifyDataSetChange() {
        try {
            notifyDataSetChanged();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    protected void handlerPost(Runnable runnable) {
        if (osHandler == null || runnable == null) return;
        try {
            osHandler.post(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

}
