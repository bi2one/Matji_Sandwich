package com.matji.sandwich;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.RecentChangedLocationView;
import com.matji.sandwich.widget.SearchResultView;
import com.matji.sandwich.widget.RelativeLayoutThatDetectsSoftKeyboard;
import com.matji.sandwich.widget.title.TitleContainer;
import com.matji.sandwich.util.KeyboardUtil;

import java.util.ArrayList;

public class ChangeLocationActivity extends BaseActivity implements TextView.OnEditorActionListener,
								    RelativeLayoutThatDetectsSoftKeyboard.Listener {
    public static final String INTENT_KEY_LATITUDE = "ChangeLocationActivity.intent_key_latitude";
    public static final String INTENT_KEY_LONGITUDE = "ChangeLocationActivity.intent_key_longitude";
    public static final String INTENT_KEY_LOCATION_NAME = "ChangeLocationActivity.intent_key_location_name";
    public static final int BASIC_SEARCH_LOC_LAT = 0;
    public static final int BASIC_SEARCH_LOC_LNG = 0;
    private Context context;
    private TitleContainer titleBar;
    private EditText locationInput;
    private ImageView hideHolder;
    private SearchResultView resultView;
    private RecentChangedLocationView locationView;
    private RelativeLayoutThatDetectsSoftKeyboard mainView;
    private RelativeLayout contentsWrapper;

    public int setMainViewId() {
	return R.id.activity_change_location;
    }
    
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_change_location);
	context = getApplicationContext();

	mainView = (RelativeLayoutThatDetectsSoftKeyboard)getMainView();
	mainView.setListener(this);

	contentsWrapper = (RelativeLayout)findViewById(R.id.activity_change_location_contents);

	titleBar = (TitleContainer)findViewById(R.id.activity_change_location_title);
	titleBar.setTitle(R.string.change_location_activity_title);

	hideHolder = (ImageView)findViewById(R.id.activity_change_location_holder);
	hideHolder.setOnTouchListener(new View.OnTouchListener() {
		public boolean onTouch(View view, MotionEvent motionEvent) {
		    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			KeyboardUtil.hideKeyboard(ChangeLocationActivity.this);
		    }
		    return true;
		}
	    });
	
	locationInput = (EditText)findViewById(R.id.activity_change_location_input);
	locationInput.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
	locationInput.setOnEditorActionListener(this);
	
	locationView = (RecentChangedLocationView)findViewById(R.id.activity_change_location_recent_view);
	locationView.init(this);

	resultView = (SearchResultView)findViewById(R.id.activity_change_location_result);
	resultView.init(this);
    }

    public void searchLocation() {
	resultView.search(locationInput.getText().toString());
    }

    public boolean onEditorAction(TextView v, int action, KeyEvent e) {
	if ((action == EditorInfo.IME_ACTION_DONE) ||
	    (e != null && e.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
	    searchLocation();
	}
	return false;
    }

    public void onCancelClicked(View v) {
	locationInput.setText("");
	KeyboardUtil.hideKeyboard(this);
    }

    public void onSoftKeyboardShown(boolean isShowing) {
	if (isShowing) {
	    hideHolder.setVisibility(View.VISIBLE);
	} else {
	    hideHolder.setVisibility(View.GONE);
	}
    }
}