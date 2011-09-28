package com.matji.sandwich;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class RegisterActivity extends BaseActivity implements Completable, Requestable {

    private CompletableTitle title;
    private EditText etEmail;
    private EditText etNickname;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private TextView tvAccept;
    private CheckBox cbAccept;

    private SimpleAlertDialog emailIsNullDialog;
    private SimpleAlertDialog emailIsIncorrectDialog;
    private SimpleAlertDialog nicknameIsNullDialog;
    private SimpleAlertDialog nicknameLengthErrorDialog;
    private SimpleAlertDialog passwordIsNullDialog;
    private SimpleAlertDialog passwordLengthErrorDialog;
    private SimpleAlertDialog passwordIsIncorrectDialog;
    private SimpleAlertDialog plzAcceptDialog;
    private SimpleAlertDialog successDialog;
    
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
        findViews();
        createDialogs();
        setListeners();

        title.setTitle(R.string.default_string_register);
        title.setCompletable(this);
    }

    private void findViews() {
        title = (CompletableTitle) findViewById(R.id.Titlebar);        
        etEmail = (EditText) findViewById(R.id.register_email_field);
        etNickname = (EditText) findViewById(R.id.register_nickname_field);
        etPassword = (EditText) findViewById(R.id.register_password_field);
        etPasswordConfirm = (EditText) findViewById(R.id.register_password_confirm_field);
        tvAccept = (TextView) findViewById(R.id.register_view_acceptance_of_terms);
        cbAccept = (CheckBox) findViewById(R.id.register_acceptance_of_terms_check);
    }

    private void setListeners() {
        tvAccept.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // move to clause activity
            }
        });
        
        successDialog.setOnClickListener(new SimpleAlertDialog.OnClickListener() {
            
            @Override
            public void onConfirmClick(SimpleDialog dialog) {
                finish();
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
        passwordIsIncorrectDialog = new SimpleAlertDialog(this, R.string.register_password_is_incorrect);
        plzAcceptDialog = new SimpleAlertDialog(this, R.string.register_plz_accept);
        successDialog = new SimpleAlertDialog(this, R.string.register_success);
    }

    @Override
    public void complete() {
        
        String email = etEmail.getText().toString();
        String nickname = etNickname.getText().toString();
        String password = etPassword.getText().toString();
        String passwordConfirm = etPasswordConfirm.getText().toString();
        
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
            passwordIsIncorrectDialog.show();
        } else if (!cbAccept.isChecked()) {
            plzAcceptDialog.show();
        } else {
            requestRegister(email, nickname, password, passwordConfirm);
        }
    }

    private void requestRegister(String email, String nick, String password, String password_confirmation) {
        title.lockCompletableButton();
        HttpRequestManager manager = HttpRequestManager.getInstance(this);
        UserHttpRequest request = new UserHttpRequest(this);
        request.actionCreate(email, nick, password, password_confirmation);
        manager.request(getMainView(), request, 0, this);
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
}