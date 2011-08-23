package com.matji.sandwich.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;

public class MatjiAlertDialog extends Dialog implements OnClickListener {
    private Context context;
    private TextView contents;
    private Button confirmButton;
    private OnConfirmHook confirmHook;

    public MatjiAlertDialog(Context context, String message) {
	super(context, R.style.Theme_Dialog);
	init(context, message);
    }

    public MatjiAlertDialog(Context context, int msgId) {
	super(context, R.style.Theme_Dialog);
	init(context, MatjiConstants.string(msgId));
    }

    public void setOnConfirmHook(OnConfirmHook confirmHook) {
	this.confirmHook = confirmHook;
    }

    private void init(Context context, String message) {
	this.context = context;
	setContentView(R.layout.dialog_matji_alert);
	setCancelable(false);

	confirmButton = (Button)findViewById(R.id.dialog_matji_alert_confirm);
	contents = (TextView)findViewById(R.id.dialog_matji_alert_contents);
	contents.setText(message);

	confirmButton.setFocusable(false);
	confirmButton.setOnClickListener(this);
    }

    public void onClick(View v) {
	if (v.getId() == confirmButton.getId()) {
	    cancel();
	    if (confirmHook != null) {
		confirmHook.onConfirm(this);
	    }
	}
    }

    public interface OnConfirmHook {
	public void onConfirm(MatjiAlertDialog dialog);
    }
}