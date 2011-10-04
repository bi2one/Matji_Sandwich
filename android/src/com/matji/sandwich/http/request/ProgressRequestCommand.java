package com.matji.sandwich.http.request;

import com.matji.sandwich.listener.ProgressListener;

public interface ProgressRequestCommand extends RequestCommand {
    public void setProgressListener(int tag, ProgressListener listener);
    public int getTotalCount();
}