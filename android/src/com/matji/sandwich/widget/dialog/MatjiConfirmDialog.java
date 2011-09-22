package com.matji.sandwich.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

public class MatjiConfirmDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView contents;
    private Button okButton;
    private Button cancelButton;
    private OnClickListener listener;

    public MatjiConfirmDialog(Context context, String message) {
	super(context, R.style.Theme_Dialog);
	init(context, message);
    }

    public MatjiConfirmDialog(Context context, int msgId) {
	super(context, R.style.Theme_Dialog);
	init(context, MatjiConstants.string(msgId));
    }

    private void init(Context context, String message) {
	this.context = context;
	setContentView(R.layout.dialog_matji_confirm);

	listener = new OnClickListener() {
		public void onConfirmClick(MatjiConfirmDialog dialog) { }
		public void onCancelClick(MatjiConfirmDialog dialog) { }
	    };

	contents = (TextView)findViewById(R.id.dialog_matji_confirm_contents);
	okButton = (Button)findViewById(R.id.dialog_matji_confirm_ok);
	cancelButton = (Button)findViewById(R.id.dialog_matji_confirm_cancel);

	contents.setText(message);
	okButton.setOnClickListener(this);
	cancelButton.setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener listener) {
	this.listener = listener;
    }
    
    public void onClick(View v) {
	int vId = v.getId();
	if (vId == okButton.getId()) {
	    cancel();
	    listener.onConfirmClick(this);
	} else if (vId == cancelButton.getId()) {
	    cancel();
	    listener.onCancelClick(this);
	}
    }
    
    public interface OnClickListener {
	public void onConfirmClick(MatjiConfirmDialog dialog);
	public void onCancelClick(MatjiConfirmDialog dialog);
    }
}