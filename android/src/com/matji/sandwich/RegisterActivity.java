package com.matji.sandwich;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class RegisterActivity extends BaseActivity implements Completable, Requestable, Loginable {
    private Session session;
    private CompletableTitle title;
    private EditText etEmail;
    private EditText etNickname;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private TextView tvAccept;
    private CheckBox cbAccept;
    private Button bRegister;

    private String email;
    private String nickname;
    private String password;
    private String passwordConfirm;

    private SimpleAlertDialog emailIsNullDialog;
    private SimpleAlertDialog emailIsIncorrectDialog;
    private SimpleAlertDialog nicknameIsNullDialog;
    private SimpleAlertDialog nicknameLengthErrorDialog;
    private SimpleAlertDialog passwordIsNullDialog;
    private SimpleAlertDialog passwordLengthErrorDialog;
    private SimpleAlertDialog passwordIsNotEqualsDialog;
    private SimpleAlertDialog plzAcceptDialog;
    private SimpleAlertDialog successDialog;
    
    public int setMainViewId() {
        return R.id.activity_register;
    }

    protected void init() {
        super.init();

        setContentView(R.layout.activity_register);
        findViews();
        createDialogs();
        setListeners();

        title.setTitle(R.string.default_string_register);
        title.setCompletable(this);

	session = Session.getInstance(this);
    }

    private void findViews() {
        title = (CompletableTitle) findViewById(R.id.Titlebar);        
        etEmail = (EditText) findViewById(R.id.register_email_field);
        etNickname = (EditText) findViewById(R.id.register_nickname_field);
        etPassword = (EditText) findViewById(R.id.register_password_field);
        etPasswordConfirm = (EditText) findViewById(R.id.register_password_confirm_field);
        tvAccept = (TextView) findViewById(R.id.register_view_acceptance_of_terms);
        cbAccept = (CheckBox) findViewById(R.id.register_acceptance_of_terms_check);
        bRegister = (Button) findViewById(R.id.register_btn);
    }

    private void setListeners() {
        tvAccept.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // move to clause activity
            }
        });
        
        bRegister.setOnClickListener(new OnClickListener() {
		
            @Override
            public void onClick(View arg0) {
                complete();
            }
        });
        
        successDialog.setOnClickListener(new SimpleAlertDialog.OnClickListener() {
            @Override
            public void onConfirmClick(SimpleDialog dialog) {
		session.loginWithDialog(RegisterActivity.this, email, password, RegisterActivity.this);
            }
        });
    }
    
    private void createDialogs() {
        emailIsNullDialog = new SimpleAlertDialog(this, R.string.register_email_is_null);
        emailIsIncorrectDialog = new SimpleAlertDialog(this, R.string.register_email_is_incorrect);
        nicknameIsNullDialog = new SimpleAlertDialog(this, R.string.register_nickname_is_null);
        nicknameLengthErrorDialog = new SimpleAlertDialog(this, R.string.register_nickname_too_short_or_too_long);
        passwordIsNullDialog = new SimpleAlertDialog(this, R.string.register_password_is_null);
        passwordLengthErrorDialog = new SimpleAlertDialog(this, R.string.register_password_too_short_or_too_long);
        passwordIsNotEqualsDialog = new SimpleAlertDialog(this, R.string.register_password_is_not_equals);
        plzAcceptDialog = new SimpleAlertDialog(this, R.string.register_plz_accept);
        successDialog = new SimpleAlertDialog(this, R.string.register_success);
    }

    @Override
    public void complete() {
        email = etEmail.getText().toString();
        nickname = etNickname.getText().toString();
        password = etPassword.getText().toString();
        passwordConfirm = etPasswordConfirm.getText().toString();
        
        if (email.equals("")) {
            emailIsNullDialog.show();
        } else if (!isCorrectEmail(email)) {
            emailIsIncorrectDialog.show();            
        } else if (nickname.equals("")) {
            nicknameIsNullDialog.show();
        } else if (nickname.length() < MatjiConstants.MIN_NICK_LENGTH
                || nickname.length() > MatjiConstants.MAX_NICK_LENGTH) {
            Log.d("Matji", nickname.length()+"");
            nicknameLengthErrorDialog.show();
        } else if (password.equals("") || passwordConfirm.equals("")) {
            passwordIsNullDialog.show();
        } else if (password.length() < MatjiConstants.MIN_PASSWORD_LENGTH
                || password.length() > MatjiConstants.MAX_PASSWORD_LENGTH
                || passwordConfirm.length() < MatjiConstants.MIN_PASSWORD_LENGTH
                || passwordConfirm.length() > MatjiConstants.MAX_PASSWORD_LENGTH) {
            passwordLengthErrorDialog.show();
        } else if (!password.equals(passwordConfirm)) {        
            passwordIsNotEqualsDialog.show();
        } else if (!cbAccept.isChecked()) {
            plzAcceptDialog.show();
        } else {
            requestRegister(email, nickname, password, passwordConfirm);
        }
    }

    private void requestRegister(String email, String nick, String password, String password_confirmation) {
        title.lockCompletableButton();
        HttpRequestManager manager = HttpRequestManager.getInstance();
        UserHttpRequest request = new UserHttpRequest(this);
        request.actionCreate(email, nick, password, password_confirmation);
        manager.request(getApplicationContext(), getMainView(), request, 0, this);
    }
    
    /**
     * 전달받은 문자열이 올바른 이메일의 형태인지 확인한다.
     * 
     * @param email 확인할 문자열
     * @return 이메일이면 true
     */
    public boolean isCorrectEmail(String email) {
        if (email == null) return false;
        boolean isCorrect = Pattern.matches(
                "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", 
                email);
        return isCorrect;
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        successDialog.show();
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        title.unlockCompletableButton();
        e.performExceptionHandling(this);
    }

    public void loginCompleted() {
	Intent intent = new Intent(this, MainTabActivity.class);
	intent.putExtra(MainTabActivity.IF_INDEX, MainTabActivity.IV_INDEX_CONFIG);
	startActivity(intent);
	finish();
    }

    public void loginFailed() { }
}