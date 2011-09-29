package com.matji.sandwich.util;

import android.app.Activity;
import android.content.Intent;
import android.content.ContentResolver;
import android.provider.MediaStore;
import android.net.Uri;
import android.util.Log;
import android.database.Cursor;
import android.graphics.Bitmap;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;
import java.io.IOException;

public class PhotoUtil {
    public static enum IntentType {
	FROM_CAMERA, FROM_ALBUM
    }

    private static Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private static String[] IMAGE_PROJECTION = {
	MediaStore.Images.ImageColumns.DATA,
	MediaStore.Images.Thumbnails.DATA
    };

    private Activity activity;
    private ContentResolver resolver;
    private File tempTakedPhoto;

    public PhotoUtil(Activity activity) {
	this.activity = activity;
	resolver = activity.getContentResolver();
    }

    public Intent getIntent(IntentType intentType) {
	Intent result = new Intent();
	switch(intentType) {
	case FROM_CAMERA:
	    try {
		tempTakedPhoto = File.createTempFile("matji_", "_tmp_image");
	    } catch(IOException e) {
		Log.e("Matji", e.toString());
		return null;
	    }
	    
	    Uri outputFileUri = Uri.fromFile(tempTakedPhoto);
	    result.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
	    result.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
	    break;
	case FROM_ALBUM:
	    result.setAction(Intent.ACTION_GET_CONTENT);
	    result.setType("image/*");
	    break;
	}
	return result;
    }

    public File getFileFromIntent(IntentType intentType, Intent data) {
	switch(intentType) {
	case FROM_CAMERA:
	    return tempTakedPhoto;
	case FROM_ALBUM:
	    return new File(getRealPathFromURI(data.getData()));
	default:
	    return null;
	}
    }

    private String getRealPathFromURI(Uri contentUri){
	String [] proj={MediaStore.Images.Media.DATA};
	Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);
	int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	cursor.moveToFirst();
	return cursor.getString(column_index);
    }
}