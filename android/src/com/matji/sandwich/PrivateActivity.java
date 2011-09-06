package com.matji.sandwich;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.request.MeHttpRequest;
import com.matji.sandwich.http.request.MeHttpRequest.Service;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.Session.LoginAsyncTask;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.widget.LoginView;
import com.matji.sandwich.widget.UserProfileView;

public class PrivateActivity extends BaseActivity implements Loginable {
    
    private int currentMainView;
    
    private UserProfileView userProfileView;
    private LoginView loginView;

    public int setMainViewId() {
        return currentMainView;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        setContentView(R.layout.activity_private);
        userProfileView = (UserProfileView) findViewById(R.id.user_profile_view);
        loginView = (LoginView) findViewById(R.id.login_view);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    final public void refresh() {
        if (Session.getInstance(this).isLogin()) {
            showUserProfileView();
        } else {
            showLoginView();
        }
    }

    public void showUserProfileView() {
        userProfileView.setVisibility(View.VISIBLE);
        userProfileView.setUser(Session.getInstance(this).getCurrentUser());
        loginView.setVisibility(View.GONE);
        currentMainView = userProfileView.getId();
    }
    
    public void showLoginView() {
        loginView.initialize();
        userProfileView.setVisibility(View.GONE);
        loginView.setVisibility(View.VISIBLE);
        currentMainView = loginView.getId();
    }
    
    public void loginButtonClicked(View v) {
        EditText usernameField = (EditText) findViewById(R.id.username);
        EditText passwordField = (EditText) findViewById(R.id.password);

        new LoginAsyncTask(getParent(), this, usernameField.getText().toString(), passwordField.getText().toString()).execute(new Object());
    }

    public void cancelButtonClicked(View v) {
//        finish();
    }

    /* Loginable Interface methods */
    @Override
    public void loginCompleted() {
        this.setResult(RESULT_OK);
        KeyboardUtil.hideKeyboard(this);
        showUserProfileView();
    }

    @Override
    public void loginFailed() {
        // show toast -> id, pw 확인해라
    }

    public void twitterLoginClicked(View v){
        MeHttpRequest request = new MeHttpRequest(this);
        request.authorizeViaExternalService(this, Service.TWITTER);
    }
    
    public void facebookLoginClicked(View v){
        MeHttpRequest request = new MeHttpRequest(this);
        request.authorizeViaExternalService(this, Service.FACEBOOK);
        
    }    
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == BaseActivity.REQUEST_EXTERNAL_SERVICE_LOGIN){
            if (resultCode == Activity.RESULT_OK){
                setResult(Activity.RESULT_OK);
                refresh();
            }
        }
    }
}