package com.zgr.tool.recycler;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.zgr.tool.bean.ObjectBeanId;
import com.zgr.tool.handler.IWeakRefreshHandler;
import com.zgr.tool.list.ListDataUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by zgr on 2018/9/1.
 * RecycleViewBaseAdapter
 */

public abstract class RecycleViewBaseAdapter<T> extends RecycleViewNotifyCheckBaseAdapter<RecyclerView.ViewHolder> implements IWeakRefreshHandler.HandlerReference {

    protected static final int ITEM_TYPE_HEAD = Integer.MIN_VALUE;
    protected static final int ITEM_TYPE_FOOT = Integer.MAX_VALUE;
    protected static final int ITEM_TYPE_CONTENT = Integer.MAX_VALUE / 2;

    protected static final int WHAT_HEAD_FOOT_VIEW_NOTIFY_CHANGE = 1;

    protected Context context;
    protected List<T> data;
    protected List<View> headViewList;
    protected List<View> footViewList;
    protected List<OnDataChangeListener> onDataChangeListenerList;
    protected List<OnItemClickListener> onItemClickListenerList;
    protected OnItemLongClickListener onItemLongClickListener;
    protected OnRefreshDataInfoChangeListener onRefreshDataInfoChangeListener;
    protected boolean canRemoveEmptyIdItem;
    protected IWeakRefreshHandler handler;
    protected Set<Object> dataIdSet;
    private boolean isRemoveHeadIng;
    protected int checkLimit;// 用于限制去重比较的个数  0则不进行限制

    public void setCheckLimit(int limit) {
        this.checkLimit = limit;
    }

