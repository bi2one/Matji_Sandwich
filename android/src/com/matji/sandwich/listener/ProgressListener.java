package com.matji.sandwich.listener;

public interface ProgressListener {
    public void onWritten(int tag, int totalBytes, int readBytes);
}