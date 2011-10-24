package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest.TagByType;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;

public class TagPostListView extends PostListView implements Requestable {

    private HttpRequest request;
    private Tag tag;
    private TagByType type;
    
    public SimpleAlertDialog tagDialog;
    
    public TagPostListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void init() {
        request = new PostHttpRequest(getContext());
        tagDialog = new SimpleAlertDialog(getContext(), R.string.store_tag_unlink_post);
        setPage(1);
    }

    public void setType(TagByType type) {
        this.type = type;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        super.init();
        setSubtitle(String.format(
                MatjiConstants.string(R.string.subtitle_tag_post),
                tag.getTag().getTag()));
    }    

    @Override
    public HttpRequest request() {
        ((PostHttpRequest) request).actionTagByList(type, tag.getId(), getPage(), getLimit());
        return request;
    }
    

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		
		if (data == null || data.size() == 0) {
			tagDialog.show();
		} else {
			super.requestCallBack(tag, data);
		}
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(getContext());
	}
}