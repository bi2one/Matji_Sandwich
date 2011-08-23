package com.matji.sandwich.widget.dialog;

import android.content.Context;
import android.view.View;

import com.matji.sandwich.widget.dialog.button.WritePostActionButton;
import com.matji.sandwich.widget.dialog.button.WriteStoreActionButton;
import com.matji.sandwich.widget.dialog.button.ActionButton;

public class WritePostStoreMatjiDialog extends MatjiDialog implements View.OnClickListener {
    private Context context;
    
    public WritePostStoreMatjiDialog(Context context) {
	super(context);
	this.context = context;
	ActionButton writePostButton = new WritePostActionButton(context);
	ActionButton writeStoreButton = new WriteStoreActionButton(context);

	writePostButton.setLastOnClickListener(this);
	writeStoreButton.setLastOnClickListener(this);
	
	addActionButton(writePostButton);
	addActionButton(writeStoreButton);
    }

    public void onClick(View v) {
	cancel();
    }
}