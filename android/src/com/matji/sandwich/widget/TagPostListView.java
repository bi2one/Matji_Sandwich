package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest.TagByType;
import com.matji.sandwich.util.MatjiConstants;

public class TagPostListView extends PostListView {

    private HttpRequest request;
    private Tag tag;
    private TagByType type;

    public TagPostListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void init() {
        request = new PostHttpRequest(getContext());
        setPage(1);
    }
    
    public void setType(TagByType type) {
        this.type = type;
    }
    
    public void setTag(Tag tag) {
        this.tag = tag;
        super.init();
    }    
    
    @Override
    public HttpRequest request() {
        ((PostHttpRequest) request).actionTagByList(type, tag.getId(), getPage(), getLimit());
        return request;
    }

    @Override
    protected String getSubtitle() {
        return String.format(
                MatjiConstants.string(R.string.subtitle_tag_post),
                tag.getTag().getTag());
    }
}