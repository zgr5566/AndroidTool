package com.zgr.tool.Intent;

import android.net.Uri;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by zgr on 2018/3/3.
 * 缓存Intent
 */

public class CacheIntent implements Serializable {

    private static final long serialVersionUID = 5927L;

    private String action;
    private Set<String> categories;
    private String className;
    private String packName;
    private Uri data;
    private int flags;
    private int requestCode;
    private String intentContentKey;
    private boolean hasComponent;
    private CacheBundle bundle;

    public CacheIntent() {
        bundle = new CacheBundle();
    }

    public CacheBundle getBundle() {
        return bundle;
    }

    public void setBundle(CacheBundle bundle) {
        this.bundle = bundle;
    }

    public boolean isHasComponent() {
        return hasComponent;
    }

    public void setHasComponent(boolean hasComponent) {
        this.hasComponent = hasComponent;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getIntentContentKey() {
        return intentContentKey;
    }

    public void setIntentContentKey(String intentContentKey) {
        this.intentContentKey = intentContentKey;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Uri getData() {
        return data;
    }

    public void setData(Uri data) {
        this.data = data;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @NonNull
    public CacheIntent putExtra(String name, boolean value) {
        bundle.putBoolean(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, byte value) {
        bundle.putByte(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, char value) {
        bundle.putChar(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, short value) {
        bundle.putShort(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, int value) {
        bundle.putInt(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, long value) {
        bundle.putLong(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, float value) {
        bundle.putFloat(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, double value) {
        bundle.putDouble(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, String value) {
        bundle.putString(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, CharSequence value) {
        bundle.putCharSequence(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, Parcelable value) {
        bundle.putParcelable(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, Parcelable[] value) {
        bundle.putParcelableArray(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putParcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putIntegerArrayListExtra(String name, ArrayList<Integer> value) {
        bundle.putIntegerArrayList(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putStringArrayListExtra(String name, ArrayList<String> value) {
        bundle.putStringArrayList(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putCharSequenceArrayListExtra(String name, ArrayList<CharSequence> value) {
        bundle.putCharSequenceArrayList(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, Serializable value) {
        bundle.putSerializable(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, boolean[] value) {
        bundle.putBooleanArray(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, byte[] value) {
        bundle.putByteArray(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, short[] value) {
        bundle.putShortArray(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, char[] value) {
        bundle.putCharArray(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, int[] value) {
        bundle.putIntArray(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, long[] value) {
        bundle.putLongArray(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, float[] value) {
        bundle.putFloatArray(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, double[] value) {
        bundle.putDoubleArray(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, String[] value) {
        bundle.putStringArray(name, value);
        return this;
    }

    @NonNull
    public CacheIntent putExtra(String name, CharSequence[] value) {
        bundle.putCharSequenceArray(name, value);
        return this;
    }

    /**
     * Retrieve extended data from the intent.
     *
     * @param name The name of the desired item.
     * @return the value of an item that previously added with putExtra()
     * or null if none was found.
     * @hide
     * @deprecated
     */
    @Deprecated
    public Object getExtra(String name) {
        return getExtra(name, null);
    }

    public boolean getBooleanExtra(String name, boolean defaultValue) {
        return bundle == null ? defaultValue :
                bundle.getBoolean(name, defaultValue);
    }

    public byte getByteExtra(String name, byte defaultValue) {
        return bundle == null ? defaultValue :
                bundle.getByte(name, defaultValue);
    }

    public short getShortExtra(String name, short defaultValue) {
        return bundle == null ? defaultValue :
                bundle.getShort(name, defaultValue);
    }

    public char getCharExtra(String name, char defaultValue) {
        return bundle == null ? defaultValue :
                bundle.getChar(name, defaultValue);
    }

    public int getIntExtra(String name, int defaultValue) {
        return bundle == null ? defaultValue :
                bundle.getInt(name, defaultValue);
    }

    public long getLongExtra(String name, long defaultValue) {
        return bundle == null ? defaultValue :
                bundle.getLong(name, defaultValue);
    }

    public float getFloatExtra(String name, float defaultValue) {
        return bundle == null ? defaultValue :
                bundle.getFloat(name, defaultValue);
    }

    public double getDoubleExtra(String name, double defaultValue) {
        return bundle == null ? defaultValue :
                bundle.getDouble(name, defaultValue);
    }

    public String getStringExtra(String name) {
        return bundle == null ? null : bundle.getString(name);
    }

    public CharSequence getCharSequenceExtra(String name) {
        return bundle == null ? null : bundle.getCharSequence(name);
    }

    public <T extends Parcelable> T getParcelableExtra(String name) {
        return bundle == null ? null : bundle.<T>getParcelable(name);
    }

    public Parcelable[] getParcelableArrayExtra(String name) {
        return bundle == null ? null : bundle.getParcelableArray(name);
    }

    public <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(String name) {
        return bundle == null ? null : bundle.<T>getParcelableArrayList(name);
    }

    public Serializable getSerializableExtra(String name) {
        return bundle == null ? null : bundle.getSerializable(name);
    }

    public ArrayList<Integer> getIntegerArrayListExtra(String name) {
        return bundle == null ? null : bundle.getIntegerArrayList(name);
    }

    public ArrayList<String> getStringArrayListExtra(String name) {
        return bundle == null ? null : bundle.getStringArrayList(name);
    }

    public ArrayList<CharSequence> getCharSequenceArrayListExtra(String name) {
        return bundle == null ? null : bundle.getCharSequenceArrayList(name);
    }

    public boolean[] getBooleanArrayExtra(String name) {
        return bundle == null ? null : bundle.getBooleanArray(name);
    }

    public byte[] getByteArrayExtra(String name) {
        return bundle == null ? null : bundle.getByteArray(name);
    }

    public short[] getShortArrayExtra(String name) {
        return bundle == null ? null : bundle.getShortArray(name);
    }

    public char[] getCharArrayExtra(String name) {
        return bundle == null ? null : bundle.getCharArray(name);
    }

    public int[] getIntArrayExtra(String name) {
        return bundle == null ? null : bundle.getIntArray(name);
    }

    public long[] getLongArrayExtra(String name) {
        return bundle == null ? null : bundle.getLongArray(name);
    }

    public float[] getFloatArrayExtra(String name) {
        return bundle == null ? null : bundle.getFloatArray(name);
    }

    public double[] getDoubleArrayExtra(String name) {
        return bundle == null ? null : bundle.getDoubleArray(name);
    }

    public String[] getStringArrayExtra(String name) {
        return bundle == null ? null : bundle.getStringArray(name);
    }

    public CharSequence[] getCharSequenceArrayExtra(String name) {
        return bundle == null ? null : bundle.getCharSequenceArray(name);
    }


    @Deprecated
    public Object getExtra(String name, Object defaultValue) {
        Object result = defaultValue;
        if (bundle != null) {
            Object result2 = bundle.get(name);
            if (result2 != null) {
                result = result2;
            }
        }

        return result;
    }

    public void putAll(Map<String, Object> map) {
        bundle.putAll(map);
    }

    public Map<String, Object> getAll() {
        if (bundle != null) {
            return bundle.getData();
        }
        return null;
    }
}
