package com.matji.sandwich;

public interface FileUploadProgressListener {
    public void onFileWritten(int totalBytes, int readBytes);
}