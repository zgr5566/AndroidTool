package com.zgr.tool.list;

import java.util.List;

/**
 * Created by zgr on 2018/8/7.
 * 列表数据callBack
 */

public interface ListDataCallBack<T> {
    void onCallBack(List<T> newData);
}
