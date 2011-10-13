package com.matji.sandwich.widget.dialog.button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.R;

public abstract class ActionButton extends RelativeLayout implements OnClickListener {
    private static final int LAYOUT_REFERENCE = R.layout.dialog_action_button;
    private TextView title;
    private OnClickListener listener;

    protected abstract String setTitle();
    public abstract void onButtonClick(View v);
    
    public ActionButton(Context context) {
        super(context);
        init(context);
    }

    public ActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setLastOnClickListener(OnClickListener listener) {
	this.listener = listener;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(LAYOUT_REFERENCE, this, true);
        setOnClickListener(this);
        title = (TextView)findViewById(R.id.dialog_action_button_title);
        title.setText(setTitle());
    }

    public void onClick(View v) {
	onButtonClick(v);
	listener.onClick(v);
    }
}