package com.matji.sandwich.widget;

import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.util.PhotoUtil;
import com.matji.sandwich.util.MatjiConstants;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.File;

public class AlbumImageView extends RelativeLayout {
    private static final int LAYOUT_REFERENCE = R.layout.album_image_view;
    private static final int THUMBNAIL_WIDTH_REF = R.dimen.album_view_contents_width;
    private static final int THUMBNAIL_HEIGHT_REF = R.dimen.album_view_contents_height;
    private static final int THUMBNAIL_WIDTH = (int)MatjiConstants.dimen(THUMBNAIL_WIDTH_REF);
    private static final int THUMBNAIL_HEIGHT = (int)MatjiConstants.dimen(THUMBNAIL_HEIGHT_REF);
    private ImageView imageView;
    private Context context;
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
	this.context = context;
	LayoutInflater.from(context).inflate(LAYOUT_REFERENCE, this, true);
	imageView = (ImageView)findViewById(R.id.album_image_view_contents);
    }

    public File getFile() {
	return file;
    }

    public void setImage(File file) {
	this.file = file;
	imageView.setImageBitmap(decodeFile(file));
    }

    public boolean isFileEquals(File file) {
	return this.file.getAbsolutePath().equals(file.getAbsolutePath());
    }

    private Bitmap decodeFile(File f){
	try {
	    //Decode image size
	    BitmapFactory.Options o = new BitmapFactory.Options();
	    o.inJustDecodeBounds = true;
	    BitmapFactory.decodeStream(new FileInputStream(f),null,o);

	    //Find the correct scale value. It should be the power of 2.
	    int width_tmp=o.outWidth, height_tmp=o.outHeight;
	    int scale=1;

	    while(true){
		if(width_tmp/2<THUMBNAIL_WIDTH || height_tmp/2<THUMBNAIL_HEIGHT)
		    break;
		width_tmp/=2;
		height_tmp/=2;
		scale*=2;
	    }

	    //Decode with inSampleSize
	    BitmapFactory.Options o2 = new BitmapFactory.Options();
	    o2.inSampleSize=scale;
	    return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	} catch (FileNotFoundException e) {}
	return null;
    }
}