    public RecycleViewBaseAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
        headViewList = new ArrayList<>();
        footViewList = new ArrayList<>();
        onDataChangeListenerList = new ArrayList<>();
        onItemClickListenerList = new ArrayList<>();
        canRemoveEmptyIdItem = true;
        dataIdSet = new ArraySet<>();
        handler = new IWeakRefreshHandler(this);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (handler == null) {
            handler = new IWeakRefreshHandler(this);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    public void setCanRemoveEmptyIdItem(boolean canRemoveEmptyIdItem) {
        this.canRemoveEmptyIdItem = canRemoveEmptyIdItem;
    }

    public void setData(List<T> setData) {
        setData(setData, true);
    }


    public void setData(List<T> setData, final boolean isCheckRefreshCount) {
        setData(setData, isCheckRefreshCount, true);
    }

    public void setData(List<T> setData, final boolean isCheckRefreshCount, boolean canRemoveRepeat) {
        dataIdSet.clear();
        setData = ListDataUtil.removeNullAndRepeatDataForIdSet(dataIdSet, setData, canRemoveEmptyIdItem, canRemoveRepeat);
        setDataForCallBack(setData, isCheckRefreshCount);
    }

    private void setDataForCallBack(List<T> newData, boolean isCheckRefreshCount) {
        List<T> copyData = null;
        if (isCheckRefreshCount) {
            copyData = new ArrayList<>(data);
        }
        data.clear();
        if (newData != null && !newData.isEmpty()) {
            data.addAll(newData);
        }
        onSetDataForCallback();
        notifyDataSetChange();
        for (OnDataChangeListener onDataChangeListener : onDataChangeListenerList) {
            onDataChangeListener.onSetDataSucceed(data.size());
            onDataChangeListener.onDataSizeChange(data.size());
        }
        if (isCheckRefreshCount) {
            checkRefreshCount(copyData, newData);
        }
    }

    protected void onSetDataForCallback() {

    }

    public void setDataToReplace(List<T> newData) {
        setDataToReplace(newData, false, true);
    }

    public void setDataToReplace(List<T> newData, boolean isCheckRefreshCount, boolean canRemoveEmptyIdItem) {
        dataIdSet.clear();

        if (newData == null || newData.isEmpty()) {
            data.clear();
            notifyDataSetChange();

            for (OnDataChangeListener onDataChangeListener : onDataChangeListenerList) {
                onDataChangeListener.onSetDataSucceed(data.size());
                onDataChangeListener.onDataSizeChange(data.size());
            }
            return;
        }

        newData = ListDataUtil.removeNullAndRepeatDataForIdSet(dataIdSet, newData, canRemoveEmptyIdItem, true);

        if (data.isEmpty()) {
            data.addAll(newData);
            notifyDataSetChange();

            for (OnDataChangeListener onDataChangeListener : onDataChangeListenerList) {
                onDataChangeListener.onSetDataSucceed(data.size());
                onDataChangeListener.onDataSizeChange(data.size());
            }

            if (isCheckRefreshCount) {
                checkRefreshCount(null, newData);
            }

            return;
        }

        List<T> copyData = null;
        if (isCheckRefreshCount) {
            copyData = new ArrayList<>(data);
        }
        int dataSize = data.size();
        int newDataSize = newData.size();
        int notifyCount = dataSize;
        if (dataSize > newDataSize) {
            int positionStart = newDataSize;
            List<T> removeData = data.subList(positionStart, dataSize);
            onRemoveData(removeData);
            data.removeAll(removeData);
            positionStart += headViewList.size();
            notifyItemRangeRemoved(positionStart, dataSize - newDataSize);
            notifyCount = newDataSize;
        } else if (dataSize < newDataSize) {
            int positionStart = dataSize;
            List<T> addData = newData.subList(positionStart, newDataSize);
            data.addAll(addData);
            positionStart += headViewList.size();
            notifyItemRangeInserted(positionStart, newDataSize - dataSize);
            notifyCount = dataSize;
        }

        for (int i = 0; i < notifyCount; i++) {
            T info = data.get(i);
            T newInfo = newData.get(i);
            if (info.equals(newInfo)) continue;
//            if (info instanceof ReplaceObjectBean) {
//                ReplaceObjectBean bean = (ReplaceObjectBean) info;
//                bean.replace(newInfo);
//            }
            notifyItemChanged(i + headViewList.size());
        }

        for (OnDataChangeListener onDataChangeListener : onDataChangeListenerList) {
            onDataChangeListener.onSetDataSucceed(data.size());
            onDataChangeListener.onDataSizeChange(data.size());
        }

        if (isCheckRefreshCount) {
            checkRefreshCount(copyData, newData);
        }
    }

    public void addData(List<T> addData) {
        addData(addData, data.size(), false);
    }


    public void addData(T itemData) {
        List<T> addData = new ArrayList<>();
        addData.add(itemData);
        addData(addData, data.size(), false);
    }

    public void addData(T itemData, int index) {
        List<T> addData = new ArrayList<>();
        addData.add(itemData);
        addData(addData, index, false);
    }


    public void addData(List<T> addData, final int index, final boolean isNotifyAll) {
        addData(addData, index, isNotifyAll, true);
    }

    public void addData(List<T> addData, boolean canRemoveRepeat) {
        addData(addData, data.size(), false, canRemoveRepeat);
    }

    public void addData(List<T> addData, final int index, final boolean isNotifyAll, final boolean canRemoveRepeat) {
        checkLimitResetDataIdSet();
        addData = ListDataUtil.removeNullAndRepeatDataForIdSet(dataIdSet, addData, canRemoveEmptyIdItem, canRemoveRepeat);
        addDataForCallBack(addData, index, isNotifyAll);
    }

    private void checkLimitResetDataIdSet() {
        if (checkLimit <= 0 || dataIdSet.size() <= checkLimit) {
            return;
        }
        Object[] dataIds = dataIdSet.toArray();
        if (dataIds == null) {
            return;
        }
        dataIdSet.clear();
        dataIdSet.addAll(Arrays.asList(dataIds).subList(dataIds.length - checkLimit, dataIds.length));
    }


    private void addDataForCallBack(List<T> newData, int index, boolean isNotifyAll) {
        if (newData == null || newData.isEmpty()) return;
        int finalIndex = index;
        if (finalIndex < 0) finalIndex = 0;
        if (finalIndex > data.size()) finalIndex = data.size();
        int positionStart = headViewList.size() + finalIndex;
        onAddDataForCallback(newData, finalIndex);
        data.addAll(finalIndex, newData);
        onAddDataForCallbackLater(newData, finalIndex);
        if (isNotifyAll) {
            notifyDataSetChange();
        } else {
            if (finalIndex - 1 >= 0) {
                notifyItemChange(positionStart - 1, "notify");
            }
            if (finalIndex + newData.size() < data.size()) {
                notifyItemChange(positionStart + newData.size(), "notify");
            }
            notifyItemRangeInserted(positionStart, newData.size());
        }
        for (OnDataChangeListener onDataChangeListener : onDataChangeListenerList) {
            onDataChangeListener.onAddDataSucceed(positionStart, finalIndex, data.size(), newData.size());
            onDataChangeListener.onDataSizeChange(data.size());
        }
    }

    protected void onAddDataForCallback(List<T> newData, int index) {

    }

    protected void onAddDataForCallbackLater(List<T> newData, int index) {

    }

    public void removeData(T removeInfo) {
        if (removeInfo == null || data.isEmpty()) return;
        int removeIndex = -1;
        for (int i = 0; i < data.size(); i++) {
            T info = data.get(i);
            if (info == null) continue;
            if (info.equals(removeInfo)) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex == -1) return;

        int removePosition = getItemPositionForDataIndex(removeIndex);

        removeData(removePosition);
    }

    public void removeData(int removePosition) {
        if (isHeadOrFootPosition(removePosition)) {
            return;
        }
        T removeInfo = getItem(removePosition);

        if (removeInfo instanceof ObjectBeanId) {
            ObjectBeanId o = (ObjectBeanId) removeInfo;
            Object id = o.getObjectBeanId();
            if (id != null) {
                dataIdSet.remove(id);
            }
        }
        int removeIndex = getDataIndex(removePosition);
        onRemoveData(data.get(removeIndex));
        data.remove(removeIndex);
        if (getItemCount() == 0) {
            notifyDataSetChange();
        } else {
            notifyItemRemoved(removePosition);
            notifyItemRangeChange(headViewList.size(), data.size(), "notify");
        }

        for (OnDataChangeListener onDataChangeListener : onDataChangeListenerList) {
            onDataChangeListener.onRemoveDataSucceed(removePosition, removeIndex, data.size());
            onDataChangeListener.onDataSizeChange(data.size());
        }
    }

    public void removeData(List<T> removeDataList) {
        if (removeDataList == null || removeDataList.isEmpty()) return;
        List<Integer> removePosition = new ArrayList<>();
        Iterator<T> iterator = data.iterator();
        int i = -1;
        other:
        while (iterator.hasNext()) {
            i++;
            T info = iterator.next();
            Object id = null;
            if (info instanceof ObjectBeanId) {
                ObjectBeanId o = (ObjectBeanId) info;
                id = o.getObjectBeanId();
            }
            for (T removeInfo : removeDataList) {
                if (info.equals(removeInfo)) {
                    if (id != null) {
                        dataIdSet.remove(id);
                    }
                    onRemoveData(removeInfo);
                    iterator.remove();
                    removePosition.add(getItemPositionForDataIndex(i));
                    continue other;
                }
            }
        }

        if (removePosition.isEmpty()) return;

        try {
            boolean hasContinuous = true;

            for (int j = 0; j < removePosition.size(); j++) {
                if (j == 0 || j == removePosition.size() - 1) continue;
                int pP = removePosition.get(j - 1);
                int cP = removePosition.get(j);
                int lP = removePosition.get(j + 1);
                if (cP == pP + 1 && cP == lP - 1) continue;
                hasContinuous = false;
                break;
            }

            if (hasContinuous) {
                notifyItemRangeRemoved(removePosition.get(0), removeDataList.size());
            } else {
                for (Integer position : removePosition) {
                    notifyItemRemoved(position);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            notifyDataSetChange();
        }

        for (OnDataChangeListener onDataChangeListener : onDataChangeListenerList) {
            onDataChangeListener.onDataSizeChange(data.size());
        }
    }

    public void removeDataToNotifyAll(List<T> removeDataList) {
        if (removeDataList == null || removeDataList.isEmpty()) return;
        int removeCount = 0;
        Iterator<T> iterator = data.iterator();
        other:
        while (iterator.hasNext()) {
            T info = iterator.next();
            Object id = null;
            if (info instanceof ObjectBeanId) {
                ObjectBeanId o = (ObjectBeanId) info;
                id = o.getObjectBeanId();
            }
            for (T removeInfo : removeDataList) {
                if (info.equals(removeInfo)) {
                    if (id != null) {
                        dataIdSet.remove(id);
                    }
                    onRemoveData(removeInfo);
                    iterator.remove();
                    removeCount++;
                    continue other;
                }
            }
        }
        if (removeCount > 0) {
            notifyDataSetChange();
        }
        for (OnDataChangeListener onDataChangeListener : onDataChangeListenerList) {
            onDataChangeListener.onDataSizeChange(data.size());
        }
    }

    protected void onRemoveData(T item) {

    }

    protected void onRemoveData(List<T> data) {

    }

    public void replaceData(T newData) {
        if (newData == null) return;
        List<T> newDataList = new ArrayList<>();
        newDataList.add(newData);
        replaceData(newDataList);
    }

    public void replaceData(List<T> newData) {
        Collection<Integer> result = ListDataUtil.removeNullAndReplaceData(data, newData);
        if (ListDataUtil.isEmpty(result)) {
            return;
        }
        for (int index : result) {
            notifyItemChange(getItemPositionForDataIndex(index), "notify");
        }
    }

    public List<T> getData() {
        return data;
    }

    public void addHeadView(View v) {
        addHeadView(v, true);
    }

    public void addHeadView(View v, boolean isNotify) {
        addHeadView(v, headViewList.size(), isNotify);
    }

    public void addHeadView(View v, int index, boolean isNotify) {
        if (v == null || headViewList.contains(v) || footViewList.contains(v)) return;
        headViewList.add(index, v);
        if (isNotify) {
            notifyItemInserted(index);
            sendMessage(WHAT_HEAD_FOOT_VIEW_NOTIFY_CHANGE, null, 100);
        }
    }

    public View getHeadView(int position) {
        return ListDataUtil.getItem(headViewList, position);
    }

    public View getFootView(int position) {
        int index = position - headViewList.size() - data.size();
        return getFootViewForIndex(index);
    }

    public View getFootViewForIndex(int index) {
        return ListDataUtil.getItem(footViewList, index);
    }

    public int getHeaderLayoutCount() {
        if (ListDataUtil.isEmpty(headViewList)) {
            return 0;
        } else {
            return headViewList.size();
        }
    }

    public int getFooterLayoutCount() {
        if (ListDataUtil.isEmpty(footViewList)) {
            return 0;
        } else {
            return footViewList.size();
        }
    }


    public void addHeadView(View v, int index) {
        if (v == null || headViewList.contains(v) || footViewList.contains(v)) return;
        headViewList.add(index, v);
        notifyItemInserted(index);
        sendMessage(WHAT_HEAD_FOOT_VIEW_NOTIFY_CHANGE, null, 100);
    }

    public void addFootView(View v) {
        addFootView(v, true);
    }

    public void addFootView(View v, boolean isNotify) {
        if (v == null || footViewList.contains(v) || headViewList.contains(v)) return;
        int positionStart = headViewList.size() + data.size() + footViewList.size();
        footViewList.add(v);
        if (isNotify) {
            notifyItemInserted(positionStart);
            sendMessage(WHAT_HEAD_FOOT_VIEW_NOTIFY_CHANGE, null, 100);
        }
    }

    public void removeHeadView(View headView) {
        if (headView == null || isRemoveHeadIng || !headViewList.contains(headView)) return;
        isRemoveHeadIng = true;
        int removeIndex = -1;
        for (int i = 0; i < headViewList.size(); i++) {
            View v = headViewList.get(i);
            if (v.equals(headView)) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex == -1) return;
        headViewList.remove(removeIndex);
        if (getItemCount() == 0) {
            notifyDataSetChange();
        } else {
            notifyItemRemoved(removeIndex);
            sendMessage(WHAT_HEAD_FOOT_VIEW_NOTIFY_CHANGE, removeIndex, 0);
        }

        ViewParent viewParent = headView.getParent();
        if (viewParent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) viewParent;
            viewGroup.removeView(headView);
        }

        isRemoveHeadIng = false;
    }

    public void removeFootView(View footView) {
        if (footView == null) return;
        int removeIndex = -1;
        for (int i = 0; i < footViewList.size(); i++) {
            View v = footViewList.get(i);
            if (v.equals(footView)) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex == -1) return;
        int removePosition = footViewList.size() + data.size() + removeIndex;
        footViewList.remove(removeIndex);
        if (getItemCount() == 0) {
            notifyDataSetChange();
        } else {
            notifyItemRemoved(removePosition);
            sendMessage(WHAT_HEAD_FOOT_VIEW_NOTIFY_CHANGE, removeIndex, 0);
        }
    }

    public boolean isHeadOrFootPosition(int position) {
        return (!headViewList.isEmpty() && position < headViewList.size()) || (!footViewList.isEmpty() && position >= headViewList.size() + data.size());
    }

    public void checkRefreshCount(List<T> data, List<T> newData) {
        if (onRefreshDataInfoChangeListener == null) return;
        int refreshCount = ListDataUtil.checkRefreshCount(data, newData);
        onRefreshDataInfoChangeListener.onInfoChangeListener(refreshCount);
    }


    public void addOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        if (onDataChangeListener == null) return;
        onDataChangeListenerList.add(onDataChangeListener);
    }

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (onItemClickListener == null) return;
        onItemClickListenerList.add(onItemClickListener);
    }


