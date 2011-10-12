package com.matji.sandwich.exception;

import android.content.Context;
import android.widget.Toast;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.Collections;

public class ToastPool {
    private static volatile ToastPool obj;
    private Map<Context, Toast> pool;

    private ToastPool() {
        pool = Collections.synchronizedMap(new WeakHashMap<Context, Toast>());
    }

    public static ToastPool getInstance() {
        if (obj == null) {
            synchronized(ToastPool.class) {
                if (obj == null) {
                    obj = new ToastPool();
                }
            }
        }
        return obj;
    }

    public Toast getToast(Context context, String message, int duration) {
        Toast toast = pool.get(context);
        if (toast == null) {
            synchronized(ToastPool.class) {
                if (toast == null) {
                    toast = Toast.makeText(context, message, duration);
                    pool.put(context, toast);
                }
            }
        }

        toast.setText(message);
        toast.setDuration(duration);
        return toast;
    }
}