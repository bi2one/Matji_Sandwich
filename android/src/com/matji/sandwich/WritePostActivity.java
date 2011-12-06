package com.matji.sandwich;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.ProgressDialogAsyncTask;
import com.matji.sandwich.http.request.WritePostHttpRequest;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.PhotoUtil;
import com.matji.sandwich.util.PhotoUtil.IntentType;
import com.matji.sandwich.widget.AlbumView;
import com.matji.sandwich.widget.GetPictureLayout;
import com.matji.sandwich.widget.PostEditText;
import com.matji.sandwich.widget.RelativeLayoutThatDetectsSoftKeyboard;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.title.CompletableTitle;

public class WritePostActivity extends BaseActivity implements CompletableTitle.Completable,
RelativeLayoutThatDetectsSoftKeyboard.Listener,
PostEditText.OnClickListener,
GetPictureLayout.OnClickListener,
Requestable,
SimpleAlertDialog.OnClickListener {
    public static final String FROM_MAIN = "WritePostActivity.from_main";
    public static final String INTENT_STORE = "WritePostActivity.store";
    public static final String INTENT_TAGS = "WritePostActivity.tags";
    public static final String INTENT_USER = "WritePostActivity.user";
    
    private static final int TAG_WRITE_POST = 0;
    private static final int INTENT_SELECT_STORE = 0;
    private static final int INTENT_SELECT_TAG = 1;
    private static final int INTENT_EXTERNAL_SERVICE = 4;
    private static final int INTENT_CAMERA = 2;
    private static final int INTENT_ALBUM = 3;
    //    private static final int BASIC_STORE_ID = -1;
    private Context context;
    private PhotoUtil photoUtil;
    private PostEditText postText;
    private RelativeLayoutThatDetectsSoftKeyboard mainView;
    private HttpRequestManager requestManager;
    //    private SessionMapUtil sessionMapUtil;
    private CompletableTitle titleBar;
    private RelativeLayout imageKeyboard;
    private AlbumView albumView;
    private GetPictureLayout pictureButtons;
    private int contentHeightWithoutKeyboard;
    private int contentHeightWithKeyboard;
    private int keyboardHeight;
    private LayoutParams keyboardLayoutParams;
    private boolean isShowImageKeyboardAfterHide;
    private boolean isShowNormalKeyboardAfterHide;
    private boolean isFlowIsCamera = false;
    private Store store;
    private String tags = "";
    private Boolean isTwitter = false;
    private Boolean isFacebook = false;
    private SimpleAlertDialog postEmptyDialog;
    private SimpleAlertDialog successDialog;
    private SimpleAlertDialog albumFullDialog;
    private Intent inputIntent;

    public int setMainViewId() {
        return R.id.activity_write_post;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);

        context = getApplicationContext();

        photoUtil = new PhotoUtil(this);
        requestManager = HttpRequestManager.getInstance();
        //	sessionMapUtil = new SessionMapUtil(context);
        mainView = (RelativeLayoutThatDetectsSoftKeyboard)getMainView();
        mainView.setListener(this);

        imageKeyboard = (RelativeLayout)findViewById(R.id.activity_write_post_image_keyboard);
        pictureButtons = (GetPictureLayout)findViewById(R.id.activity_write_post_image_btns);
        albumView = (AlbumView)findViewById(R.id.activity_write_post_albumview);
        postText = (PostEditText)findViewById(R.id.activity_write_post_text);
        titleBar = (CompletableTitle)findViewById(R.id.activity_write_post_title);
        titleBar.setTitle(R.string.write_post_activity_title);
        titleBar.setCompletable(this);
        postText.setOnClickListener(this);
        pictureButtons.setOnClickListener(this);
        postEmptyDialog = new SimpleAlertDialog(this, R.string.post_edit_text_empty);
        successDialog = new SimpleAlertDialog(this, R.string.write_post_success);
        successDialog.setCancelable(false);
        albumFullDialog = new SimpleAlertDialog(this, R.string.write_post_album_full);
        successDialog.setOnClickListener(this);
        postEmptyDialog.setOnClickListener(this);

        inputIntent = getIntent();
        store = (Store)inputIntent.getParcelableExtra(INTENT_STORE);
        tags = inputIntent.getStringExtra(INTENT_TAGS);

        checkStore(store);
        checkTags(tags);
    }

    protected void onResume() {
        super.onResume();
        requestManager.turnOn();
        if (isFlowIsCamera) {
            isFlowIsCamera = false;
        } else {
            showKeyboardPostDelay();
        }
    }

    private void showKeyboardPostDelay() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                postText.getEditText().requestFocus();
                KeyboardUtil.showKeyboard(WritePostActivity.this, postText.getEditText());
            }
        }, 100);
    }

    public void complete() {
        WritePostHttpRequest postRequest = new WritePostHttpRequest(context);
        String post = postText.getText();
        ArrayList<File> imageFiles = albumView.getFiles();

        if (!isValidPost(post)) {
            return ;
        }

        if (tags == null) {
            tags = "";
        }

        postRequest.actionNew(post, tags, store, imageFiles, isTwitter, isFacebook);
        // DialogAsyncTask requestTask = new DialogAsyncTask(this, this, postRequest, TAG_WRITE_POST);
        ProgressDialogAsyncTask requestTask = new ProgressDialogAsyncTask(this, this, postRequest, TAG_WRITE_POST);
        requestTask.execute();
        // requestManager.request(getMainView(), postRequest, TAG_WRITE_POST, this);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch(tag) {
        case TAG_WRITE_POST:
            successDialog.show();
            break;
        }
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(context);
    }

    private boolean isValidPost(String post) {
        if (post.trim().equals("")) {
            postEmptyDialog.show();
            return false;
        }
        return true;
    }

    public void onStoreClicked(View v) {
        Intent intent = new Intent(this, SelectStoreActivity.class);
        intent.putExtra(SelectStoreActivity.DATA_STORE, (Parcelable)store);
        startActivityForResult(intent, INTENT_SELECT_STORE);
    }

    public void onTagClicked(View v) {
        Intent intent = new Intent(this, SelectTagActivity.class);
        intent.putExtra(SelectTagActivity.DATA_TAGS, tags);
        startActivityForResult(intent, INTENT_SELECT_TAG);
    }

    public void onServiceClicked(View v) {
    	Intent intent = new Intent(this, ExternalServiceActivity.class);
    	intent.putExtra(ExternalServiceActivity.DATA_TWITTER, isTwitter);
    	intent.putExtra(ExternalServiceActivity.DATA_FACEBOOK, isFacebook);
    	startActivityForResult(intent, INTENT_EXTERNAL_SERVICE);
    }

    public void onPictureClicked(View v) {
        showImageKeyboardAfterHide();
    }

    public void onKeyboardClicked(View v) {
        showNormalKeyboardAfterHide();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return ;

        switch(requestCode) {
        case INTENT_SELECT_STORE:
            store = (Store)data.getParcelableExtra(SelectStoreActivity.DATA_STORE);
            checkStore(store);
            break;
        case INTENT_SELECT_TAG:
            tags = data.getStringExtra(SelectTagActivity.DATA_TAGS);
            checkTags(tags);
            break;
        case INTENT_EXTERNAL_SERVICE:
        	isTwitter = (Boolean)data.getBooleanExtra(ExternalServiceActivity.DATA_TWITTER, isTwitter);
        	isFacebook= (Boolean)data.getBooleanExtra(ExternalServiceActivity.DATA_FACEBOOK, isFacebook);
        	checkServices(isTwitter, isFacebook);
        	break;
        case INTENT_CAMERA:
            albumView.pushImage(photoUtil.getFileFromIntent(PhotoUtil.IntentType.FROM_CAMERA, data));
            break;
        case INTENT_ALBUM:
            albumView.pushImage(photoUtil.getFileFromIntent(PhotoUtil.IntentType.FROM_ALBUM, data));
            break;
        }
    }


	private void checkStore(Store store) {
        if (store != null) {
            postText.checkButton(PostEditText.ButtonIndex.STORE);
        } else {
            postText.unCheckButton(PostEditText.ButtonIndex.STORE);
        }
    }

    private void checkTags(String tags) {
        if (tags != null && !tags.trim().equals("")) {
            postText.checkButton(PostEditText.ButtonIndex.TAG);
        } else {
            postText.unCheckButton(PostEditText.ButtonIndex.TAG);
        }
    }

    private void checkServices(Boolean isT, Boolean isF) {
    	if (isT == true || isF == true) {
    		postText.checkButton(PostEditText.ButtonIndex.SERVICE);
    	} else {
    		postText.unCheckButton(PostEditText.ButtonIndex.SERVICE);
    	}
		
	}

    public void onSoftKeyboardShown(boolean isShowing) {
        if (isShowing) {
            contentHeightWithoutKeyboard = mainView.getHeight();
            imageKeyboard.setVisibility(View.GONE);
            postText.toggleTo(PostEditText.ToggleIndex.IMAGE);
        } else {
            contentHeightWithKeyboard = mainView.getHeight();
        }

        if (keyboardHeight <= 0 && contentHeightWithoutKeyboard > 0 && contentHeightWithKeyboard > 0) {
            keyboardHeight = contentHeightWithoutKeyboard - contentHeightWithKeyboard;
            keyboardLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, keyboardHeight);
            keyboardLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }

        if (!isShowing && isShowImageKeyboardAfterHide) {
            initKeyboardConstant();
            showImageKeyboard();
        }

        if (isShowing && isShowNormalKeyboardAfterHide) {
            initKeyboardConstant();
            showNormalKeyboard();
        }
    }

    private void initKeyboardConstant() {
        isShowImageKeyboardAfterHide = false;
        isShowNormalKeyboardAfterHide = false;
    }

    public void showImageKeyboardAfterHide() {
        KeyboardUtil.hideKeyboard(this);
        isShowImageKeyboardAfterHide = true;
    }

    public void showNormalKeyboardAfterHide() {
        KeyboardUtil.showKeyboard(this, postText.getEditText());
        isShowNormalKeyboardAfterHide = true;
    }

    public void showImageKeyboard() {
        imageKeyboard.setVisibility(View.VISIBLE);

        if (keyboardLayoutParams != null)
            imageKeyboard.setLayoutParams(keyboardLayoutParams);
    }

    public void showNormalKeyboard() {
        imageKeyboard.setVisibility(View.GONE);
    }

    public void onCameraClick() {
        if (albumView.isFull()) {
            albumFullDialog.show();
            return ;
        }

        isFlowIsCamera = true;
        Intent cameraIntent = photoUtil.getIntent(IntentType.FROM_CAMERA);
        requestManager.turnOff();
        startActivityForResult(cameraIntent, INTENT_CAMERA);
    }

    public void onAlbumClick() {
        if (albumView.isFull()) {
            albumFullDialog.show();
            return ;
        }

        isFlowIsCamera = true;
        Intent albumIntent = photoUtil.getIntent(IntentType.FROM_ALBUM);
        requestManager.turnOff();
        startActivityForResult(albumIntent, INTENT_ALBUM);
    }

    public void onConfirmClick(SimpleDialog dialog) {
        if (dialog == successDialog) {
            if (getIntent().getBooleanExtra(FROM_MAIN, false)) {
                Intent intent = new Intent(context, MainTabActivity.class);
                intent.putExtra(MainTabActivity.IF_INDEX, MainTabActivity.IV_INDEX_POST);
                intent.putExtra(MainTabActivity.IF_SUB_INDEX, 0);
                startActivity(intent);
            } else {
                setResult(RESULT_OK);
                finish();
            }
        } else if (dialog == postEmptyDialog) {
            showKeyboardPostDelay();
        }
    }
    
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
            // 다시 setOrientation()을 호출한다.
            WindowManager wm = getWindowManager();
            if (wm == null) return;
            setRequestedOrientation(wm.getDefaultDisplay().getOrientation());
    }
}