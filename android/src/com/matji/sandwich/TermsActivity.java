package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class TermsActivity extends BaseActivity implements Completable, Requestable {

    public static final String FROM_REGISTER_ACTIVITY = "TermActivity.from_register_activity"; 
    
    private CompletableTitle title;
    private boolean fromRegisterActivity;
    
    @Override
    public int setMainViewId() {
        return R.id.activity_terms;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_terms);
        
        fromRegisterActivity = getIntent().getBooleanExtra(FROM_REGISTER_ACTIVITY, true);
        
        title = ((CompletableTitle) findViewById(R.id.Titlebar));
        title.setCompletable(this);
        title.setTitle(R.string.default_string_terms);
        title.setButtonLabel(R.string.default_string_agree);
    }

    @Override
    public void complete() {
        if (!fromRegisterActivity) {
            UserHttpRequest request = new UserHttpRequest(this);
            request.actionAgreeTerm(Session.getInstance(this).getToken());
            
            HttpRequestManager.getInstance().request(this, getMainView(), request, HttpRequestManager.USER_AGREE_TERM_REQUEST, this);
        } else {
            finish();
        }
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) {
        case HttpRequestManager.USER_AGREE_TERM_REQUEST:
            setResult(RESULT_OK);
            finish();
            break;
        }
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(this);
    }        
}
