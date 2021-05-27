package com.zgr.tool.Intent;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.collection.ArrayMap;

import com.zgr.tool.other.Utils;
import com.zgr.tool.text.TextUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by zgr on 2019/9/20.
 * Bundle帮助类，绕过序列化数据大小限制
 */

public class ArgumentHelper {
    private static final String BUNDLE_CONTENT_KEY = "argumentContentKey";
    private final ArrayMap<String, CacheBundle> cacheData;

    private ArgumentHelper() {
        cacheData = new ArrayMap<>();
    }

    public static ArgumentHelper getInstance() {
        return ArgumentHelperHolder.instance;
    }

    private static class ArgumentHelperHolder {
        private static final ArgumentHelper instance = new ArgumentHelper();
    }

    public CacheBundle getCacheBundle(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) return null;
        String key = bundle.getString(BUNDLE_CONTENT_KEY);
        if (TextUtil.isEmpty(key)) return null;
        return cacheData.get(key);
    }

    public CacheBundle setCacheBundle(Bundle bundle) {
        if (bundle == null) return null;
        String bundleContentKey = bundle.getString(BUNDLE_CONTENT_KEY);
        if (!TextUtil.isEmpty(bundleContentKey)) {
            CacheBundle cacheBundle = cacheData.get(bundleContentKey);
            if (cacheBundle != null) {
                return cacheBundle;
            }
        }
        CacheBundle contentBundle = new CacheBundle(bundle);
        String contentKey = Utils.getFormatUUID();
        bundle.putString(BUNDLE_CONTENT_KEY, contentKey);
        cacheData.put(contentKey, contentBundle);
        return contentBundle;
    }

    public void removeCacheBundle(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) return;
        String key = bundle.getString(BUNDLE_CONTENT_KEY);
        if (TextUtil.isEmpty(key)) return;
        cacheData.remove(key);
    }

    public Bundle transitionBundle(CacheBundle cacheBundle) {
        if (cacheBundle == null || cacheBundle.isEmpty()) return null;
        Bundle bundle = new Bundle();
        Map<String, Object> data = cacheBundle.getData();
        for (String key : data.keySet()) {
            Object o = data.get(key);
            if (o instanceof Byte) {
                Byte value = (Byte) o;
                bundle.putByte(key, value);
            } else if (o instanceof Short) {
                Short value = (Short) o;
                bundle.putShort(key, value);
            } else if (o instanceof Character) {
                Character value = (Character) o;
                bundle.putChar(key, value);
            } else if (o instanceof Integer) {
                Integer value = (Integer) o;
                bundle.putInt(key, value);
            } else if (o instanceof Float) {
                Float value = (Float) o;
                bundle.putFloat(key, value);
            } else if (o instanceof Long) {
                Long value = (Long) o;
                bundle.putLong(key, value);
            } else if (o instanceof byte[]) {
                byte[] value = (byte[]) o;
                bundle.putByteArray(key, value);
            } else if (o instanceof short[]) {
                short[] value = (short[]) o;
                bundle.putShortArray(key, value);
            } else if (o instanceof char[]) {
                char[] value = (char[]) o;
                bundle.putCharArray(key, value);
            } else if (o instanceof int[]) {
                int[] value = (int[]) o;
                bundle.putIntArray(key, value);
            } else if (o instanceof float[]) {
                float[] value = (float[]) o;
                bundle.putFloatArray(key, value);
            } else if (o instanceof long[]) {
                long[] value = (long[]) o;
                bundle.putLongArray(key, value);
            } else if (o instanceof CharSequence) {
                CharSequence value = (CharSequence) o;
                bundle.putCharSequence(key, value);
            } else if (o instanceof CharSequence[]) {
                CharSequence[] value = (CharSequence[]) o;
                bundle.putCharSequenceArray(key, value);
            } else if (o instanceof Parcelable) {
                Parcelable value = (Parcelable) o;
                bundle.putParcelable(key, value);
            } else if (o instanceof Parcelable[]) {
                Parcelable[] value = (Parcelable[]) o;
                bundle.putParcelableArray(key, value);
            } else if (o instanceof Serializable) {
                Serializable value = (Serializable) o;
                bundle.putSerializable(key, value);
            }
        }
        return bundle;
    }

}
