package com.matji.sandwich.widget;

import java.util.ArrayList;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.PostSectionedAdapter;
import com.matji.sandwich.adapter.SimplePostAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

import android.content.Context;
import android.util.AttributeSet;

/**
 * View displaying the list with sectioned header.
 * 
 * @author mozziluv
 * 
 */
public class SimplePostListView extends RequestableMListView {
    private HttpRequest request;

    public SimplePostListView(Context context, AttributeSet attr) {
        super(context, attr, new SimplePostAdapter(context), 10);
        init();
    }	

    protected void init() {
        setBackgroundDrawable(MatjiConstants.drawable(R.drawable.pattern_bg));
//        setDivider(null);
//        setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
//        setCacheColorHint(Color.TRANSPARENT);
//        setSelector(android.R.color.transparent);
    }

    public HttpRequest request() {
        if (request == null || !(request instanceof PostHttpRequest)) {
            request = new PostHttpRequest(getContext());
        }
        ((PostHttpRequest) request).actionListWithAttachFiles(getPage(), getLimit());
        return request;
    }

    public void setPosts(ArrayList<MatjiData> data) {
        getMBaseAdapter().setData(data);
    }

    public void onListItemClick(int position) {}
}
