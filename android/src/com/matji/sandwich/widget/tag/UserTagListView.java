package com.matji.sandwich.widget.tag;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.matji.sandwich.Refreshable;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.UserTagPostListActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.TagHttpRequest;

public class UserTagListView extends TagListView implements Requestable, Refreshable {
    
    private HttpRequest request;
    private HttpRequestManager manager;

    private User user;

    private ViewGroup spinnerContainer;
    
    /**
     * 기본 생성자 (XML)
     * 
     * @param context
     * @param attr
     */
    public UserTagListView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }
    
    protected void init() {
        manager = HttpRequestManager.getInstance();
    }
    

    public void setUser(User user) {
        this.user = user;
    }
    
    public void setSpinnerContainer(ViewGroup spinnerContainer) {
        this.spinnerContainer = spinnerContainer;
    }
    
    protected HttpRequest request() {
        if (request == null || !(request instanceof TagHttpRequest)) {
            request = new TagHttpRequest(getContext());
        }
        
        ((TagHttpRequest) request).actionUserTagList(user.getId(), getPage(), getLimit());

        return request;
    }
    
    @Override
    protected OnClickListener getListener(final Tag tag) {
        return new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserTagPostListActivity.class);
                intent.putExtra(UserTagPostListActivity.TAG, (Parcelable) tag);
                getContext().startActivity(intent);
            }
        };
    }
    
    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) {
        case HttpRequestManager.USER_TAG_LIST_REQUEST:
            ArrayList<Tag> tags = new ArrayList<Tag>();
            for (MatjiData d : data) {
                tags.add((Tag) d);
            }

            show(tags);
        }
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        // TODO Auto-generated method stub
        e.performExceptionHandling(getContext());
    }

    @Override
    public void refresh() {
        manager.request(getContext(), spinnerContainer, request(), HttpRequestManager.USER_TAG_LIST_REQUEST, this);
    }    
    
    @Override
    public void refresh(MatjiData data) {
        if (data instanceof User) {
            this.user = (User) data;
            refresh();
        }        
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {
        // TODO Auto-generated method stub
        
    }
}