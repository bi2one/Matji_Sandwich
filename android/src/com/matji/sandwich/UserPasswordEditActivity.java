package com.matji.sandwich;

import java.util.ArrayList;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

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

public class UserPasswordEditActivity extends BaseActivity implements Completable, Requestable {

    private UserHttpRequest request;
    private HttpRequestManager manager;

    private CompletableTitle title;
    private EditText curPwdField;
    private EditText newPwdField;
    private EditText newPwdConfirmField;
//    private SimpleAlertDialog pwdIncorrectDialog;
    private SimpleAlertDialog pwdNotEqualDialog;
    private SimpleAlertDialog pwdChangedDialog;

    public int setMainViewId() {
        return R.id.activity_user_password_edit;
    }

    @Override
    protected void init() {
        setContentView(R.layout.activity_user_password_edit);

        request = new UserHttpRequest(this);
        manager = HttpRequestManager.getInstance(this);
//        pwdIncorrectDialog = new SimpleAlertDialog(this, R.string.register_password_is_incorrect);
        pwdNotEqualDialog = new SimpleAlertDialog(this, R.string.register_password_is_not_equals);
        pwdChangedDialog = new SimpleAlertDialog(this, R.string.user_password_edit_password_changed);
        
        title = (CompletableTitle) findViewById(R.id.Titlebar);
        curPwdField = (EditText) findViewById(R.id.user_current_password_field);
        newPwdField = (EditText) findViewById(R.id.user_new_password_field);
        newPwdConfirmField = (EditText) findViewById(R.id.user_new_password_confirm_field);
        
        pwdChangedDialog.setOnClickListener(new SimpleAlertDialog.OnClickListener() {

            @Override
            public void onConfirmClick(SimpleDialog dialog) {
                finish();
            }
        });

        title.setTitle(R.string.edit_password);
        title.setCompletable(this);
        title.lockCompletableButton();
        TextWatcher tw = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("Matji", newPwdField.getText().length()+", "+ MatjiConstants.MIN_PASSWORD_LENGTH);
                Log.d("Matji", newPwdConfirmField.getText().length()+", "+ MatjiConstants.MIN_PASSWORD_LENGTH);
                if (newPwdField.getText().length() < MatjiConstants.MIN_PASSWORD_LENGTH
                        || newPwdConfirmField.getText().length() < MatjiConstants.MIN_PASSWORD_LENGTH) {
                    title.lockCompletableButton();
                } else {
                    title.unlockCompletableButton();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };
        newPwdField.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(MatjiConstants.MAX_PASSWORD_LENGTH)
        });
        newPwdConfirmField.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(MatjiConstants.MAX_PASSWORD_LENGTH)
        });
        newPwdField.addTextChangedListener(tw);
        newPwdConfirmField.addTextChangedListener(tw);
    }

    @Override
    public void complete() {
        title.lockCompletableButton();
        if (!newPwdField.getText().toString().equals(newPwdConfirmField.getText().toString())) {
            title.unlockCompletableButton();
            pwdNotEqualDialog.show();
        } else {
            request.actionChangePassword(curPwdField.getText().toString(), newPwdField.getText().toString(), newPwdField.getText().toString());
            manager.request(getMainView(), request, HttpRequestManager.USER_CHANGE_PASSWORD_REQUEST, this);
        }
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) { 
        case HttpRequestManager.USER_CHANGE_PASSWORD_REQUEST:
            pwdChangedDialog.show();
            break;
        }
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        title.unlockCompletableButton();
        e.performExceptionHandling(this);
    }
}