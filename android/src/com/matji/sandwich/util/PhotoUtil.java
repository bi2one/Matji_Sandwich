package com.matji.sandwich.util;

import android.app.Activity;
import android.content.Intent;
import android.content.ContentResolver;
import android.provider.MediaStore;
import android.net.Uri;
import android.util.Log;
import android.database.Cursor;
import android.graphics.Matrix;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

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

    private Bitmap rotateBitmap(Bitmap bitmap, int angle) {
	Matrix matrix = new Matrix();
	matrix.postRotate(angle);
	return Bitmap.createBitmap(bitmap,
				   0,
				   0,
				   bitmap.getWidth(),
				   bitmap.getHeight(),
				   matrix,
				   true);
    }

    private void saveBitmap(File file, Bitmap bitmap) {
	// try {
	    // FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
	    // bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
	// } catch(IOException e) {
	    // Log.d("Matji", e.toString());
	// }
    }

    public File rotateBitmapFile(File bitmapFile) {
	ExifInterface exif = null;
	int orientation = ExifInterface.ORIENTATION_NORMAL;
	
	try {
	    exif = new ExifInterface(bitmapFile.getAbsolutePath());
	    orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					       ExifInterface.ORIENTATION_NORMAL);
	} catch(IOException e) {
	    Log.d("Matji", e.toString());
	    return bitmapFile;
	}
	Log.d("=====", "orientation: " + orientation);

	// try {
	    // Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(new FileInputStream(bitmapFile)));

	    switch(orientation) {
	    case ExifInterface.ORIENTATION_ROTATE_90:
		// saveBitmap(bitmapFile, rotateBitmap(bitmap, 90));
		break;
	    case ExifInterface.ORIENTATION_ROTATE_180:
		// saveBitmap(bitmapFile, rotateBitmap(bitmap, 180));
		break;
	    case ExifInterface.ORIENTATION_ROTATE_270:
		// saveBitmap(bitmapFile, rotateBitmap(bitmap, 270));
		break;
	    }
	// } catch(FileNotFoundException e) {
	//     Log.d("Matji", e.toString());
	// }

	return bitmapFile;
    }

    public File getFileFromIntent(IntentType intentType, Intent data) {
	switch(intentType) {
	case FROM_CAMERA:
	    return rotateBitmapFile(tempTakedPhoto);
	case FROM_ALBUM:
	    return rotateBitmapFile(new File(getRealPathFromURI(data.getData())));
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