package com.matji.sandwich.widget;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.util.AttributeSet;
import android.view.View;
import android.view.LayoutInflater;

import com.matji.sandwich.R;

public class SimpleSubmitLocationBar extends RelativeLayout implements View.OnClickListener {
    private Context context;
    private TextView addressView;
    private Button submitButton;
    private OnClickListener listener;
    
    public SimpleSubmitLocationBar(Context context, AttributeSet attrs) {
	super(context, attrs);
	this.context = context;
	LayoutInflater.from(context).inflate(R.layout.simple_submit_location_bar, this, true);
	addressView = (TextView)findViewById(R.id.simple_submit_location_bar_address);
	submitButton = (Button)findViewById(R.id.simple_submit_location_bar_submit);
	submitButton.setOnClickListener(this);
    }

    public void setSubmitText(int textId) {
	submitButton.setText(textId);
    }

    public void setSubmitText(String text) {
	submitButton.setText(text);
    }

    public void setAddress(String address) {
	addressView.setText(address);
    }

    public void setOnClickListener(OnClickListener listener) {
	this.listener = listener;
    }

    public void onClick(View view) {
	if (listener != null) {
	    listener.onSubmitClick();
	}
    }

    public interface OnClickListener {
	public void onSubmitClick();
    }
}