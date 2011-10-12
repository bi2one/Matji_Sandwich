package com.matji.sandwich;

import android.content.Context;
import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.session.SessionWritePostUtil;
import com.matji.sandwich.widget.AlbumView;

public class WritePostPictureActivity extends BaseActivity {
    private Context context;
    private AlbumView albumView;
    // private WritePostTabActivity parentActivity;
    private SessionWritePostUtil sessionUtil;

    public int setMainViewId() {
	return R.id.activity_write_post_picture;
    }
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	context = getApplicationContext();
	setContentView(R.layout.activity_write_post_picture);
	albumView = (AlbumView)findViewById(R.id.activity_write_post_picture_albumview);
	// parentActivity = (WritePostTabActivity)getParent();
	sessionUtil = new SessionWritePostUtil(context);
    }

    protected void onPause() {
	super.onPause();
	// Log.d("=====", "albumView files: " + albumView.getFiles().toString());
	sessionUtil.setPictureFiles(albumView.getFiles());
    }
}