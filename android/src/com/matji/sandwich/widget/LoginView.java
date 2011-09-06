package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.matji.sandwich.R;

public class LoginView extends RelativeLayout {
	
    public LoginView(Context context) {
        super(context);
        init();
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.login, this);
    }
    
    public void initialize() {
        ((EditText) findViewById(R.id.username)).setText("");
        ((EditText) findViewById(R.id.password)).setText("");
    }
}
