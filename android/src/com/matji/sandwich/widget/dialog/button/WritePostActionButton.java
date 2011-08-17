package com.matji.sandwich.widget.dialog.button;

import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.R;

public class WritePostActionButton extends ActionButton {
    private static final int STRING_REFERENCE = R.string.dialog_action_button_write_post;

    public WritePostActionButton(Context context) { super(context); }
    public WritePostActionButton(Context context, AttributeSet attrs) { super(context, attrs); }
    
    protected String setTitle() {
	return getContext().getString(STRING_REFERENCE);
    }

    public void onClick(View v) {
	Log.d("=====", "write post action button clicked!!");
    }
}