package com.zgr.tool.Intent;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zgr.tool.other.Utils;
import com.zgr.tool.text.TextUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zgr on 2019/9/24.
 */

public class IIntent extends Intent {

    private CacheIntent cacheIntent;
    private String contextName;

    public IIntent() {
        super();
    }

    public IIntent(Intent o) {
        super(o);
    }

    public IIntent(String action) {
        super(action);
    }

    public IIntent(String action, Uri uri) {
        super(action, uri);
    }

    public IIntent(Context packageContext, Class<?> cls) {
        super(packageContext, cls);
        if (packageContext != null) {
            Class clazz = packageContext.getClass();
            contextName = clazz.getName();
        }
    }

    public IIntent(String action, Uri uri, Context packageContext, Class<?> cls) {
        super(action, uri, packageContext, cls);
        if (packageContext != null) {
            Class clazz = packageContext.getClass();
            contextName = clazz.getName();
        }
    }

    public CacheIntent getCacheIntent() {
        return IntentHelper.getInstance().getCacheIntent(super.getStringExtra(IntentHelper.INTENT_CONTENT_KEY));
    }

    @Override
    public boolean getBooleanExtra(String name, boolean defaultValue) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getBooleanExtra(name, defaultValue);
        }
        return super.getBooleanExtra(name, defaultValue);
    }

    @Override
    public byte getByteExtra(String name, byte defaultValue) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getByteExtra(name, defaultValue);
        }
        return super.getByteExtra(name, defaultValue);
    }

    @Override
    public short getShortExtra(String name, short defaultValue) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getShortExtra(name, defaultValue);
        }
        return super.getShortExtra(name, defaultValue);
    }

    @Override
    public char getCharExtra(String name, char defaultValue) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getCharExtra(name, defaultValue);
        }
        return super.getCharExtra(name, defaultValue);
    }

    @Override
    public int getIntExtra(String name, int defaultValue) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getIntExtra(name, defaultValue);
        }
        return super.getIntExtra(name, defaultValue);
    }

    @Override
    public long getLongExtra(String name, long defaultValue) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getLongExtra(name, defaultValue);
        }
        return super.getLongExtra(name, defaultValue);
    }

    @Override
    public float getFloatExtra(String name, float defaultValue) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getFloatExtra(name, defaultValue);
        }
        return super.getFloatExtra(name, defaultValue);
    }

    @Override
    public double getDoubleExtra(String name, double defaultValue) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getDoubleExtra(name, defaultValue);
        }
        return super.getDoubleExtra(name, defaultValue);
    }

    @Override
    public String getStringExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getStringExtra(name);
        }
        return super.getStringExtra(name);
    }

    @Override
    public CharSequence getCharSequenceExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getCharSequenceExtra(name);
        }
        return super.getCharSequenceExtra(name);
    }

    @Override
    public <T extends Parcelable> T getParcelableExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getParcelableExtra(name);
        }
        return super.getParcelableExtra(name);
    }

    @Override
    public Parcelable[] getParcelableArrayExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getParcelableArrayExtra(name);
        }
        return super.getParcelableArrayExtra(name);
    }

    @Override
    public <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getParcelableArrayListExtra(name);
        }
        return super.getParcelableArrayListExtra(name);
    }

    @Override
    public Serializable getSerializableExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getSerializableExtra(name);
        }
        return super.getSerializableExtra(name);
    }

    @Override
    public ArrayList<Integer> getIntegerArrayListExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getIntegerArrayListExtra(name);
        }
        return super.getIntegerArrayListExtra(name);
    }

    @Override
    public ArrayList<String> getStringArrayListExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getStringArrayListExtra(name);
        }
        return super.getStringArrayListExtra(name);
    }

    @Override
    public ArrayList<CharSequence> getCharSequenceArrayListExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getCharSequenceArrayListExtra(name);
        }
        return super.getCharSequenceArrayListExtra(name);
    }

    @Override
    public boolean[] getBooleanArrayExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getBooleanArrayExtra(name);
        }
        return super.getBooleanArrayExtra(name);
    }

    @Override
    public byte[] getByteArrayExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getByteArrayExtra(name);
        }
        return super.getByteArrayExtra(name);
    }

    @Override
    public short[] getShortArrayExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getShortArrayExtra(name);
        }
        return super.getShortArrayExtra(name);
    }

    @Override
    public char[] getCharArrayExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getCharArrayExtra(name);
        }
        return super.getCharArrayExtra(name);
    }

    @Override
    public int[] getIntArrayExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getIntArrayExtra(name);
        }
        return super.getIntArrayExtra(name);
    }

    @Override
    public long[] getLongArrayExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getLongArrayExtra(name);
        }
        return super.getLongArrayExtra(name);
    }

    @Override
    public float[] getFloatArrayExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getFloatArrayExtra(name);
        }
        return super.getFloatArrayExtra(name);
    }

    @Override
    public double[] getDoubleArrayExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getDoubleArrayExtra(name);
        }
        return super.getDoubleArrayExtra(name);
    }

    @Override
    public String[] getStringArrayExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getStringArrayExtra(name);
        }
        return super.getStringArrayExtra(name);
    }

    @Override
    public CharSequence[] getCharSequenceArrayExtra(String name) {
        CacheIntent cacheIntent = getCacheIntent();
        if (cacheIntent != null) {
            return cacheIntent.getCharSequenceArrayExtra(name);
        }
        return super.getCharSequenceArrayExtra(name);
    }

    private CacheIntent setCacheIntent() {
        ComponentName componentName = getComponent();
        if (componentName != null && !TextUtil.isEmpty(componentName.getClassName())) {
            String className = componentName.getClassName();
            if (!className.contains("xmz1")) return null;
        }
        IntentHelper intentHelper = IntentHelper.getInstance();
        CacheIntent cacheIntent = null;
        String key = super.getStringExtra(IntentHelper.INTENT_CONTENT_KEY);
        if (!TextUtil.isEmpty(key)) {
            cacheIntent = intentHelper.getCacheIntent(key);
        }
        if (cacheIntent == null) {
            cacheIntent = new CacheIntent();
            String uuid = Utils.getFormatUUID();
            super.putExtra(IntentHelper.INTENT_CONTENT_KEY, uuid);
            intentHelper.addCacheIntent(uuid, cacheIntent);
        }
        return cacheIntent;
    }

    @NonNull
    @Override
    public Intent putExtra(String name, boolean value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, byte value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, char value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, short value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, int value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, long value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, float value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, double value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, String value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, CharSequence value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, Parcelable value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, Parcelable[] value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putParcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putParcelableArrayListExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putIntegerArrayListExtra(String name, ArrayList<Integer> value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putIntegerArrayListExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putStringArrayListExtra(String name, ArrayList<String> value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putStringArrayListExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putCharSequenceArrayListExtra(String name, ArrayList<CharSequence> value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putCharSequenceArrayListExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, Serializable value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, boolean[] value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, byte[] value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, short[] value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, char[] value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, int[] value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, long[] value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, float[] value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, double[] value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, String[] value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, CharSequence[] value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }

    @NonNull
    @Override
    public Intent putExtra(String name, Bundle value) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            cacheIntent.putExtra(name, value);
            return this;
        }
        return super.putExtra(name, value);
    }


    @NonNull
    @Override
    public Intent putExtras(@NonNull Bundle extras) {
        CacheIntent cacheIntent = setCacheIntent();
        if (cacheIntent != null) {
            Set<String> keySet = extras.keySet();
            Map<String, Object> map = new HashMap<>();
            for (String key : keySet) {
                map.put(key, extras.get(key));
            }
            cacheIntent.putAll(map);
            return this;
        }
        return super.putExtras(extras);
    }


    @Nullable
    @Override
    public Bundle getExtras() {
        return super.getExtras();
    }

    public String getSuperStringExtra(String name){
        return super.getStringExtra(name);
    }

    public void putSuperExtra(String name, String content){
        super.putExtra(name,content);
    }

}
