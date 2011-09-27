package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class StoreTagPostListView extends PostListView {

    private HttpRequest request;
    private Tag tag;

    public StoreTagPostListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void init() {
        request = new PostHttpRequest(getContext());
        setPage(1);
    }
    
    public void setStoreTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public HttpRequest request() {
        ((PostHttpRequest) request).actionUserTagByList(tag.getId(), getPage(), getLimit());
        return request;
    }

    @Override
    protected String getSubtitle() {
        return String.format(
                MatjiConstants.string(R.string.subtitle_tag_post),
                tag.getTag().getTag());
    }
}