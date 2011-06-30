package com.matji.sandwich.listener;

public interface FileUploadProgressListener {
    public void onFileWritten(int tag, int totalBytes, int readBytes);
}