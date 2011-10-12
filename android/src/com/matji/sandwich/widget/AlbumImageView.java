package com.matji.sandwich.widget;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.matji.sandwich.R;
import com.matji.sandwich.util.ImageUtil;
import com.matji.sandwich.util.MatjiConstants;

public class AlbumImageView extends RelativeLayout {
    private static final int LAYOUT_REFERENCE = R.layout.album_image_view;
//    private static final int THUMBNAIL_WIDTH_REF = R.dimen.album_view_contents_width;
//    private static final int THUMBNAIL_HEIGHT_REF = R.dimen.album_view_contents_height;
//    private static final int THUMBNAIL_WIDTH = (int)MatjiConstants.dimen(THUMBNAIL_WIDTH_REF);
//    private static final int THUMBNAIL_HEIGHT = (int)MatjiConstants.dimen(THUMBNAIL_HEIGHT_REF);
    private static final Drawable emptyDrawable = (Drawable)MatjiConstants.drawable(R.drawable.box_restaurant_plus);
    private ImageView imageView;
    private Bitmap thumbnail;
    private File file;
    
    public AlbumImageView(Context context) {
	super(context);
	init(context);
    }

    public AlbumImageView(Context context, AttributeSet attrs) {
	super(context, attrs);
	init(context);
    }

    private void init(Context context) {
	LayoutInflater.from(context).inflate(LAYOUT_REFERENCE, this, true);
	imageView = (ImageView)findViewById(R.id.album_image_view_contents);
    }

    public Bitmap getThumbnail() {
	return thumbnail;
    }

    public boolean isEmpty() {
	return file == null;
    }

    public File getFile() {
	return file;
    }

    public void setImage(File file, Bitmap bitmap) {
	if (bitmap == null) {
	    setImage(file);
	    return;
	}
    
	this.file = file;
	thumbnail = bitmap;
	imageView.setImageBitmap(bitmap);
    }

    public void setImage(File file) {
	setImage(file, ImageUtil.decodeFile(file, true));
    }

    public void removeImage() {
	this.file = null;
	thumbnail = null;
	imageView.setImageDrawable(emptyDrawable);
    }
    
    public boolean isFileEquals(File file) {
	return this.file.getAbsolutePath().equals(file.getAbsolutePath());
    }
}