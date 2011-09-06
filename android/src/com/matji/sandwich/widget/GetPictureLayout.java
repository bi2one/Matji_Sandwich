package com.matji.sandwich.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.R;

public class GetPictureLayout extends RelativeLayout implements View.OnClickListener {
    private static final int LAYOUT_REFERENCE = R.layout.get_picture_layout;
    private Context context;
    private RelativeLayout cameraButton;
    private RelativeLayout albumButton;
    private OnClickListener listener;

    public GetPictureLayout(Context context) {
	super(context);
	init(context);
    }

    public GetPictureLayout(Context context, AttributeSet attrs) {
	super(context, attrs);
	init(context);
    }

    private void init(Context context) {
	this.context = context;
	LayoutInflater.from(context).inflate(LAYOUT_REFERENCE, this, true);
	cameraButton = (RelativeLayout)findViewById(R.id.get_picture_layout_camera);
	albumButton = (RelativeLayout)findViewById(R.id.get_picture_layout_album);

	cameraButton.setOnClickListener(this);
	albumButton.setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener listener) {
	this.listener = listener;
    }

    public void onClick(View v) {
	if (listener == null) {
	    return ;
	}
	
	int vId = v.getId();
	if (vId == cameraButton.getId()) {
	    listener.onCameraClick();
	} else if (vId == albumButton.getId()) {
	    listener.onAlbumClick();
	}
    }

    public interface OnClickListener {
	public void onCameraClick();
	public void onAlbumClick();
    }
}