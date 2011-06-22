package com.matji.sandwich;

public interface FileUploadProgressListener {
    public void onFileWritten(int tag, int totalBytes, int readBytes);
}