package com.matji.sandwich.widget.tag;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.Refreshable;
import com.matji.sandwich.UserTagPostListActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.TagHttpRequest;

public class UserTagListView extends TagListView implements Refreshable {

    private HttpRequest request;

    private User user;

    /**
     * 기본 생성자 (XML)
     * 
     * @param context
     * @param attr
     */
    public UserTagListView(Context context, AttributeSet attr) {
        super(context, attr);
    }


    public void setUser(User user) {
        this.user = user;
        requestReload();
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
    public void refresh() {
        requestReload();
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