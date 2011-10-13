package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.R;

public class SimpleSubmitLocationBar extends RelativeLayout implements View.OnClickListener {
    private TextView addressView;
    private ImageButton locationButton;
    private OnClickListener listener;
    private RelativeLayout spinnerContainer;
    
    public SimpleSubmitLocationBar(Context context, AttributeSet attrs) {
	super(context, attrs);
	LayoutInflater.from(context).inflate(R.layout.simple_submit_location_bar, this, true);
	addressView = (TextView)findViewById(R.id.simple_submit_location_bar_address);
	locationButton = (ImageButton)findViewById(R.id.simple_submit_location_bar_location);
	spinnerContainer = (RelativeLayout)findViewById(R.id.simple_submit_location_bar_spinner);
	
	addressView.setOnClickListener(this);
	locationButton.setOnClickListener(this);
    }

    public ViewGroup getSpinnerContainer() {
	return spinnerContainer;
    }

    public void setAddress(String address) {
	addressView.setText(address);
    }

    public void setOnClickListener(OnClickListener listener) {
	this.listener = listener;
    }

    public void onClick(View view) {
	if (listener == null) {
	    return ;
	}
	
	int viewId = view.getId();
	
	if (viewId == addressView.getId()) {
	    listener.onSubmitClick();
	} else if (viewId == locationButton.getId()) {
	    listener.onLocationClick();
	}
    }

    public interface OnClickListener {
	public void onSubmitClick();
	public void onLocationClick();
    }
}