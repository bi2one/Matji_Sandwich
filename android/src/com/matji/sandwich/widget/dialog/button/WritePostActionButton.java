package com.matji.sandwich.widget.dialog.button;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.R;
import com.matji.sandwich.WritePostActivity;
import com.matji.sandwich.data.Store;

public class WritePostActionButton extends ActionButton {
    private static final int STRING_REFERENCE = R.string.dialog_action_button_write_post;
    private Store store;
    private String tags;

    public WritePostActionButton(Context context) { super(context); }
    public WritePostActionButton(Context context, AttributeSet attrs) { super(context, attrs); }
    
    protected String setTitle() {
	return getContext().getString(STRING_REFERENCE);
    }

    public void setData(Store store, String tags) {
	this.store = store;
	this.tags = tags;
    }

    public void onButtonClick(View v) {
	Intent writePostIntent = new Intent(getContext(), WritePostActivity.class);
	if (store != null)
	    writePostIntent.putExtra(WritePostActivity.INTENT_STORE, (Parcelable)store);
	if (tags != null)
	    writePostIntent.putExtra(WritePostActivity.INTENT_STORE, tags);
	    
	getContext().startActivity(writePostIntent);
    }
}