package com.matji.sandwich;

import android.os.Bundle;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.PostEditText;
import com.matji.sandwich.widget.GetPictureLayout;
import com.matji.sandwich.widget.RelativeLayoutThatDetectsSoftKeyboard;
import com.matji.sandwich.widget.title.CompletableTitle;

public class WritePostActivity extends BaseActivity implements CompletableTitle.Completable,
							       RelativeLayoutThatDetectsSoftKeyboard.Listener,
							       PostEditText.OnClickListener {
    private static final int INTENT_SELECT_STORE = 0;
    private static final int BASIC_STORE_ID = -1;
    private Context context;
    private PostEditText postText;
    private RelativeLayoutThatDetectsSoftKeyboard mainView;
    private CompletableTitle titleBar;
    private GetPictureLayout imageKeyboard;
    private int contentHeightWithoutKeyboard;
    private int contentHeightWithKeyboard;
    private int keyboardHeight;
    private LayoutParams keyboardLayoutParams;
    private boolean isShowImageKeyboardAfterHide;
    private boolean isShowNormalKeyboardAfterHide;
    // private boolean isShowNormalKeyboardRepeat;
    private Store store;
    private String tags;
    

    public int setMainViewId() {
	return R.id.activity_write_post;
    }
    
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_write_post);
	
	context = getApplicationContext();
	mainView = (RelativeLayoutThatDetectsSoftKeyboard)getMainView();
	mainView.setListener(this);

	imageKeyboard = (GetPictureLayout)findViewById(R.id.activity_write_post_image_keyboard);
	postText = (PostEditText)findViewById(R.id.activity_write_post_text);
	titleBar = (CompletableTitle)findViewById(R.id.activity_write_post_title);
	titleBar.setTitle(R.string.write_post_activity_title);
	titleBar.setCompletable(this);
	postText.setOnClickListener(this);
    }

    protected void onResume() {
    	super.onResume();
	// showNormalKeyboardRepeat();
	Handler handler = new Handler();
	handler.postDelayed(new Runnable() {
		public void run() {
		    postText.getEditText().requestFocus();
		    KeyboardUtil.showKeyboard(WritePostActivity.this, postText.getEditText());
		}
	    }, 100);
    }

    // protected void onPause() {
    // 	super.onPause();
	// sessionUtil.setPost(editText.getText().toString());
	// parentActivity.hideKeyboard();
    // }

    public void complete() {
	Log.d("=====", "complete");
    }

    public void onStoreClicked(View v) {
	Intent intent = new Intent(this, SelectStoreActivity.class);
	startActivityForResult(intent, INTENT_SELECT_STORE);
	// Log.d("=====", "store click");
    }

    public void onTagClicked(View v) {
	Log.d("=====", "tag click");
    }
    
    public void onServiceClicked(View v) {
	Log.d("=====", "service click");
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

	if (requestCode == INTENT_SELECT_STORE) {
	    store = (Store)data.getParcelableExtra(SelectStoreActivity.DATA_STORE);
	    if (store != null) {
		Log.d("=====", "storeId: " + store.getId());
		// check button! -- TODO
	    }
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

	// if (!isShowing && isShowNormalKeyboardRepeat) {
	//     initKeyboardConstant();
	//     showNormalKeyboard();
	// }

	if (isShowing && isShowNormalKeyboardAfterHide) {
	    initKeyboardConstant();
	    showNormalKeyboard();
	}
    }

    private void initKeyboardConstant() {
	isShowImageKeyboardAfterHide = false;
	// isShowNormalKeyboardRepeat = false;
	isShowNormalKeyboardAfterHide = false;
    }

    public void showImageKeyboardAfterHide() {
	KeyboardUtil.hideKeyboard(this);
	isShowImageKeyboardAfterHide = true;
    }

    // public void showNormalKeyboardRepeat() {
    // 	isShowNormalKeyboardRepeat = true;
    // }

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
}