package com.matji.sandwich.widget;

import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.graphics.Rect;

import com.matji.sandwich.R;

public class PostEditText extends RelativeLayout implements View.OnClickListener {
    public enum ToggleIndex {
	IMAGE, KEYBOARD
    }
    
    private Context context;
    private EditText postText;
    private ImageButton storeButton;
    private ImageButton tagButton;
    private ImageButton serviceButton;
    private LinearLayout pictureButton;
    private LinearLayout keyboardButton;
    private OnClickListener listener;

    public PostEditText(Context context, AttributeSet attrs) {
	super(context, attrs);
	
	this.context = context;
	LayoutInflater.from(context).inflate(R.layout.post_edit_text, this, true);
	listener = new NullListener();

	postText = (EditText)findViewById(R.id.post_edit_text_post);
	storeButton = (ImageButton)findViewById(R.id.post_edit_text_store_button);
	tagButton = (ImageButton)findViewById(R.id.post_edit_text_tag_button);
	serviceButton = (ImageButton)findViewById(R.id.post_edit_text_service_button);
	pictureButton = (LinearLayout)findViewById(R.id.post_edit_text_picture_button);
	keyboardButton = (LinearLayout)findViewById(R.id.post_edit_text_keyboard_button);
	
	storeButton.setOnClickListener(this);
	tagButton.setOnClickListener(this);
	serviceButton.setOnClickListener(this);
	pictureButton.setOnClickListener(this);
	keyboardButton.setOnClickListener(this);
    }

    public void toggleTo(ToggleIndex index) {
	switch(index) {
	case IMAGE:
	    showPictureButton();
	    break;
	case KEYBOARD:
	    showKeyboardButton();
	    break;
	}
    }

    public EditText getEditText() {
	return postText;
    }

    public void setOnClickListener(OnClickListener listener) {
	this.listener = listener;
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
	if (gainFocus) {
	    postText.requestFocus();
	}
    }

    public void onClick(View v) {
	int vId = v.getId();
	if (vId == storeButton.getId()) {
	    listener.onStoreClicked(this);
	} else if (vId == tagButton.getId()) {
	    listener.onTagClicked(this);
	} else if (vId == serviceButton.getId()) {
	    listener.onServiceClicked(this);
	} else if (vId == pictureButton.getId()) {
	    showKeyboardButton();
	    listener.onPictureClicked(this);
	} else if (vId == keyboardButton.getId()) {
	    showPictureButton();
	    listener.onKeyboardClicked(this);
	}
    }

    public void showKeyboardButton() {
	pictureButton.setVisibility(View.GONE);
	keyboardButton.setVisibility(View.VISIBLE);
    }

    public void showPictureButton() {
	keyboardButton.setVisibility(View.GONE);
	pictureButton.setVisibility(View.VISIBLE);
    }

    public interface OnClickListener {
	public void onStoreClicked(View v);
	public void onTagClicked(View v);
	public void onServiceClicked(View v);
	public void onPictureClicked(View v);
	public void onKeyboardClicked(View v);
    }

    private class NullListener implements OnClickListener {
	public void onStoreClicked(View v) { }
	public void onTagClicked(View v) { }
	public void onServiceClicked(View v) { }
	public void onPictureClicked(View v) { }
	public void onKeyboardClicked(View v) { }
    }
}
