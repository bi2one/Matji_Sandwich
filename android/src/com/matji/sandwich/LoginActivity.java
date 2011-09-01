package com.matji.sandwich;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.request.MeHttpRequest;
import com.matji.sandwich.http.request.MeHttpRequest.Service;

public class LoginActivity extends BaseActivity implements Loginable {

    public int setMainViewId() {
        return R.id.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    
    public void loginButtonClicked(View v) {
        EditText usernameField = (EditText) findViewById(R.id.username);
        EditText passwordField = (EditText) findViewById(R.id.password);        
        
        new LoginAsyncTask(this, this, getMainView(), usernameField.getText().toString(), passwordField.getText().toString()).execute(new Object());
    }

    public void cancelButtonClicked(View v) {
        finish();

    }

    /* Loginable Interface methods */
    public void loginCompleted() {
        this.setResult(RESULT_OK);
        finish();
    }

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
                finish();
            }
        }
    }
}