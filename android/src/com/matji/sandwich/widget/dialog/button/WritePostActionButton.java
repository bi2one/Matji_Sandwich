package com.matji.sandwich.widget.dialog.button;

import android.app.Activity;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.WritePostActivity;

public class WritePostActionButton extends ActionButton {
    private static final int STRING_REFERENCE = R.string.dialog_action_button_write_post;
    private Activity activity;

    public WritePostActionButton(Context context) { super(context); }
    public WritePostActionButton(Context context, AttributeSet attrs) { super(context, attrs); }
    
    protected String setTitle() {
	return getContext().getString(STRING_REFERENCE);
    }

    public void onButtonClick(View v) {
	Intent writePostIntent = new Intent(getContext(), WritePostActivity.class);
	if (activity != null) {
	    activity.startActivityForResult(writePostIntent, BaseActivity.WRITE_POST_ACTIVITY);
	} else {
	    getContext().startActivity(writePostIntent);
	}
    }
}