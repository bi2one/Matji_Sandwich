package com.matji.sandwich.widget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.matji.sandwich.FindPasswordActivity;
import com.matji.sandwich.Loginable;
import com.matji.sandwich.R;
import com.matji.sandwich.RegisterActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Me;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.MeHttpRequest;
import com.matji.sandwich.http.request.MeHttpRequest.Service;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.SessionPrivateUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.dialog.SimpleNotificationPopup;

public class LoginView extends RelativeLayout implements OnClickListener, OnCheckedChangeListener {
	private SimpleNotificationPopup notification;
	private WeakReference<Loginable> loginableRef;
	private CheckBox saveidCheckBox;
	private View loginFacebook;
	private View loginTwitter;
	private View findPassword;
	private View register;
	private EditText pwdField;
	private EditText idField;
	private Toast toast;
	private SessionPrivateUtil privateUtil;
	private Session session;
	private Context context;

	public LoginView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public LoginView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	protected void init() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.login, this);
		session = Session.getInstance(getContext());        
		privateUtil = session.getPrivateUtil();
		notification = new SimpleNotificationPopup(getContext(), getClass().toString(), MatjiConstants.string(R.string.popup_login));
		toast = Toast.makeText(getContext(), R.string.login_writing_id_password, Toast.LENGTH_SHORT);
		initViews();
		listeners();
	}

	private void initViews() {
		idField = (EditText) findViewById(R.id.login_username);
		pwdField = (EditText) findViewById(R.id.login_password);
		saveidCheckBox = (CheckBox) findViewById(R.id.login_save_id);
		findPassword = findViewById(R.id.login_find_password);
		register = findViewById(R.id.login_register);
		loginTwitter = findViewById(R.id.login_twitter);
		loginFacebook = findViewById(R.id.login_facebook);
		idField.setText(privateUtil.getSavedUserId());
		saveidCheckBox.setChecked(privateUtil.isCheckedSaveId());        
	}

	private void listeners() {
		saveidCheckBox.setOnCheckedChangeListener(this);
		findPassword.setOnClickListener(this);
		register.setOnClickListener(this);
		loginTwitter.setOnClickListener(this);
		loginFacebook.setOnClickListener(this);
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
			session.loginWithDialog(getContext(), 
					idField.getText().toString(), 
					pwdField.getText().toString(), 
					loginableRef.get());
		}
	}

	public void loginViaTwitter(View v){
		MeHttpRequest request = new MeHttpRequest(context);
		request.authorizeViaExternalService(context, Service.TWITTER);
	}

	public void loginViaFacebook(View v){
		MeHttpRequest request = new MeHttpRequest(context);
		request.authorizeViaExternalService(context, Service.FACEBOOK);        
	}

	public void findPasswordClicekd(View v) {
		Intent intent = new Intent(getContext(), FindPasswordActivity.class);
		getContext().startActivity(intent);
	}

	public void registerClicked(View v) {
		Intent intent = new Intent(getContext(), RegisterActivity.class);
		getContext().startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == register.getId()) {
			registerClicked(v);
		} else if (v.getId() == findPassword.getId()) {
			findPasswordClicekd(v);
		} else if (v.getId() == loginTwitter.getId()) {
			loginViaTwitter(v);
		} else if (v.getId() == loginFacebook.getId()) {
			loginViaFacebook(v);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
		privateUtil.setIsCheckedSaveId(isChecked);
		if (!isChecked) {
			privateUtil.setSavedUserId("");
		}

	}

	public void notificationShow() {
		notification.show();
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		Me me = (Me)data.get(0);
		session.saveMe(me);
		if (loginableRef != null && loginableRef.get() != null) {
			loginableRef.get().loginCompleted();
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getContext());
		if (loginableRef != null && loginableRef.get() != null) {
			loginableRef.get().loginFailed();
		}
	}    

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == BaseActivity.EXTERNAL_SERVICE_LOGIN_REQUEST){
			if (resultCode == Activity.RESULT_OK){
				((Activity) context).setResult(Activity.RESULT_OK);
				((Activity) context).finish();
			}
		}
	}
}