package com.matji.sandwich.widget.dialog.button;

import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.dialog.MatjiDialog;
import com.matji.sandwich.WriteStoreActivity;

public class WriteStoreActionButton extends ActionButton {
    private static final int STRING_REFERENCE = R.string.dialog_action_button_write_store;

    public WriteStoreActionButton(Context context) { super(context); }
    public WriteStoreActionButton(Context context, AttributeSet attrs) { super(context, attrs); }

    protected String setTitle() {
	return getContext().getString(STRING_REFERENCE);
    }

    public void onButtonClick(View v) {
	Intent writeStoreIntent = new Intent(getContext(), WriteStoreActivity.class);
	getContext().startActivity(writeStoreIntent);
    }
}