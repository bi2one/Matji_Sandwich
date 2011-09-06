package com.matji.sandwich.http.request.cache;

import android.content.Context;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCache {
    private static String EXTENSION = ".png";

    private static String generateFileName(String url) {
	return url.hashCode() + EXTENSION;
    }

    public static void addBitmap(Context context, String url, Bitmap bitmap) {
	File cacheDir = context.getCacheDir();
	String fileName = generateFileName(url);
	File bitmapFile = new File(cacheDir, fileName);
	FileOutputStream out = null;

	try {
	    out = new FileOutputStream(bitmapFile);
	    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
	} catch(FileNotFoundException e) {
	    Log.d("Matji", e.toString());
	} finally {
	    if (out != null) {
		try {
		    out.close();
		} catch (IOException e) {
		    Log.d("Matji", e.toString());
		}
	    }
	}
    }

    public static Bitmap getBitmap(Context context, String url) {
	File cacheDir = context.getCacheDir();
	String fileName = generateFileName(url);
	File bitmapFile = new File(cacheDir, fileName);
	if (bitmapFile.exists()) {
	    return BitmapFactory.decodeFile(bitmapFile.toString());
	} else {
	    return null;
	}
    }
}
