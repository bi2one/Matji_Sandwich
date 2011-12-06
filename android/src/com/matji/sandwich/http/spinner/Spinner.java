package com.matji.sandwich.http.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Spinner extends RelativeLayout implements Spinnable {
	protected Context context;
	private LayoutParams layoutParams;
	private ViewGroup layout;
	private SpinListener listener;

	public Spinner(Context context, ViewGroup layout, int layoutReference) {
		super(context);
		this.context = context;
		LayoutInflater.from(context).inflate(layoutReference, this, true);
		this.layout = layout;

		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
	}

	public void setSpinListener(SpinListener listener) {
		this.listener = listener;
	}

	public void start() {
		if (listener != null) {
			listener.onStart(this);
		}
		layout.removeView(this);

		if (layout != null)
			layout.addView(this, layoutParams);
		setVisibility(View.VISIBLE);

		// bringToFront();
		// requestFocus();
		// invalidate();
	}

	public void stop() {
		if (listener != null) {
			listener.onStop(this);
		}
		// setVisibility(View.GONE);
		if (layout != null) {
			layout.removeView(this);
		}
	}
}