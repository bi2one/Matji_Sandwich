package com.matji.sandwich.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

public class SimplePopupDialog implements SimpleDialog, DialogInterface.OnClickListener {
    AlertDialog dialog;
    OnClickListener listener;

    public SimplePopupDialog(Context context, int msgId) {
        this(context, MatjiConstants.string(msgId));
    }

    public SimplePopupDialog(Context context, String message) {
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
	if (listener != null) {
	    listener.onConfirmClick(this);
	}
        cancel();
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