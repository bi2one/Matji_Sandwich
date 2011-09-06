package com.matji.sandwich.http.request;

import android.app.Activity;
import android.widget.ImageView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.net.http.AndroidHttpClient;

import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.cache.ImageCache;

import org.apache.http.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.InputStream;
import java.io.FilterInputStream;
import java.io.IOException;

public class ImageDownloadHttpRequest implements RequestCommand {
    public static enum UrlType {
	USER, ATTACH_FILE;

	public String toString() {
	    switch(this) {
	    case USER:
		return "http://222.122.205.227/v2/users/profile";
	    case ATTACH_FILE:
		return "http://222.122.205.227/v2/attach_files/image";
	    }
	    return null;
	}
	
	public String getIdString() {
	    switch(this) {
	    case USER:
		return "user_id";
	    case ATTACH_FILE:
		return "attach_file_id";
	    }
	    return null;
	}
    }

    public static enum ImageSize {
	SSMALL, SMALL, MEDIUM, LARGE, XLARGE;

	public String toString() {
	    switch(this) {
	    case SSMALL:
		return "ss";
	    case SMALL:
		return "s";
	    case MEDIUM:
		return "m";
	    case LARGE:
		return "l";
	    case XLARGE:
		return "xl";
	    }
	    return null;
	}
    }

    private UrlType type;
    private ImageSize size;
    private ImageView view;
    private Context context;
    private Activity activity;
    private String fileUrl;
    private int id;
    private boolean isRound;
    private SetImageRunnable runnable;
    
    public ImageDownloadHttpRequest(Activity activity, UrlType type, ImageSize size, ImageView view, String imageTag, int id) {
	this.context = activity;
	this.activity = activity;
	this.type = type;
	this.size = size;
	this.view = view;
	this.fileUrl = imageTag + "_" + size;
	this.id = id;
    }

    public ArrayList<MatjiData> request() throws MatjiException {
	Bitmap bitmap = ImageCache.getBitmap(context, fileUrl);
	
	if (bitmap == null) {
	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put(type.getIdString(), id + "");
	    params.put("size", size.toString());
	    
	    String url = HttpUtility.getUrlStringWithQuery(type.toString(), params);
	    bitmap = download(url);
	}
	    
	if (bitmap != null) {
	    activity.runOnUiThread(new SetImageRunnable(view, bitmap));
	}
	
	return null;
    }

    private Bitmap download(String url) throws MatjiException {
	AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
	HttpGet getRequest = new HttpGet(url);

	try {
	    HttpResponse response = client.execute(getRequest);
	    int statusCode = response.getStatusLine().getStatusCode();
	    if (statusCode != HttpStatus.SC_OK) {
		Log.d("Matji", "ImageDownloadHttpRequest: server status code error -> " + statusCode);
		return null;
	    }

	    HttpEntity entity = response.getEntity();
	    if (entity != null) {
		InputStream inputStream = null;
		try {
		    inputStream = entity.getContent();
		    return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
		} finally {
		    if (inputStream != null)
			inputStream.close();
		    entity.consumeContent();
		}
	    }
	} catch(IOException e) {
	    getRequest.abort();
	    Log.d("Matji", "ImageDownloadHttpRequest: IOException #1");
	} catch(IllegalStateException e) {
	    getRequest.abort();
	    Log.d("Matji", "ImageDownloadHttpRequest: IllegalStateException #2");
	} catch(Exception e) {
	    getRequest.abort();
	    Log.d("Matji", "ImageDownloadHttpRequest: Exception #3");
	} finally {
	    client.close();
	}
	return null;
    }

    private class FlushedInputStream extends FilterInputStream {
	public FlushedInputStream(InputStream inputStream) {
	    super(inputStream);
	}

	public long skip(long n) throws IOException {
	    long totalBytesSkipped = 0L;
	    while(totalBytesSkipped < n) {
		long bytesSkipped = in.skip(n - totalBytesSkipped);
		if (bytesSkipped == 0L) {
		    int b = read();
		    if (b < 0) {
			break;
		    } else {
			bytesSkipped = 1;
		    }
		}

		totalBytesSkipped += bytesSkipped;
	    }
	    return totalBytesSkipped;
	}
    }

    public void cancel() { }

    private class SetImageRunnable implements Runnable {
	ImageView imageView;
	Bitmap bitmap;

	public SetImageRunnable(ImageView imageView, Bitmap bitmap) {
	    this.imageView = imageView;
	    this.bitmap = bitmap;
	}
	
	public void run() {
	    imageView.setImageBitmap(bitmap);
	}
    }
}