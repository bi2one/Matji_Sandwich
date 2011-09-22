package com.matji.sandwich.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.net.Uri;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.dialog.SimpleConfirmDialog;

public class PhoneCallUtil implements SimpleConfirmDialog.OnClickListener {
    Context context;
    SimpleConfirmDialog confirmDialog;
    String number;
    
    public PhoneCallUtil(Context context) {
    	this.context = context;
    	confirmDialog = new SimpleConfirmDialog(context, R.string.phone_call_confirm);
	confirmDialog.setOnClickListener(this);
    }

    public void call(String number) {
	this.number = number;
	confirmDialog.show();
    }

    public void onConfirmClick(SimpleDialog dialog) {
	Intent intent = new Intent(Intent.ACTION_CALL);
	intent.setData(Uri.parse("tel:" + number));
	context.startActivity(intent);
    }

    public void onCancelClick(SimpleDialog dialog) { }
}