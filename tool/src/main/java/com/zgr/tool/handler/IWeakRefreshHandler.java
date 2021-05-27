package com.zgr.tool.handler;


import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

public class IWeakRefreshHandler extends Handler {

    private final WeakReference<HandlerReference> weakReference;

    public IWeakRefreshHandler(HandlerReference reference){
        weakReference = new WeakReference<>(reference);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        HandlerReference reference = weakReference.get();
        if(reference != null){
            reference.dealMessage(msg);
        }
    }

    public interface HandlerReference{
        void dealMessage(Message msg);
    }

}
