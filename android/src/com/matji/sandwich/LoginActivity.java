package com.matji.sandwich;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.LoginView;
import com.matji.sandwich.widget.title.HomeTitle;

public class LoginActivity extends BaseActivity implements Loginable {

    private HomeTitle title;
    private LoginView loginView;

    public int setMainViewId() {
        return R.id.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        title = ((HomeTitle) findViewById(R.id.Titlebar));
        title.setTitle(R.string.default_string_login);
        loginView = (LoginView) findViewById(R.id.login_view);
    }
    
    public void loginButtonClicked(View v) {
    	loginView.login(this);
    }

    /* Loginable Interface methods */
    public void loginCompleted() {
	Log.d("=====", "abcd");
        this.setResult(RESULT_OK);
        finish();
    }

    public void loginFailed() {
        // show toast -> id, pw 확인해라
    }
}