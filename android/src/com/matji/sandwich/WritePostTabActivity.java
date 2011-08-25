package com.matji.sandwich;

import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TabHost.OnTabChangeListener;
import android.location.Location;
import android.util.Log;

import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.widget.RoundTabHost;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.PhotoUtil;
import com.matji.sandwich.util.PhotoUtil.IntentType;
import com.matji.sandwich.util.adapter.GeoPointToLocationAdapter;
import com.matji.sandwich.widget.GetPictureLayout;
import com.matji.sandwich.widget.RelativeLayoutThatDetectsSoftKeyboard;
import com.matji.sandwich.widget.AlbumView;
import com.matji.sandwich.widget.indicator.Indicator;
import com.matji.sandwich.widget.indicator.Checkable;
import com.matji.sandwich.widget.title.WritePostTitle;
import com.matji.sandwich.widget.dialog.MatjiAlertDialog;
import com.matji.sandwich.http.HttpRequestManager;
// import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.http.request.WritePostHttpRequest;
import com.matji.sandwich.session.SessionWritePostUtil;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;

import java.io.File;
import java.util.ArrayList;

public class WritePostTabActivity extends BaseTabActivity implements OnTabChangeListener,
								     Requestable,
								     RelativeLayoutThatDetectsSoftKeyboard.Listener,
								     GetPictureLayout.OnClickListener,
								     WritePostTitle.OnClickListener {
    public static final String TAB_ID_POST = "WritePostTabActivity.tab_id_post";
    public static final String TAB_ID_STORE = "WritePostTabActivity.tab_id_store";
    public static final String TAB_ID_PICTURE = "WritePostTabActivity.tab_id_picture";
    public static final String TAB_ID_TAG = "WritePostTabActivity.tab_id_tag";
    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;
    private static final int REQUEST_WRITE_POST = 0;
    private static final int REQUEST_UPLOAD_IMAGE = 1;
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
    private boolean isHidePictureKeyboardAfterHide;
    private boolean isKeyboardShowing;
    private View currentView;
    private PhotoUtil photoUtil;
    private AlbumView albumView;
    private WritePostTitle titleBar;
    private HttpRequestManager requestManager;
    private SessionWritePostUtil sessionUtil;
    private SessionMapUtil sessionMapUtil;
    private KeyboardListener keyboardListener;
    private MatjiAlertDialog postEmptyDialog;
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_write_post_tab);

	context = getApplicationContext();
	pictureKeyboard = (GetPictureLayout)findViewById(R.id.activity_write_post_tab_picture_keyboard);
	pictureKeyboard.setOnClickListener(this);
	requestManager = HttpRequestManager.getInstance(context);
	sessionUtil = new SessionWritePostUtil(context);
	sessionUtil.clear();
	sessionMapUtil = new SessionMapUtil(context);
	isShowPictureKeyboard = false;
	isShowPictureKeyboardAfterHide = false;
	keyboardBasicLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, 0);
	keyboardBasicLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	photoUtil = new PhotoUtil(this);
	postEmptyDialog = new MatjiAlertDialog(this, R.string.write_post_tab_activity_post_empty);
	
	mainView = (RelativeLayoutThatDetectsSoftKeyboard)findViewById(R.id.activity_write_post_tab);
	mainView.setListener(this);
	titleBar = (WritePostTitle)findViewById(R.id.activity_write_post_tab_title);
	titleBar.setOnClickListener(this);
	
	tabHost = (RoundTabHost)getTabHost();
	tabHost.addLeftTab(TAB_ID_POST,
			   R.string.default_string_post,
			   new Intent(this, WritePostActivity.class));
	tabHost.addCenterCheckTab(TAB_ID_STORE,
				  R.string.default_string_store,
				  new Intent(this, WritePostStoreActivityGroup.class));
	tabHost.addCenterCheckTab(TAB_ID_PICTURE,
				  R.string.default_string_picture,
				  new Intent(this, WritePostPictureActivity.class));
	tabHost.addRightCheckTab(TAB_ID_TAG,
				 R.string.default_string_tag,
				 new Intent(this, WritePostTagActivity.class));
	tabHost.setOnTabChangedListener(this);
    }

    protected void onResume() {
	super.onResume();
	requestManager.turnOn();
    }

    public void onTabChanged(String tabId) {
	currentView = tabHost.getCurrentView();
	isShowPictureKeyboardAfterHide = false;
	if (tabId.equals(TAB_ID_STORE)) {
	    KeyboardUtil.hideKeyboard(this);
	    hidePictureKeyboard();
	} else if (tabId.equals(TAB_ID_POST)) {
	    View postText = currentView.findViewById(R.id.activity_write_post_text);
	    if (!isKeyboardShowing) {
		hidePictureKeyboardAfterHide();
	    } else {
		hidePictureKeyboard();
	    }
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
	} else if (tabId.equals(TAB_ID_TAG)) {
	    KeyboardUtil.hideKeyboard(this);
	    hidePictureKeyboard();
	}
    }

    public void onChecked(String tabId, boolean isCheck) {
	Indicator indicator = tabHost.getIndicator(tabId);
	if (indicator instanceof Checkable) {
	    ((Checkable)indicator).setCheck(isCheck);
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

	if (isShowing && isHidePictureKeyboardAfterHide) {
	    hidePictureKeyboard();
	}

	if (keyboardListener != null) {
	    keyboardListener.onKeyboardStateChanged(isShowing);
	}
    }

    public void setKeyboardListener(KeyboardListener keyboardListener) {
	this.keyboardListener = keyboardListener;
    }

    public void removeKeyboardListener() {
	setKeyboardListener(null);
    }

    public void onCompleteClick() {
	if (requestManager.isRunning()) {
	    return ;
	}
	
	String currentTab = tabHost.getCurrentTabTag();
	View currentView = tabHost.getCurrentView();
	WritePostHttpRequest postRequest = new WritePostHttpRequest(context);
	Location centerLocation = new GeoPointToLocationAdapter(sessionMapUtil.getCenter());

	String post = findPost(currentTab);
	int storeId = findStoreId(currentTab);
	ArrayList<File> images = findImages(currentTab);
	String tags = findTags(currentTab);

	if (!isValidPost(post)) {
	    return ;
	}
	
	postRequest.actionNew(post, tags, storeId, centerLocation, images);
	requestManager.request(this, postRequest, REQUEST_WRITE_POST, this);
    }

    private boolean isValidPost(String post) {
	if (post.trim().equals("")) {
	    postEmptyDialog.show();
	    return false;
	}
	return true;
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	switch(tag) {
	case REQUEST_WRITE_POST:
	    finish();
	}
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
	e.performExceptionHandling(context);
    }

    private String findTags(String tabLabel) {
	if (tabLabel.equals(TAB_ID_TAG)) {
	    EditText tagsEditText = (EditText)findViewFromCurrentView(R.id.activity_write_post_tag_text);
	    return tagsEditText.getText().toString().trim();
	} else
	    return sessionUtil.getTags();
    }

    private String findPost(String tabLabel) {
	if (tabLabel.equals(TAB_ID_POST)) {
	    TextView postTextView = (TextView)findViewFromCurrentView(R.id.activity_write_post_text);
	    return postTextView.getText().toString();
	} else
	    return sessionUtil.getPost();
    }

    private int findStoreId(String tabLabel) {
	if (tabLabel.equals(TAB_ID_STORE)) {
	    Store store = (Store) findViewFromCurrentView(R.id.activity_write_post_store_selected).getTag();
	    if (store == null)
		return 0;
	    else
		return store.getId();
	} else
	    return sessionUtil.getStoreId();
    }

    private ArrayList<File> findImages(String tabLabel) {
	if (tabLabel.equals(TAB_ID_PICTURE)) {
	    AlbumView album = (AlbumView)findViewFromCurrentView(R.id.activity_write_post_picture_albumview);
	    return album.getFiles();
	} else
	    return sessionUtil.getPictureFiles();
    }

    private View findViewFromCurrentView(int id) {
	View currentView = tabHost.getCurrentView();
	return currentView.findViewById(id);
    }

    public void onCameraClick() {
	Intent cameraIntent = photoUtil.getIntent(IntentType.FROM_CAMERA);
	requestManager.turnOff();
	startActivityForResult(cameraIntent, FROM_CAMERA);
    }

    public void onAlbumClick() {
	Intent albumIntent = photoUtil.getIntent(IntentType.FROM_ALBUM);
	requestManager.turnOff();
	startActivityForResult(albumIntent, FROM_ALBUM);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	File imageFile = null;
	if (resultCode == RESULT_OK) {
	    switch(requestCode) {
	    case FROM_CAMERA:
		imageFile = photoUtil.getFileFromIntent(PhotoUtil.IntentType.FROM_CAMERA, data);
		if(albumView.pushImage(imageFile)) {
		    onChecked(TAB_ID_PICTURE, true);
		}
		break;
	    case FROM_ALBUM:
		imageFile = photoUtil.getFileFromIntent(PhotoUtil.IntentType.FROM_ALBUM, data);
		if (albumView.pushImage(imageFile)) {
		    onChecked(TAB_ID_PICTURE, true);
		}
	    }
	}
    }

    private void showPictureKeyboardAfterHide() {
	isShowPictureKeyboardAfterHide = true;
    }

    private void hidePictureKeyboardAfterHide() {
	isHidePictureKeyboardAfterHide = true;
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

    public interface KeyboardListener {
	public void onKeyboardStateChanged(boolean isShowing);
    }
}