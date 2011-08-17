package com.matji.sandwich.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.dialog.button.ActionButton;

public class MatjiDialog extends Dialog implements OnClickListener {
    private static final int ACTION_BUTTON_BOTTOM_MARGIN = R.dimen.dialog_button_margin_bottom;
    private Context context;
    private Button cancelButton;
    private LinearLayout actionButtonList;
    private LayoutParams marginParam;
    
    public MatjiDialog(Context context) {
	super(context, R.style.Theme_Dialog);
	this.context = context;
	setContentView(R.layout.dialog_matji);
	setCancelable(false);

	marginParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	marginParam.setMargins(0, 0, 0, (int)context.getResources().getDimension(ACTION_BUTTON_BOTTOM_MARGIN));
	
	cancelButton = (Button)findViewById(R.id.dialog_matji_cancel);
	actionButtonList = (LinearLayout)findViewById(R.id.dialog_matji_action_buttons);

	cancelButton.setOnClickListener(this);
    }

    public void onClick(View v) {
	if (v.getId() == cancelButton.getId()) {
	    cancel();
	}
    }

    public void addActionButton(ActionButton button) {
    	actionButtonList.addView(button, marginParam);
    }
}