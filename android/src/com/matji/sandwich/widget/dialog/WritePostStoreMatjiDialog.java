package com.matji.sandwich.widget.dialog;

import android.content.Context;
import android.view.View;

import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.dialog.button.WritePostActionButton;
import com.matji.sandwich.widget.dialog.button.WriteStoreActionButton;
import com.matji.sandwich.widget.dialog.button.ActionButton;

public class WritePostStoreMatjiDialog extends MatjiDialog implements View.OnClickListener {
    private Context context;
    private WritePostActionButton writePostButton;
    private WriteStoreActionButton writeStoreButton;
    
    public WritePostStoreMatjiDialog(Context context) {
	super(context);
	this.context = context;
	writePostButton = new WritePostActionButton(context);
	writeStoreButton = new WriteStoreActionButton(context);

	writePostButton.setLastOnClickListener(this);
	writeStoreButton.setLastOnClickListener(this);
	
	addActionButton(writePostButton);
	addActionButton(writeStoreButton);
    }

    public void setPostData(Store store, String tags) {
	writePostButton.setData(store, tags);
    }

    public void onClick(View v) {
	cancel();
    }
}