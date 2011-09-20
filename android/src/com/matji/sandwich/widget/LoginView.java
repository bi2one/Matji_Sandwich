package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.matji.sandwich.Loginable;
import com.matji.sandwich.R;
import com.matji.sandwich.session.Session.LoginAsyncTask;

public class LoginView extends RelativeLayout implements OnClickListener {

	private Toast toast;
	
	private EditText idField;
	private EditText pwdField;
	
	private View loginTwitter;
	private View loginFacebook;
	
    public LoginView(Context context) {
        super(context);
        init();
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    protected void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.login, this);
        toast = Toast.makeText(getContext(), R.string.login_writing_id_password, Toast.LENGTH_SHORT);
        idField = ((EditText) findViewById(R.id.login_username));
        pwdField = ((EditText) findViewById(R.id.login_password));
        loginTwitter = findViewById(R.id.login_twitter);
        loginFacebook = findViewById(R.id.login_facebook);
        
        loginTwitter.setOnClickListener(this);
        loginFacebook.setOnClickListener(this);
    }
    
    public void clearField() {
    	idField.setText("");
    	pwdField.setText("");
    }
    
    public void login(Loginable loginable) {
    	if (idField.getText().toString().trim().equals("")
    			|| pwdField.getText().toString().trim().equals("")) {
    		toast.show();
    	} else {
        	new LoginAsyncTask(getContext(), loginable, idField.getText().toString(), pwdField.getText().toString()).execute(new Object());
    	}
    }
    

    public void loginViaTiwtter(View v){
    	Log.d("Matji", "login via twitter");
//        MeHttpRequest request = new MeHttpRequest(this);
//        request.authorizeViaExternalService(this, Service.TWITTER);
    }
    
    public void loginViaFacebook(View v){
    	Log.d("Matji", "login via facebook");
//        MeHttpRequest request = new MeHttpRequest(this);
//        request.authorizeViaExternalService(this, Service.FACEBOOK);        
    }

	@Override
	public void onClick(View v) {
		if (v.getId() == loginTwitter.getId()) {
			loginViaTiwtter(v);
		} else if (v.getId() == loginFacebook.getId()) {
			loginViaFacebook(v);
		}
	}    
    
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//         TODO Auto-generated method stub
//        if (requestCode == BaseActivity.REQUEST_EXTERNAL_SERVICE_LOGIN){
//            if (resultCode == Activity.RESULT_OK){
//                setResult(Activity.RESULT_OK);
//                finish();
//            }
//        }
//    }
}