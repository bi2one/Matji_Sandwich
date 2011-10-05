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

    public enum ButtonIndex {
	STORE, TAG, SERVICE
    }

    private static final boolean isToggleable = false;
    private Context context;
    private EditText postText;
    private ImageButton storeButton;
    private ImageButton tagButton;
    private ImageButton serviceButton;
    private LinearLayout pictureButton;
    private LinearLayout keyboardButton;
    private OnClickListener listener;
    private ToggleIndex currentToggleIndex;

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
	serviceButton.setVisibility(GONE); // should be removed
	pictureButton.setOnClickListener(this);
	keyboardButton.setOnClickListener(this);
	currentToggleIndex = ToggleIndex.IMAGE;
    }

    public void checkButton(ButtonIndex index) {
	switch(index) {
	case STORE:
	    storeButton.setBackgroundResource(R.drawable.btn_white_touch);
	    storeButton.setImageResource(R.drawable.icon_story_restaurant_touch);
	    break;
	case TAG:
	    tagButton.setBackgroundResource(R.drawable.btn_white_touch);
	    tagButton.setImageResource(R.drawable.icon_story_tag_touch);
	    break;
	case SERVICE:
	    serviceButton.setBackgroundResource(R.drawable.btn_white_touch);
	    serviceButton.setImageResource(R.drawable.icon_story_link_touch);
	    break;
	}
    }

    public void unCheckButton(ButtonIndex index) {
	switch(index) {
	case STORE:
	    storeButton.setBackgroundResource(R.drawable.btn_white);
	    storeButton.setImageResource(R.drawable.icon_story_restaurant);
	    break;
	case TAG:
	    tagButton.setBackgroundResource(R.drawable.btn_white);
	    tagButton.setImageResource(R.drawable.icon_story_tag);
	    break;
	case SERVICE:
	    serviceButton.setBackgroundResource(R.drawable.btn_white);
	    serviceButton.setImageResource(R.drawable.icon_story_link);
	    break;
	}
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

    public String getText() {
	return postText.getText().toString();
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
	    if (isToggleable) {
		showKeyboardButton();
		listener.onPictureClicked(this);
	    } else if (currentToggleIndex == ToggleIndex.IMAGE) {
		showKeyboardButton();
		listener.onPictureClicked(this);
	    } else if (currentToggleIndex == ToggleIndex.KEYBOARD) {
		showPictureButton();
		listener.onKeyboardClicked(this);
	    }
	} else if (vId == keyboardButton.getId()) {
	    showPictureButton();
	    listener.onKeyboardClicked(this);
	}
    }

    public void showKeyboardButton() {
	if (isToggleable) {
	    pictureButton.setVisibility(View.GONE);
	    keyboardButton.setVisibility(View.VISIBLE);
	}
	currentToggleIndex = ToggleIndex.KEYBOARD;
    }

    public void showPictureButton() {
	keyboardButton.setVisibility(View.GONE);
	pictureButton.setVisibility(View.VISIBLE);
	currentToggleIndex = ToggleIndex.IMAGE;
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
