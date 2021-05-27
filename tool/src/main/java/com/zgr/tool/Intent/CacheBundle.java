package com.zgr.tool.Intent;


import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zgr on 2019/9/23.
 */

public class CacheBundle implements Serializable {

    private static final long serialVersionUID = 1L;

    private HashMap<String, Object> data;

    public CacheBundle() {
        data = new HashMap<>();
    }

    public CacheBundle(Bundle bundle) {
        data = new HashMap<>();
        if (bundle != null && !bundle.isEmpty()) {
            Set<String> keySet = bundle.keySet();
            for (String key : keySet) {
                data.put(key, deepCopyValue(bundle.get(key)));
            }
        }
    }

    Object deepCopyValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof ArrayList) {
            return deepCopyArrayList((ArrayList) value);
        } else if (value.getClass().isArray()) {
            if (value instanceof int[]) {
                return ((int[]) value).clone();
            } else if (value instanceof long[]) {
                return ((long[]) value).clone();
            } else if (value instanceof float[]) {
                return ((float[]) value).clone();
            } else if (value instanceof double[]) {
                return ((double[]) value).clone();
            } else if (value instanceof Object[]) {
                return ((Object[]) value).clone();
            } else if (value instanceof byte[]) {
                return ((byte[]) value).clone();
            } else if (value instanceof short[]) {
                return ((short[]) value).clone();
            } else if (value instanceof char[]) {
                return ((char[]) value).clone();
            }
        }
        return value;
    }

    ArrayList deepCopyArrayList(ArrayList from) {
        final int N = from.size();
        ArrayList out = new ArrayList(N);
        for (int i = 0; i < N; i++) {
            out.add(deepCopyValue(from.get(i)));
        }
        return out;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void putBoolean(@Nullable String key, boolean value) {
        data.put(key, value);
    }

    public void putByte(@Nullable String key, byte value) {
        data.put(key, value);
    }

    public void putChar(@Nullable String key, char value) {
        data.put(key, value);
    }

    public void putShort(@Nullable String key, short value) {
        data.put(key, value);
    }

    public void putInt(@Nullable String key, int value) {
        data.put(key, value);
    }

    public void putLong(@Nullable String key, long value) {
        data.put(key, value);
    }

    public void putFloat(@Nullable String key, float value) {
        data.put(key, value);
    }

    public void putDouble(@Nullable String key, double value) {
        data.put(key, value);
    }

    public void putString(@Nullable String key, @Nullable String value) {
        data.put(key, value);
    }

    public void putCharSequence(@Nullable String key, @Nullable CharSequence value) {
        data.put(key, value);
    }

    public void putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        data.put(key, value);
    }

    public void putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        data.put(key, value);
    }

    public void putCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
        data.put(key, value);
    }

    public void putSerializable(@Nullable String key, @Nullable Serializable value) {
        data.put(key, value);
    }

    public void putBooleanArray(@Nullable String key, @Nullable boolean[] value) {
        data.put(key, value);
    }

    public void putByteArray(@Nullable String key, @Nullable byte[] value) {
        data.put(key, value);
    }

    public void putShortArray(@Nullable String key, @Nullable short[] value) {
        data.put(key, value);
    }

    public void putCharArray(@Nullable String key, @Nullable char[] value) {
        data.put(key, value);
    }

    public void putIntArray(@Nullable String key, @Nullable int[] value) {
        data.put(key, value);
    }

    public void putLongArray(@Nullable String key, @Nullable long[] value) {
        data.put(key, value);
    }

    public void putFloatArray(@Nullable String key, @Nullable float[] value) {
        data.put(key, value);
    }

    public void putDoubleArray(@Nullable String key, @Nullable double[] value) {
        data.put(key, value);
    }

    public void putStringArray(@Nullable String key, @Nullable String[] value) {
        data.put(key, value);
    }

    public void putCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
        data.put(key, value);
    }

    public void putParcelable(@Nullable String key, @Nullable Parcelable value) {
        data.put(key, value);
    }

    public void putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
        data.put(key, value);
    }

    public void putParcelableArrayList(@Nullable String key,
                                       @Nullable ArrayList<? extends Parcelable> value) {
        data.put(key, value);
    }

    public void putParcelableList(String key, List<? extends Parcelable> value) {
        data.put(key, value);
    }

    public void putSparseParcelableArray(@Nullable String key,
                                         @Nullable SparseArray<? extends Parcelable> value) {
        data.put(key, value);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Object o = data.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Boolean) o;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    public Byte getByte(String key, byte defaultValue) {
        Object o = data.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Byte) o;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public char getChar(String key) {
        return getChar(key, (char) 0);
    }

    public char getChar(String key, char defaultValue) {
        Object o = data.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Character) o;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    public short getShort(String key, short defaultValue) {
        Object o = data.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Short) o;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        Object o = data.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Integer) o;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public long getLong(String key) {
        return getLong(key, 0L);
    }

    public long getLong(String key, long defaultValue) {
        Object o = data.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Long) o;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }

    public float getFloat(String key, float defaultValue) {
        Object o = data.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Float) o;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    public double getDouble(String key) {
        return getDouble(key, 0.0);
    }

    public double getDouble(String key, double defaultValue) {
        Object o = data.get(key);
        if (o == null) {
            return defaultValue;
        }
        try {
            return (Double) o;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    @Nullable
    public String getString(@Nullable String key) {
        final Object o = data.get(key);
        try {
            return (String) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public String getString(@Nullable String key, String defaultValue) {
        final String s = getString(key);
        return (s == null) ? defaultValue : s;
    }

    @Nullable
    public CharSequence getCharSequence(@Nullable String key) {
        final Object o = data.get(key);
        try {
            return (CharSequence) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public CharSequence getCharSequence(@Nullable String key, CharSequence defaultValue) {
        final CharSequence cs = getCharSequence(key);
        return (cs == null) ? defaultValue : cs;
    }

    @Nullable
    public Serializable getSerializable(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (Serializable) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public ArrayList<Integer> getIntegerArrayList(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (ArrayList<Integer>) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public ArrayList<String> getStringArrayList(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (ArrayList<String>) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public ArrayList<CharSequence> getCharSequenceArrayList(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (ArrayList<CharSequence>) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public boolean[] getBooleanArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (boolean[]) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public byte[] getByteArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (byte[]) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public short[] getShortArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (short[]) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public char[] getCharArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (char[]) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public int[] getIntArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (int[]) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public long[] getLongArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (long[]) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public float[] getFloatArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (float[]) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public double[] getDoubleArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (double[]) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public String[] getStringArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (String[]) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public CharSequence[] getCharSequenceArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (CharSequence[]) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public <T extends Parcelable> T getParcelable(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public Parcelable[] getParcelableArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (Parcelable[]) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public <T extends Parcelable> ArrayList<T> getParcelableArrayList(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (ArrayList<T>) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Nullable
    public <T extends Parcelable> SparseArray<T> getSparseParcelableArray(@Nullable String key) {
        Object o = data.get(key);
        if (o == null) {
            return null;
        }
        try {
            return (SparseArray<T>) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public Object get(@Nullable String key) {
        return data.get(key);
    }

    public void putAll(Map<String, Object> map) {
        if (map == null || map.isEmpty()) return;
        data.putAll(map);
    }

}
