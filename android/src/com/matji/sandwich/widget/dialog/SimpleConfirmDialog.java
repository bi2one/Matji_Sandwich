package com.matji.sandwich.widget.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

public class SimpleConfirmDialog implements SimpleDialog, DialogInterface.OnClickListener {
    Context context;
    AlertDialog dialog;
    OnClickListener listener;

    public SimpleConfirmDialog(Context context, int msgId) {
        this(context, MatjiConstants.string(msgId));
    }

    public SimpleConfirmDialog(Context context, String message) {
        this.context = context;
        listener = new OnClickListener() {
            public void onConfirmClick(SimpleDialog dialog) { }
            public void onCancelClick(SimpleDialog dialog) { }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
        .setCancelable(true)
        .setPositiveButton(R.string.default_string_confirm, this)
        .setNegativeButton(R.string.default_string_cancel, this);
        dialog = builder.create();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void onClick(DialogInterface dialog, int id) {
        cancel();
        switch(id) {
        case DialogInterface.BUTTON_POSITIVE:
            listener.onConfirmClick(this);
            break;
        case DialogInterface.BUTTON_NEGATIVE:
            listener.onCancelClick(this);
            break;
        }
    }

    public void show() {
        dialog.show();
    }

    public void cancel() {
        dialog.cancel();
    }

    public interface OnClickListener {
        public void onConfirmClick(SimpleDialog dialog);
        public void onCancelClick(SimpleDialog dialog);
    }
}