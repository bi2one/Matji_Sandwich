package com.matji.sandwich;

import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.widget.TabHost;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TabHost.OnTabChangeListener;
import android.util.Log;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.widget.RoundTabHost;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.PhotoUtil;
import com.matji.sandwich.util.PhotoUtil.IntentType;
import com.matji.sandwich.widget.GetPictureLayout;
import com.matji.sandwich.widget.RelativeLayoutThatDetectsSoftKeyboard;
import com.matji.sandwich.widget.AlbumView;
import com.matji.sandwich.widget.indicator.Indicator;
import com.matji.sandwich.widget.indicator.Checkable;

import java.io.File;

public class WritePostTabActivity extends BaseTabActivity implements OnTabChangeListener,
								     RelativeLayoutThatDetectsSoftKeyboard.Listener,
								     GetPictureLayout.OnClickListener {
    public static final String TAB_ID_POST = "WritePostTabActivity.tab_id_post";
    public static final String TAB_ID_STORE = "WritePostTabActivity.tab_id_store";
    public static final String TAB_ID_PICTURE = "WritePostTabActivity.tab_id_picture";
    public static final String TAB_ID_TAG = "WritePostTabActivity.tab_id_tag";
    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;
    private Context context;
    private RelativeLayoutThatDetectsSoftKeyboard mainView;
    private RoundTabHost tabHost;
    private GetPictureLayout pictureKeyboard;
    private int contentHeightWithoutKeyboard;
    private int contentHeightWithKeyboard;
    private int keyboardHeight;
    private LayoutParams keyboardLayoutParams;
    private LayoutParams keyboardBasicLayoutParams;
    private boolean isShowPictureKeyboard;
    private boolean isShowPictureKeyboardAfterHide;
    private boolean isKeyboardShowing;
    private View currentView;
    private PhotoUtil photoUtil;
    private AlbumView albumView;
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_write_post_tab);

	context = getApplicationContext();
	pictureKeyboard = (GetPictureLayout)findViewById(R.id.activity_write_post_tab_picture_keyboard);
	pictureKeyboard.setOnClickListener(this);
	isShowPictureKeyboard = false;
	isShowPictureKeyboardAfterHide = false;
	keyboardBasicLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, 0);
	keyboardBasicLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	photoUtil = new PhotoUtil(this);
	
	mainView = (RelativeLayoutThatDetectsSoftKeyboard)findViewById(R.id.activity_write_post_tab);
	mainView.setListener(this);
	
	tabHost = (RoundTabHost)getTabHost();
	tabHost.addLeftTab(TAB_ID_POST,
			   R.string.default_string_post,
			   new Intent(this, WritePostActivity.class));
	tabHost.addCenterCheckTab(TAB_ID_STORE,
				  R.string.default_string_store,
				  new Intent(this, WritePostStoreActivity.class));
	tabHost.addCenterCheckTab(TAB_ID_PICTURE,
				  R.string.default_string_picture,
				  new Intent(this, WritePostPictureActivity.class));
	tabHost.addRightCheckTab(TAB_ID_TAG,
				 R.string.default_string_tag,
				 new Intent(this, RankingAllListActivity.class));
	tabHost.setOnTabChangedListener(this);
    }

    public void onTabChanged(String tabId) {
	currentView = tabHost.getCurrentView();
	hidePictureKeyboardAfterHide();
	
	if (tabId.equals(TAB_ID_STORE)) {
	    KeyboardUtil.hideKeyboard(this);
	    hidePictureKeyboard();
	} else if (tabId.equals(TAB_ID_POST)) {
	    View postText = currentView.findViewById(R.id.activity_write_post_text);
	    hidePictureKeyboard();
	    KeyboardUtil.showKeyboard(this, postText);
	} else if (tabId.equals(TAB_ID_PICTURE)) {
	    if (albumView == null) {
		albumView = (AlbumView)currentView.findViewById(R.id.activity_write_post_picture_albumview);
	    }
	    
	    KeyboardUtil.hideKeyboard(this);
	    if (isKeyboardShowing) {
		showPictureKeyboardAfterHide();
	    } else {
		showPictureKeyboard();
	    }
	}
    }

    public void onChecked(String tabId) {
	Indicator indicator = tabHost.getIndicator(tabId);
	if (indicator instanceof Checkable) {
	    ((Checkable)indicator).setCheck(true);
	}
    }

    public void onSoftKeyboardShown(boolean isShowing) {
	isKeyboardShowing = isShowing;
	if (isShowing) {
	    contentHeightWithoutKeyboard = mainView.getHeight();
	} else {
	    contentHeightWithKeyboard = mainView.getHeight();
	}

	if (keyboardHeight <= 0 && contentHeightWithoutKeyboard > 0 && contentHeightWithKeyboard > 0) {
	    keyboardHeight = contentHeightWithoutKeyboard - contentHeightWithKeyboard;
	    keyboardLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, keyboardHeight);
	    keyboardLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

	    if (isShowPictureKeyboard) {
		showPictureKeyboard();
		isShowPictureKeyboard = false;
	    }
	}

	if (!isShowing && isShowPictureKeyboardAfterHide) {
	    showPictureKeyboard();
	}
    }

    public void onCameraClick() {
	Intent cameraIntent = photoUtil.getIntent(IntentType.FROM_CAMERA);
	startActivityForResult(cameraIntent, FROM_CAMERA);
    }

    public void onAlbumClick() {
	Intent albumIntent = photoUtil.getIntent(IntentType.FROM_ALBUM);
	startActivityForResult(albumIntent, FROM_ALBUM);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	File imageFile = null;
	if (resultCode == RESULT_OK) {
	    switch(requestCode) {
	    case FROM_CAMERA:
		imageFile = photoUtil.getFileFromIntent(PhotoUtil.IntentType.FROM_CAMERA, data);
		if(albumView.pushImage(imageFile)) {
		    onChecked(TAB_ID_PICTURE);
		}
		break;
	    case FROM_ALBUM:
		imageFile = photoUtil.getFileFromIntent(PhotoUtil.IntentType.FROM_ALBUM, data);
		if (albumView.pushImage(imageFile)) {
		    onChecked(TAB_ID_PICTURE);
		}
	    }
	}
    }

    private void showPictureKeyboardAfterHide() {
	isShowPictureKeyboardAfterHide = true;
    }

    private void hidePictureKeyboardAfterHide() {
	isShowPictureKeyboardAfterHide = false;
    }
    
    private void showPictureKeyboard() {
	if (keyboardLayoutParams == null) {
	    isShowPictureKeyboard = true;
	} else {
	    pictureKeyboard.setLayoutParams(keyboardLayoutParams);
	    pictureKeyboard.setVisibility(View.VISIBLE);
	}
    }

    private void hidePictureKeyboard() {
	pictureKeyboard.setVisibility(View.GONE);
	pictureKeyboard.setLayoutParams(keyboardBasicLayoutParams);
    }
}