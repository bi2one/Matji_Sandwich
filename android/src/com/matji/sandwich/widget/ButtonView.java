package com.matji.sandwich.widget;

import android.widget.LinearLayout;
import android.widget.Button;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.util.Log;

public abstract class ButtonView extends LinearLayout {
    protected abstract int setButtonId();
    protected abstract int setRootViewId();

    private LayoutInflater inflater;
    private Button button;
    private int buttonId;
    private int rootViewId;

    public ButtonView(Context context) {
	super(context);
	init(context);
    }

    public ButtonView(Context context, AttributeSet attrs) {
	super(context, attrs);
	init(context);
    }

    public void setText(String text) {
	button.setText(text);
    }

    public void setTag(Object obj) {
	button.setTag(obj);
    }

    public Object getTag() {
	return button.getTag();
    }

    public Button getButton() {
	return button;
    }

    public void setOnClickListener(View.OnClickListener listener) {
	button.setOnClickListener(listener);
    }

    private void init(Context context) {
	rootViewId = setRootViewId();
	buttonId = setButtonId();

	inflater = LayoutInflater.from(context);
	inflater.inflate(rootViewId, this, true);
	button = (Button)findViewById(buttonId);
    }
}