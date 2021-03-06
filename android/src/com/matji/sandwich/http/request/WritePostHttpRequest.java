package com.matji.sandwich.http.request;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.WritePostMatjiException;
import com.matji.sandwich.listener.ProgressListener;

public class WritePostHttpRequest implements ProgressRequestCommand {
    private AttachFileHttpRequest fileRequest;
    private PostHttpRequest postRequest;
    private ArrayList<File> images;
    private int tag;
    private ProgressListener listener;
    private int readCount;
    //    private int totalCount;

    public WritePostHttpRequest(Context context) {
        fileRequest = new AttachFileHttpRequest(context);
        postRequest = new PostHttpRequest(context);
    }

    public void actionNew(String post, String tags, Store store, ArrayList<File> images, Boolean twitter, Boolean facebook) {
        if (store == null) {
            actionNew(post, tags, -1, images, twitter, facebook);
        } else {
            actionNew(post, tags, store.getId(), images, twitter, facebook);
        }
    }

    public void actionNew(String post, String tags, int storeId, ArrayList<File> images, Boolean twitter, Boolean facebook) {
        this.images = images;

        if (storeId <= 0) {
            postRequest.actionNew(post, tags, twitter, facebook);
        } else {
            postRequest.actionNew(post, tags, storeId, twitter, facebook);
        }
    }

    public void setProgressListener(int tag, ProgressListener listener) {
        fileRequest.setProgressListener(tag, listener);
        this.tag = tag;
        this.listener = listener;
    }

    public int getRequestCount() {
        return images.size();
    }

    private void writeUnit(int totalCount, int readCount) {
        if (listener != null) {
            listener.onUnitWritten(tag, totalCount, readCount);
        }
    }

    public ArrayList<MatjiData> request() throws MatjiException {
        ArrayList<MatjiData> postRequestData = postRequest.request();
        int postId = getPostId(postRequestData);

        if (postId <= 0) {
            throw new WritePostMatjiException();
        }

        uploadImages(postId);
        return postRequestData;
    }

    private int getPostId(ArrayList<MatjiData> writePostData) {
        if (writePostData != null && writePostData.size() > 0) {
            return ((Post)(writePostData.get(0))).getId();
        } else
            return -1;
    }

    private void uploadImages(int postId) throws MatjiException {
        if (images == null) {
            return ;
        }

        for (File file : images) {
            if (file != null) {
                // File compressedFile = ImageUtil.compressFile(file);
                fileRequest.actionUpload(file, postId);
                confirmValidFileRequest(fileRequest.request());
                writeUnit(getRequestCount(), ++readCount);
            }
        }
    }

    private void confirmValidFileRequest(ArrayList<MatjiData> data) throws MatjiException { }

    // public static File compressFile(File file) throws MatjiException {
    //     File result;
    //     FileOutputStream resultStream;
    //     FileInputStream fileStream;
    //     try {
    //         result = File.createTempFile("matji_", "_compressed.jpg");
    //         resultStream = new FileOutputStream(result);
    //         fileStream = new FileInputStream(file);
    //     } catch(IOException e) {
    //         throw new BitmapCompressIOMatjiException();
    //     }

    //     Bitmap bitmap = BitmapFactory.decodeStream(fileStream);

    //     try {
    //         bitmap.compress(Bitmap.CompressFormat.JPEG, 80, resultStream);
    //         resultStream.flush();
    //         resultStream.close();
    //         return result;
    //     } catch(IOException e) {
    //         throw new BitmapCompressMatjiException();
    //     }
    // }

    public void cancel() {
        fileRequest.cancel();
        postRequest.cancel();
    }
}