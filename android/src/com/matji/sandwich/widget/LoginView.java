package com.matji.sandwich.widget;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.matji.sandwich.Loginable;
import com.matji.sandwich.R;
import com.matji.sandwich.RegisterActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionPrivateUtil;

public class LoginView extends RelativeLayout implements OnClickListener, OnCheckedChangeListener {
    private Toast toast;

    private EditText idField;
    private EditText pwdField;
    
    private View register;
    private WeakReference<Loginable> loginableRef;

    private CheckBox saveidCheckBox;
//    private View loginTwitter;
//    private View loginFacebook;

    private Session session;
    private SessionPrivateUtil privateUtil;

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

        idField = (EditText) findViewById(R.id.login_username);
        pwdField = (EditText) findViewById(R.id.login_password);
        saveidCheckBox = (CheckBox) findViewById(R.id.login_save_id);
        register = findViewById(R.id.login_register);
//        loginTwitter = findViewById(R.id.login_twitter);
//        loginFacebook = findViewById(R.id.login_facebook);

        privateUtil = Session.getInstance(getContext()).getPrivateUtil();
        idField.setText(privateUtil.getSavedUserId());
        saveidCheckBox.setChecked(privateUtil.isCheckedSaveId());        
        saveidCheckBox.setOnCheckedChangeListener(this);
        register.setOnClickListener(this);
//        loginTwitter.setOnClickListener(this);
//        loginFacebook.setOnClickListener(this);

        session = Session.getInstance(getContext());
    }

    public void clearField() {
        idField.setText(privateUtil.getSavedUserId());
        pwdField.setText("");
    }

    public void login(Loginable loginable) {
	this.loginableRef = new WeakReference<Loginable>(loginable);
        if (idField.getText().toString().trim().equals("")
	    || pwdField.getText().toString().trim().equals("")) {
            toast.show();
        } else {
            if (saveidCheckBox.isChecked()) {
                privateUtil.setSavedUserId(idField.getText().toString());
            }

	    session.loginWithDialog(getContext(), idField.getText().toString(), pwdField.getText().toString(), loginableRef.get());
            // MeHttpRequest request = new MeHttpRequest(getContext());
            // request.actionAuthorize(idField.getText().toString(), pwdField.getText().toString());

            // DialogAsyncTask dialogTask = new DialogAsyncTask(getContext(), this, request, REQUEST_LOGIN);
//            new LoginAsyncTask(getContext(), loginable, idField.getText().toString(), pwdField.getText().toString()).execute(new Object());
            // dialogTask.execute();
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

    public void registerClicked(View v) {
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        getContext().startActivity(intent);
    }
    
    @Override
    public void onClick(View v) {
        if (v.getId() == register.getId()) {
            registerClicked(v);
//        } else if (v.getId() == loginTwitter.getId()) {
//            loginViaTiwtter(v);
//        } else if (v.getId() == loginFacebook.getId()) {
//            loginViaFacebook(v);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
        privateUtil.setIsCheckedSaveId(isChecked);
        if (!isChecked) {
            privateUtil.setSavedUserId("");
        }

    }

	// @Override
	// public void requestCallBack(int tag, ArrayList<MatjiData> data) {
	// 	Me me = (Me)data.get(0);
	// 	session.saveMe(me);
	// 	if (loginableRef != null && loginableRef.get() != null) {
	// 	    loginableRef.get().loginCompleted();
	// 	}
	// }

	// @Override
	// public void requestExceptionCallBack(int tag, MatjiException e) {
	// 	e.performExceptionHandling(getContext());
	// 	if (loginableRef != null && loginableRef.get() != null) {
	// 	    loginableRef.get().loginFailed();
	// 	}
	// }    

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