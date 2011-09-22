package com.matji.sandwich.util;

import android.content.Context;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.ImageColumns;
import android.location.Location;

import java.io.File;

public class SaveToGalleryUtil {
    public static enum Orientation {
	TOP, BOTTOM, LEFT, RIGHT;
	public int toInt() {
	    switch(this) {
	    case TOP:
		return 0;
	    case RIGHT:
		return 90;
	    case BOTTOM:
		return 180;
	    case LEFT:
		return 270;
	    default:
		return 0;
	    }
	}
    }
    
    public static Uri save(Context context, File imageFile, String title, String description, long dateTaken, Orientation orientation, Location loc, String mimeType) {
	ContentValues v = new ContentValues();
	v.put(Media.TITLE, title);
	v.put(Media.DISPLAY_NAME, imageFile.getName());
	v.put(Media.DESCRIPTION, description);
	v.put(Media.DATE_ADDED, dateTaken);
	v.put(Media.DATE_TAKEN, dateTaken);
	v.put(Media.DATE_MODIFIED, dateTaken);
	v.put(Media.MIME_TYPE, "image/jpeg");
	v.put(Media.ORIENTATION, orientation.toInt());

	File parent = imageFile.getParentFile();
	String path = parent.toString().toLowerCase();
	String name = parent.getName().toLowerCase();

	v.put(ImageColumns.BUCKET_ID, path.hashCode());
	v.put(ImageColumns.BUCKET_DISPLAY_NAME, name);
	v.put(Media.SIZE, imageFile.length());

	if (loc != null) {
	    v.put(Media.LATITUDE, loc.getLatitude());
	    v.put(Media.LONGITUDE, loc.getLongitude());
	}

	v.put("_data", imageFile.toString());
	ContentResolver c = context.getContentResolver();
	return c.insert(Media.EXTERNAL_CONTENT_URI, v);
    }

    public static Uri save(Context context, String imagePath, String title, String description, long dateTaken, Orientation orientation, Location loc, String mimeType) {
	return save(context, new File(imagePath), title, description, dateTaken, orientation, loc, mimeType);
    }
}