package com.matji.sandwich.widget;

import android.widget.RelativeLayout;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.matji.sandwich.R;

public class AlbumImageView extends RelativeLayout {
    private static final int LAYOUT_REFERENCE = R.layout.album_image_view;
    private Context context;
    
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
    }
}