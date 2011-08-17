package com.matji.sandwich.widget.dialog.button;

import android.util.AttributeSet;
import android.util.Log;
import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.matji.sandwich.R;

public abstract class ActionButton extends RelativeLayout implements OnClickListener {
    private static final int LAYOUT_REFERENCE = R.layout.dialog_action_button;
    private Context context;
    private TextView title;

    protected abstract String setTitle();
    
    public ActionButton(Context context) {
	super(context);
	init(context);
    }
    
    public ActionButton(Context context, AttributeSet attrs) {
	super(context, attrs);
	init(context);
    }

    private void init(Context context) {
	this.context = context;
	LayoutInflater.from(context).inflate(LAYOUT_REFERENCE, this, true);
	setOnClickListener(this);
	title = (TextView)findViewById(R.id.dialog_action_button_title);
	title.setText(setTitle());
	Log.d("=====", setTitle());
    }

    public void onClick(View v) { }
}