    public void removeOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        if (onDataChangeListener == null || onDataChangeListenerList.isEmpty()) return;
        onDataChangeListenerList.remove(onDataChangeListener);
    }

    public void removeAllOnDataChangeListener() {
        if (onDataChangeListenerList.isEmpty()) return;
        onDataChangeListenerList.clear();
    }


    public void removeOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (onItemClickListener == null || onItemClickListenerList.isEmpty()) return;
        onItemClickListenerList.remove(onItemClickListener);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnRefreshDataInfoChangeListener(OnRefreshDataInfoChangeListener onRefreshDataInfoChangeListener) {
        this.onRefreshDataInfoChangeListener = onRefreshDataInfoChangeListener;
    }

    protected abstract RecycleViewItemViewHolder createNewItemViewHolder(ViewGroup parent);

    protected abstract void setDataToView(RecycleViewItemViewHolder viewHolder, int position, boolean canLoad);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_HEAD:
            case ITEM_TYPE_FOOT:
                ViewGroup.LayoutParams lp;
                if (layoutManager instanceof GridLayoutManager) {
                    lp = new GridLayoutManager.LayoutParams(GridLayoutManager.LayoutParams.MATCH_PARENT, GridLayoutManager.LayoutParams.WRAP_CONTENT);
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager.LayoutParams slp = new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT, StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
                    slp.setFullSpan(true);
                    lp = slp;
                } else if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
                    int orientation = llm.getOrientation();
                    int width, height;
                    if (orientation == LinearLayoutManager.VERTICAL) {
                        width = RecyclerView.LayoutParams.MATCH_PARENT;
                        height = RecyclerView.LayoutParams.WRAP_CONTENT;
                    } else {
                        width = RecyclerView.LayoutParams.WRAP_CONTENT;
                        height = RecyclerView.LayoutParams.MATCH_PARENT;
                    }
                    lp = new RecyclerView.LayoutParams(width, height);
                } else {
                    lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                }
                View view = new FrameLayout(context);
                view.setLayoutParams(lp);
                return new RecycleViewHeadFootViewHolder(view);
            default:
                return createNewItemViewHolder(parent, viewType);
        }
    }

    protected RecycleViewItemViewHolder createNewItemViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_CONTENT) {
            final RecycleViewItemViewHolder holder = createNewItemViewHolder(parent);
            registerNewViewHolderListener(holder);
            return holder;
        }
        return null;
    }

    protected void registerNewViewHolderListener(final RecycleViewItemViewHolder holder) {
        if (holder == null) {
            return;
        }
        holder.itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (OnItemClickListener onItemClickListener : onItemClickListenerList) {
                    if (onItemClickListener == null) continue;
                    onItemClickListener.onItemClick(v, holder.position);
                }
            }
        };
        holder.itemLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener == null) return false;
                return onItemLongClickListener.onItemLongClick(v, holder.position);
            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecycleViewItemViewHolder) {
            setDataToViewDeal((RecycleViewItemViewHolder) holder, position, true);
        } else if (holder instanceof RecycleViewHeadFootViewHolder) {
            setDataToHeadOrFootView((RecycleViewHeadFootViewHolder) holder, position, true);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, List payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            if (holder instanceof RecycleViewItemViewHolder) {
                setDataToViewDeal((RecycleViewItemViewHolder) holder, position, false);
            } else if (holder instanceof RecycleViewHeadFootViewHolder) {
                setDataToHeadOrFootView((RecycleViewHeadFootViewHolder) holder, position, false);
            }
        }
    }

    private void setDataToViewDeal(RecycleViewItemViewHolder holder, final int position, boolean canLoad) {
        holder.position = position;
        if (canLoad) {
            holder.itemView.setOnClickListener(holder.itemClickListener);
            holder.itemView.setOnLongClickListener(holder.itemLongClickListener);
        }

        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams slp = (StaggeredGridLayoutManager.LayoutParams) lp;
            slp.setFullSpan(false);
        }

        setDataToView(holder, position, canLoad);
    }

    protected void setDataToHeadOrFootView(RecycleViewHeadFootViewHolder holder, int position, boolean canLoad) {
        if (!(holder.itemView instanceof FrameLayout)) {
            return;
        }

        FrameLayout contentView = (FrameLayout) holder.itemView;
        View childView = contentView.getChildCount() > 0 ? contentView.getChildAt(0) : null;
        View headView = getHeadView(position);
        View footView = getFootView(position);

        View newChildView = null;

        if (headView != null && headView != childView) {
            newChildView = headView;
        } else if (footView != null && footView != childView) {
            newChildView = footView;
        } else if (headView == null && footView == null && childView != null) {
            contentView.removeView(childView);
        }

        if (newChildView != null) {
            if (childView != null) {
                contentView.removeView(childView);
            }
            ViewParent newChildParent = newChildView.getParent();
            if (newChildParent instanceof ViewGroup) {
                ViewGroup newChildParentG = (ViewGroup) newChildParent;
                newChildParentG.removeView(newChildView);
            }
            contentView.addView(newChildView);
        }
    }

    public T getItem(int position) {
        int index = getDataIndex(position);
        if (index < 0 || index >= data.size()) return null;
        T t = null;
        try {
            t = data.get(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public int getDataIndex(int position) {
        if (position < headViewList.size() || position >= headViewList.size() + data.size()) {
            return -1;
        }
        return position - headViewList.size();
    }

    public int getItemPositionForDataIndex(int index) {
        return index + headViewList.size();
    }

//    @Override
//    public long getItemId(int position) {
//        if (position < headViewList.size()) {
//            return headViewList.get(position).hashCode();
//        } else if (position >= headViewList.size() + data.size()) {
//            int index = position - (headViewList.size() + data.size());
//            if (index >= footViewList.size()) return position;
//            return footViewList.get(index).hashCode();
//        } else {
//            T info = getItem(position);
//            if (info == null) return position;
//            return info.hashCode();
//        }
//    }

    @Override
    public int getItemViewType(int position) {
        if (position < headViewList.size()) {
            return ITEM_TYPE_HEAD;
        } else if (position >= headViewList.size() + data.size()) {
            return ITEM_TYPE_FOOT;
        } else {
            return getContentItemViewType(position);
        }
    }

    protected int getContentItemViewType(int position) {
        return ITEM_TYPE_CONTENT;
    }

    public void destroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public int getItemCount() {
        return headViewList.size() + data.size() + footViewList.size();
    }

    @Override
    public void dealMessage(Message msg) {
        switch (msg.what) {
            case WHAT_HEAD_FOOT_VIEW_NOTIFY_CHANGE:
                Object o = msg.obj;
                if (o instanceof Integer) {
                    int index = (int) o;
                    for (int i = index; i < getItemCount(); i++) {
                        notifyItemChange(i, "notify");
                    }
                }
                break;
        }
    }

    protected void sendMessage(int what, Object o, long time) {
        sendMessage(what, o, time, true);
    }

    protected void sendMessage(int what, Object o, long time, boolean isSingle) {
        if (handler == null) return;
        if (isSingle) {
            handler.removeMessages(what);
        }
        Message message = handler.obtainMessage();
        message.what = what;
        message.obj = o;
        handler.sendMessageDelayed(message, time);
    }


    public int getColor(int color) {
        return context == null ? 0 : ContextCompat.getColor(context, color);
    }

    public interface OnDataChangeListener {
        void onSetDataSucceed(int size);

        void onAddDataSucceed(int positionStart, int indexStart, int size, int addSize);

        void onRemoveDataSucceed(int removePosition, int removeIndex, int size);

        void onDataSizeChange(int size);
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View v, int position);
    }

    public interface OnRefreshDataInfoChangeListener {
        void onInfoChangeListener(int count);
    }
}
