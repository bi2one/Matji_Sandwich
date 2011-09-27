package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class UserTagPostListView extends PostListView {

    private HttpRequest request;
    private Tag tag;

    public UserTagPostListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void init() {
        request = new PostHttpRequest(getContext());
        setPage(1);
    }
    
    public void setUserTag(Tag tag) {
        this.tag = tag;
        super.init();
    }

    @Override
    public HttpRequest request() {
        ((PostHttpRequest) request).actionUserTagByList(tag.getId(), getPage(), getLimit());
        return request;
    }

    @Override
    protected String getSubtitle() {
        Log.d("Matji", tag.getTag()+"");
        Log.d("Matji", tag.getCreatedAt()+"");
        Log.d("Matji", tag.getCount()+"");
        Log.d("Matji", tag.getTagId()+"");
        
        return String.format(
                MatjiConstants.string(R.string.subtitle_tag_post),
                tag.getTag().getTag());
    }
}