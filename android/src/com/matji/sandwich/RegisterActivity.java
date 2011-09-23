package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.title.CompletableTitle;

public class RegisterActivity extends BaseActivity {
    private CompletableTitle title;

    public int setMainViewId() {
        return R.id.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();

        setContentView(R.layout.activity_register);

        title = (CompletableTitle) findViewById(R.id.Titlebar);
        title.setTitle(R.string.default_string_register);
        
        
    }
}