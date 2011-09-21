package com.matji.sandwich.exception;

import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;

public class ToastPool {
    private static volatile ToastPool obj;
    private HashMap<Context, Toast> pool;

    private ToastPool() {
	pool = new HashMap();
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

    public Toast getToast(Context context, int resId, int duration) {
	Toast toast = pool.get(context);
	if (toast == null) {
	    synchronized(ToastPool.class) {
		if (toast == null) {
		    toast = Toast.makeText(context, resId, duration);
		    pool.put(context, toast);
		}
	    }
	}
	
	toast.setText(resId);
	toast.setDuration(duration);
	return toast;
    }
}