package com.matji.sandwich.http.request;

import android.content.Context;
import android.location.Location;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.exception.WritePostMatjiException;
import com.matji.sandwich.exception.BitmapCompressMatjiException;
import com.matji.sandwich.exception.BitmapCompressIOMatjiException;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WritePostHttpRequest implements RequestCommand {
    private Context context;
    private AttachFileHttpRequest fileRequest;
    private PostHttpRequest postRequest;
    private ArrayList<File> images;
    
    public WritePostHttpRequest(Context context) {
	this.context = context;
	fileRequest = new AttachFileHttpRequest(context);
	postRequest = new PostHttpRequest(context);
    }

    public void actionNew(String post, String tags, int storeId, Location location, ArrayList<File> images) {
	this.images = images;
	
	if (storeId <= 0) {
	    postRequest.actionNew(post, tags, PostHttpRequest.Device.ANDROID, location.getLatitude(), location.getLongitude());
	} else {
	    postRequest.actionNew(post, tags, PostHttpRequest.Device.ANDROID, storeId);
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
	    File compressedFile = compressFile(file);
	    fileRequest.actionUpload(compressedFile, postId);
	    confirmValidFileRequest(fileRequest.request());
	}
    }

    private void confirmValidFileRequest(ArrayList<MatjiData> data) throws MatjiException { }

    private File compressFile(File file) throws MatjiException {
	File result;
	FileOutputStream resultStream;
	FileInputStream fileStream;
	try {
	    result = File.createTempFile("matji_", "_compressed.jpg");
	    resultStream = new FileOutputStream(result);
	    fileStream = new FileInputStream(file);
	} catch(IOException e) {
	    throw new BitmapCompressIOMatjiException();
	}
	
	Bitmap bitmap = BitmapFactory.decodeStream(fileStream);

	try {
	    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, resultStream);
	    resultStream.flush();
	    resultStream.close();
	    return result;
	} catch(IOException e) {
	    throw new BitmapCompressMatjiException();
	}
    }

    public void cancel() {
	fileRequest.cancel();
	postRequest.cancel();
    }
}