package com.matji.sandwich;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Email;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.DialogAsyncTask;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.FindPasswordHttpRequest;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.title.HomeTitle;

public class FindPasswordActivity extends BaseActivity implements Requestable, SimpleAlertDialog.OnClickListener {
    private HomeTitle title; 

    private EditText etEmail;
    private EditText etUserid;

    private SimpleAlertDialog emailIsNullDialog;
    private SimpleAlertDialog useridIsNullDialog;
    private SimpleAlertDialog emailIsIncorrectDialog;
    private SimpleAlertDialog successDialog;
    private SimpleAlertDialog emailDialog;

    public int setMainViewId() {
        return R.id.activity_find_password;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        title = (HomeTitle) findViewById(R.id.Titlebar);
        etEmail = (EditText) findViewById(R.id.find_password_email_field);
        etUserid = (EditText) findViewById(R.id.find_email_userid_field);

        emailIsNullDialog = new SimpleAlertDialog(this, R.string.find_password_email_is_null);
        useridIsNullDialog = new SimpleAlertDialog(this, R.string.find_email_userid_is_null);
        emailIsIncorrectDialog = new SimpleAlertDialog(this, R.string.find_password_email_is_incorrect);
        successDialog = new SimpleAlertDialog(this, R.string.find_password_success);
        successDialog.setOnClickListener(this);

        title.setTitle(R.string.login_find_password);

        etEmail.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                        (e != null && e.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    findPassword();
                }
                return false;
            }
        });
        
        etUserid.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                        (e != null && e.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    findEmail();
                }   
                return false;
            }
        });
    }

    public void findPassword() {
        String email = etEmail.getText().toString();
        if (email.equals("")) {
            emailIsNullDialog.show();
        } else if (!isCorrectEmail(email)) {
            emailIsIncorrectDialog.show();
        } else {
            FindPasswordHttpRequest request = new FindPasswordHttpRequest(FindPasswordActivity.this);
            request.actionForgotPassword(email);
            DialogAsyncTask requestTask = new DialogAsyncTask(this, this, getMainView(), request, HttpRequestManager.USER_FIND_PASSWORD_REQUEST);
            requestTask.execute();
        }
    }

    public void findEmail() {
        String userid = etUserid.getText().toString();
        if (userid.equals("")) {
            useridIsNullDialog.show();
        } else {
            FindPasswordHttpRequest request = new FindPasswordHttpRequest(FindPasswordActivity.this);
            request.actionFindEmail(userid);
            DialogAsyncTask requestTask = new DialogAsyncTask(this, this, getMainView(), request, HttpRequestManager.USER_FIND_EMAIL_REQUEST);
            requestTask.execute();
        }
    }

    /**
     * 전달받은 문자열이 올바른 이메일의 형태인지 확인한다.
     * 
     * @param email 확인할 문자열
     * @return 이메일이면 true
     */
    public boolean isCorrectEmail(String email) {
        if (email == null) return false;
        boolean isCorrect = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email);
        return isCorrect;
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch(tag) {
        case HttpRequestManager.USER_FIND_PASSWORD_REQUEST:
            successDialog.show();
            break;
        case HttpRequestManager.USER_FIND_EMAIL_REQUEST:
            emailDialog = new SimpleAlertDialog(this, String.format(MatjiConstants.string(R.string.find_email_success), (Email) data.get(0)));
            emailDialog.show();
            break;
        }
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(this);
    }

    public void onConfirmClick(SimpleDialog dialog) {
        if (dialog == successDialog) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
