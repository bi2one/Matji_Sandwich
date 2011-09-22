package com.matji.sandwich.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

public class SimpleAlertDialog implements SimpleDialog, DialogInterface.OnClickListener {
    Context context;
    AlertDialog dialog;
    OnClickListener listener;
    
    public SimpleAlertDialog(Context context, int msgId) {
	this(context, MatjiConstants.string(msgId));
    }

    public SimpleAlertDialog(Context context, String message) {
	this.context = context;
	listener = new OnClickListener() {
		public void onConfirmClick(SimpleDialog dialog) { }
	    };
	
	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	builder.setMessage(message)
	    .setCancelable(true)
	    .setNeutralButton(R.string.default_string_confirm, this);
	dialog = builder.create();
    }

    public void setOnClickListener(OnClickListener listener) {
	this.listener = listener;
    }

    public void onClick(DialogInterface dialog, int id) {
	cancel();
	listener.onConfirmClick(this);
    }

    public void show() {
	dialog.show();
    }

    public void cancel() {
	dialog.cancel();
    }

    public interface OnClickListener {
	public void onConfirmClick(SimpleDialog dialog);
    }
}