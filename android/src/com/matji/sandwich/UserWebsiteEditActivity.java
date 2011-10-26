package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.http.util.Utils;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class UserWebsiteEditActivity extends BaseActivity implements Completable, Requestable {

    private Session session;
    private UserHttpRequest request;
    private HttpRequestManager manager;

    private CompletableTitle title;
    private EditText field;
    
    public int setMainViewId() {
        return R.id.activity_user_website_edit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }
    
    private void init() {
        setContentView(R.layout.activity_user_website_edit);

        session = Session.getInstance(this);
        request = new UserHttpRequest(this);
        manager = HttpRequestManager.getInstance();

        title = (CompletableTitle) findViewById(R.id.Titlebar);
        field = (EditText) findViewById(R.id.user_website_edit_field);

        title.setTitle(R.string.default_string_website);
        title.setCompletable(this);
        title.lockCompletableButton();
        TextWatcher tw = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if (count == 0 
                        && field.getText().toString().trim().equals(
                                session.getCurrentUser().getWebsite())) {
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
        field.addTextChangedListener(tw);
        field.setText(session.getCurrentUser().getWebsite());
        field.setSelection(field.length());
    }

    @Override
    public void complete() {
        title.lockCompletableButton();
        request.actionUpdateWebsite(Utils.getCorrectUrl(field.getText().toString().trim()));
        manager.request(getApplicationContext(), getMainView(), request, HttpRequestManager.USER_UPDATE_REQUEST, this);
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) { 
        case HttpRequestManager.USER_UPDATE_REQUEST:
            session.getCurrentUser().setWebsite(Utils.getCorrectUrl(field.getText().toString().trim()));
            finish();
            break;
        }
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        title.unlockCompletableButton();
        e.performExceptionHandling(this);
    }
}