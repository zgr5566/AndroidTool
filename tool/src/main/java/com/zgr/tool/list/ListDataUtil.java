package com.zgr.tool.list;


import androidx.collection.ArraySet;

import com.zgr.tool.bean.ObjectBeanId;
import com.zgr.tool.bean.RefreshCount;
import com.zgr.tool.bean.ReplaceObjectBean;
import com.zgr.tool.text.TextUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by zgr on 2018/8/13.
 * 列表数据工具类
 */

public class ListDataUtil {

    public static <T> List<T> removeNullAndRepeatDataToSync(final List<T> data, final List<T> addData,
                                                            final boolean canRemoveEmptyIdItem) {
        return removeNullAndRepeatDataToSync(data, addData, canRemoveEmptyIdItem, true);
    }

    public static <T> List<T> removeNullAndRepeatDataToSync(final List<T> data, final List<T> addData,
                                                            final boolean canRemoveEmptyIdItem, boolean canRemoveRepeat) {
        if (addData == null) {
            return new ArrayList<>();
        }
        if (addData.isEmpty()) {
            return addData;
        }
        Iterator<T> iterator = addData.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (t == null) {
                iterator.remove();
                continue;
            }
            if (canRemoveEmptyIdItem) {
                if (t instanceof ObjectBeanId) {
                    ObjectBeanId objectBeanId = (ObjectBeanId) t;
                    Object id = objectBeanId.getObjectBeanId();
                    if (id == null) {
                        iterator.remove();
                        continue;
                    }
                    if (id instanceof String) {
                        String idStr = (String) id;
                        if (TextUtil.isEmpty(idStr)) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
        if (canRemoveRepeat && data != null && !data.isEmpty() && !addData.isEmpty()) {
            for (T t : data) {
                Iterator<T> iterator1 = addData.iterator();
                while (iterator1.hasNext()) {
                    T addT = iterator1.next();
                    if (t.equals(addT)) {
                        iterator1.remove();
                    }
                }
            }
        }
        return addData;
    }

    /**
     * 删除空数据和重复数据，泛型类需要实现ObjectBeanId接口
     */
    public static <T> List<T> removeNullAndRepeatDataForIdSet(Set<Object> dataIdSet, final List<T> addData,
                                                              final boolean canRemoveEmptyIdItem, boolean canRemoveRepeat) {
        if (addData == null) {
            return new ArrayList<>();
        }
        if (addData.isEmpty() || (!canRemoveEmptyIdItem && !canRemoveRepeat)) {
            return addData;
        }

        Iterator<T> iterator = addData.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (t == null) {
                iterator.remove();
                continue;
            }

            if (!(t instanceof ObjectBeanId)) return addData;

            ObjectBeanId objectBeanId = (ObjectBeanId) t;
            Object id = objectBeanId.getObjectBeanId();

            if (canRemoveEmptyIdItem) {
                if (id == null) {
                    iterator.remove();
                    continue;
                }
                if (id instanceof String) {
                    String idStr = (String) id;
                    if (TextUtil.isEmpty(idStr)) {
                        iterator.remove();
                        continue;
                    }
                }
            }

            if (!canRemoveRepeat || dataIdSet == null) continue;

            if (dataIdSet.contains(id)) {
                iterator.remove();
            } else {
                dataIdSet.add(id);
            }
        }
        return addData;
    }

    /**
     * 检查数据刷新数量
     *
     * @param data    原数据
     * @param newData 新数据
     * @param <T>     泛型类
     * @return 刷新数量
     */
    public static <T> int checkRefreshCount(List<T> data, List<T> newData) {
        if (newData == null || newData.isEmpty()) {
            return 0;
        }
        int setDataSize = newData.size();
        if (data == null || data.isEmpty()) {
            return setDataSize;
        }
        int subDataSize = setDataSize >= data.size() ? data.size() : setDataSize;
        List<T> subData = data.subList(0, subDataSize);
        int refreshCount = 0;
        other:
        for (T newInfo : newData) {
            if (newInfo == null) continue;
            if (newInfo instanceof RefreshCount) {
                RefreshCount newRefreshCount = (RefreshCount) newInfo;
                if (newRefreshCount.isNotCalculate()) continue;
            }
            for (int i = 0; i < subDataSize; i++) {
                T subInfo = subData.get(i);
                if (subInfo == null) continue;
                if (subInfo instanceof RefreshCount) {
                    RefreshCount subRefreshCount = (RefreshCount) subInfo;
                    if (subRefreshCount.isNotCalculate()) continue;
                }
                if (newInfo.equals(subInfo)) continue other;
                if (i == subDataSize - 1) {
                    refreshCount++;
                }
            }
        }
        return refreshCount;
    }

    /**
     * 检查数据刷新数量 考虑到短视频里面撤回一部分的情况进行的修改
     *
     * @param data    原数据
     * @param newData 新数据
     * @param <T>     泛型类
     * @return 刷新数量
     */
    public static <T> int checkRefreshCountAfterDelete(List<T> data, List<T> newData, CheckChange checkChange) {
        if (newData == null || newData.isEmpty()) {
            return 0;
        }
        int setDataSize = newData.size();
        if (data == null || data.isEmpty()) {
            return setDataSize;
        }
        int subDataSize = setDataSize >= data.size() ? data.size() : (data.size() >= 30 ? 30 : data.size());
        List<T> subData = data.subList(0, subDataSize);
        int refreshCount = 0;
        int minSize = Math.min(subData.size(), newData.size());
        other:
        for (int j = 0; j < newData.size(); j++) {
            T newInfo = newData.get(j);
            if (newInfo == null) continue;
            if (j < minSize) {
                T oldSortData = subData.get(j);
                if (checkChange != null) {
                    checkChange.onCheckSortChange(oldSortData, newInfo);
                }
            }
            for (int i = 0; i < subDataSize; i++) {
                T subInfo = subData.get(i);
                if (subInfo == null) continue;
                if (newInfo.equals(subInfo)) {
                    if (checkChange != null) {
                        checkChange.onCheckChange(subInfo, newInfo);
                    }
                    continue other;
                }
                if (i == subDataSize - 1) {  //和已有数据对比到最后没有匹配的，则表示是添加的数据
                    refreshCount++;
                }
            }
        }
        return refreshCount;
    }

    public interface CheckChange {
        /**
         * 用于把要判断的两个对象是否有改变的
         *
         * @param oldData
         * @param newData
         */
        void onCheckChange(Object oldData, Object newData);

        /**
         * 用于把要判断的两个对象放到外面判断（减少循环的次数）
         */
        void onCheckSortChange(Object oldData, Object newData);
    }


    /**
     * 检查是否有测回的数据
     *
     * @param data    原数据
     * @param newData 新数据
     * @param <T>     泛型类
     * @return 需要撤回的 对象列表
     */
    public static <T> List<T> checkRemoveDatas(List<T> data, List<T> newData, int pageSize) {
        if (newData == null || newData.isEmpty()) {
            return null;
        }
        if (data == null || data.isEmpty()) {
            return null;
        }
        List<T> result = new ArrayList<>();

        int setDataSize = newData.size();
        if (setDataSize > data.size()) {
            return null;
        }
        int index = -1;
        int newCheckLast = -1;
        for (int i = newData.size() - 1; i >= (pageSize / 2); i--) {
            T newItem = newData.get(i);
            if (newItem == null) continue;
            for (int j = 0; j < data.size(); j++) {
                T oldItem = data.get(j);
                if (oldItem == null) break;
                if (oldItem.equals(newItem)) {
                    index = j;
                    newCheckLast = i;
                }
            }
        }
        if (index == -1) return null;
        List<T> subData = data.subList(0, index);
        List<T> newSubData = newData.subList(0, newCheckLast);
        other:
        for (T subInfo : subData) {
            if (subInfo == null) continue;
            for (T newInfo : newSubData) {
                if (newInfo == null) continue;
                if (newInfo.equals(subInfo)) {
                    continue other;
                }
            }
            result.add(subInfo);
        }
        return result;
    }

    public static <T> Collection<Integer> removeNullAndReplaceData(final List<T> data, final List<T> newData) {
        return removeNullAndReplaceData(data, newData, true);
    }

    public static <T> Collection<Integer> removeNullAndReplaceData(final List<T> data, final List<T> newData,
                                                                   final boolean canRemoveEmptyIdItem) {
        if (isEmpty(data) || isEmpty(newData)) {
            return null;
        }

        Iterator<T> iterator = newData.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (t == null) {
                iterator.remove();
                continue;
            }
            if (canRemoveEmptyIdItem) {
                if (t instanceof ObjectBeanId) {
                    ObjectBeanId objectBeanId = (ObjectBeanId) t;
                    Object id = objectBeanId.getObjectBeanId();
                    if (id == null) {
                        iterator.remove();
                        continue;
                    }
                    if (id instanceof String) {
                        String idStr = (String) id;
                        if (TextUtil.isEmpty(idStr)) {
                            iterator.remove();
                        }
                    }
                }
            }
        }

        if (newData.isEmpty()) {
            return null;
        }

        Set<Integer> indexList = new ArraySet<>();

        if (!newData.isEmpty()) {
            other:
            for (int i = 0; i < data.size(); i++) {
                T t = data.get(i);
                for (T newT : newData) {
                    if (newT.equals(t) && t instanceof ReplaceObjectBean) {
                        ReplaceObjectBean replaceObjectBean = (ReplaceObjectBean) t;
                        replaceObjectBean.replace(newT);
                        indexList.add(i);
                        continue other;
                    }
                }
            }
        }
        return indexList;
    }

    public static <T> T getItem(List<T> data, int position) {
        if (isEmpty(data) || position < 0 || position >= data.size()) return null;
        return data.get(position);
    }

    public static <T> int getSize(List<T> data) {
        return data == null ? 0 : data.size();
    }

    public static boolean isEmpty(Collection data) {
        return data == null || data.isEmpty();
    }

    public static boolean isSizeEqual(Collection data1, Collection data2) {
        int data1Size = isEmpty(data1) ? 0 : data1.size();
        int data2Size = isEmpty(data2) ? 0 : data2.size();
        return data1Size == data2Size;
    }

    public static <T> void removeRepeatData(List<T> data) {
        if (isEmpty(data)) {
            return;
        }
        removeNullData(data);
        if (isEmpty(data)) {
            return;
        }

        List<T> checkData = new ArrayList<>(data);

        for (T ct : checkData) {
            int count = 0;
            Iterator<T> iterator = data.iterator();
            while (iterator.hasNext()) {
                T t = iterator.next();
                if (!(ct.equals(t))) {
                    continue;
                }
                count++;
                if (count < 2) {
                    continue;
                }
                count--;
                iterator.remove();
            }
        }
    }

    public static <T> List<T> removeNullData(List<T> data) {
        if (isEmpty(data)) {
            return data;
        }
        Iterator<T> iterator = data.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (t == null) {
                iterator.remove();
            }
        }
        return data;
    }

    public static <T> boolean contains(Set<T> set, T info) {
        if (set == null || set.isEmpty()) {
            return false;
        }
        return set.contains(info);
    }

    public static <T> boolean isContentEquals(List<T> data1, List<T> data2) {
        if (isEmpty(data1) &&isEmpty(data2)) {
            return true;
        }
        if (isEmpty(data1) ||isEmpty(data2)) {
            return false;
        }
        if (data1.size() != data2.size()) {
            return false;
        }
        for (int i = 0; i < data1.size(); i++) {
            T item1 = data1.get(i);
            T item2 = data2.get(i);
            if (item1 == null && item2 == null) {
                continue;
            }
            if (item1 == null || item2 == null) {
                return false;
            }
            if (!item1.equals(item2)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean contains(List<T> data, T info) {
        if (data == null || data.isEmpty()) {
            return false;
        }
        return data.contains(info);
    }


}
