package com.matji.sandwich.widget.dialog;

import android.content.Context;

import com.matji.sandwich.widget.dialog.button.WritePostActionButton;
import com.matji.sandwich.widget.dialog.button.WriteStoreActionButton;

public class WritePostStoreMatjiDialog extends MatjiDialog {
    private Context context;
    
    public WritePostStoreMatjiDialog(Context context) {
	super(context);
	this.context = context;
	addActionButton(new WritePostActionButton(context));
	addActionButton(new WriteStoreActionButton(context));
    }
}