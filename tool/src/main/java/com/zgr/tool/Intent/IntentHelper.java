package com.zgr.tool.Intent;

import android.content.Intent;

import androidx.collection.ArrayMap;

import com.zgr.tool.other.Utils;
import com.zgr.tool.text.TextUtil;


/**
 * Created by zgr on 2019/9/2.
 * Intent帮助类，绕过Intent传递数据大小限制
 */

public class IntentHelper {

    public static final String INTENT_CONTENT_KEY = "intentContentKey";

    private final ArrayMap<String, CacheIntent> cacheData;

    private IntentHelper() {
        cacheData = new ArrayMap<>();
    }

    public static IntentHelper getInstance() {
        return IntentHelperHolder.instance;
    }

    private static class IntentHelperHolder {
        private static final IntentHelper instance = new IntentHelper();
    }

    public CacheIntent getCacheIntent(String key) {
        if (TextUtil.isEmpty(key)) return null;
        return cacheData.get(key);
    }

    public CacheIntent getCacheIntent(Intent intent) {
        if (intent == null) return null;
        String key;
        if (intent instanceof IIntent) {
            IIntent IIntent = (IIntent) intent;
            key = IIntent.getSuperStringExtra(INTENT_CONTENT_KEY);
        } else {
            key = intent.getStringExtra(INTENT_CONTENT_KEY);
        }
        return getCacheIntent(key);
    }

    public void removeCacheIntent(Intent intent) {
        if (intent == null) return;
        String key = intent.getStringExtra(INTENT_CONTENT_KEY);
        cacheData.remove(key);
    }

    public void addCacheIntent(String key, CacheIntent cacheIntent) {
        if (cacheIntent == null) return;
        cacheData.put(key, cacheIntent);
    }

    private CacheIntent setCacheIntent(Intent intent) {
        if (intent == null) return null;
        String key;
        if (intent instanceof IIntent) {
            IIntent IIntent = (IIntent) intent;
            key = IIntent.getSuperStringExtra(INTENT_CONTENT_KEY);
        } else {
            key = intent.getStringExtra(INTENT_CONTENT_KEY);
        }
        if (!TextUtil.isEmpty(key)) {
            return getCacheIntent(key);
        }
        CacheIntent cacheIntent = new CacheIntent();
        String uuid = Utils.getFormatUUID();
        if (intent instanceof IIntent) {
            IIntent IIntent = (IIntent) intent;
            IIntent.putSuperExtra(INTENT_CONTENT_KEY, uuid);
        } else {
            intent.putExtra(INTENT_CONTENT_KEY, uuid);
        }
        addCacheIntent(uuid, cacheIntent);
        return cacheIntent;
    }

    public Intent transitionNewIntent(Intent intent) {
        CacheIntent cacheIntent = IntentHelper.getInstance().getCacheIntent(intent);
        if (cacheIntent != null) {
            removeCacheIntent(intent);
            intent = new IIntent();
            CacheIntent newCacheIntent = IntentHelper.getInstance().setCacheIntent(intent);
            newCacheIntent.putAll(cacheIntent.getAll());
        }
        return intent;
    }


}
