package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog.OnClickListener;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class PostEditActivity extends BaseActivity implements Completable, Requestable {

    public static final String POST = "PostEditActivity.post";

    private Toast writingPostToast;

    private HttpRequestManager manager;
    private HttpRequest request;

    private Post post;
    
    private EditText postField;
    private CompletableTitle title;
    private SimpleAlertDialog dialog;

    @Override
    public int setMainViewId() {
        return R.id.post_edit_activity;
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        super.init();
        setContentView(R.layout.activity_post_edit);

        manager = HttpRequestManager.getInstance();
        title = (CompletableTitle) findViewById(R.id.Titlebar);
        post = (Post) getIntent().getParcelableExtra(POST);
        postField = (EditText) findViewById(R.id.post_edit_field);
       
        writingPostToast = Toast.makeText(this, R.string.writing_content_post, Toast.LENGTH_SHORT);
        title.setCompletable(this);
        title.lockCompletableButton();
        title.setTitle(R.string.title_post_edit);
        
        postField.setText(post.getPost());
        TextWatcher tw = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if (postField.getText().toString().trim().equals(post.getPost())) {
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
        postField.addTextChangedListener(tw);
        
        dialog = new SimpleAlertDialog(this, R.string.post_edit_confirm);
        dialog.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onConfirmClick(SimpleDialog dialog) {
                finish();
            }
        });
    }

    @Override
    public void complete() {
        String post = postField.getText().toString().trim();
        if (post.equals("")) {
            writingPostToast.show();
        } else {
            title.lockCompletableButton();
            modify(post);
        }
    }
    
    public void modify(String newPost) {
        if (request != null || !(request instanceof PostHttpRequest)) {
            request = new PostHttpRequest(this);
        }
        ((PostHttpRequest) request).actionModify(post.getId(), newPost);
        
        manager.request(this, getMainView(), request, HttpRequestManager.POST_MODIFY_REQUEST, this);
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch(tag) {
        case HttpRequestManager.POST_MODIFY_REQUEST:
            title.unlockCompletableButton();
            dialog.show();
            Intent intent = new Intent();
            intent.putExtra(POST, (Post) data.get(0));
            setResult(RESULT_OK, intent);
            break;
        }
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        title.unlockCompletableButton();
        e.performExceptionHandling(this);
    }
}