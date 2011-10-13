package com.matji.sandwich;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.widget.EditText;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.DialogAsyncTask;
import com.matji.sandwich.http.request.FindPasswordHttpRequest;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class FindPasswordActivity extends BaseActivity implements Completable, Requestable, SimpleAlertDialog.OnClickListener {
	private static final int TAG_FIND_PASSWORD = 1;

	private CompletableTitle title; 
	
	private EditText etEmail;
	
	private SimpleAlertDialog emailIsNullDialog;
	private SimpleAlertDialog emailIsIncorrectDialog;
	private SimpleAlertDialog successDialog;
	
	public int setMainViewId() {
		return R.id.activity_find_password;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_password);
		
		title = (CompletableTitle) findViewById(R.id.Titlebar);
		etEmail = (EditText) findViewById(R.id.find_password_email_field);
		
		emailIsNullDialog = new SimpleAlertDialog(this, R.string.register_email_is_null);
		emailIsIncorrectDialog = new SimpleAlertDialog(this, R.string.register_email_is_incorrect);
		successDialog = new SimpleAlertDialog(this, R.string.find_password_success);
		successDialog.setOnClickListener(this);
		
		title.setTitle(R.string.login_find_password);
		title.setCompletable(this);
	}
	
	public void complete() {
		String email = etEmail.getText().toString();
		if (email.equals("")) {
			emailIsNullDialog.show();
		} else if (!isCorrectEmail(email)) {
			emailIsIncorrectDialog.show();
		} else {
			title.lockCompletableButton();
			FindPasswordHttpRequest request = new FindPasswordHttpRequest(FindPasswordActivity.this);
			request.actionForgotPassword(email);
			DialogAsyncTask requestTask = new DialogAsyncTask(this, this, getMainView(), request, TAG_FIND_PASSWORD);
			requestTask.execute();
			title.setCompletable(null);
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
		case TAG_FIND_PASSWORD:
			successDialog.show();
			break;
		}
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);
		title.setCompletable(this);
	}

	public void onConfirmClick(SimpleDialog dialog) {
		if (dialog == successDialog) {
			setResult(RESULT_OK);
			finish();
		}
	}
	
}
