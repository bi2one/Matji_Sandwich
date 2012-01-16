package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.PostAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;

/**
 * View displaying the list with sectioned header.
 * 
 * @author mozziluv
 * 
 */
public class PostListView extends RequestableMListView {
    private HttpRequest request;
    private Type type;
    public enum Type {
        ALL, FRIEND, DOMESTIC;
    };

    protected String getSubtitle(Type type) {
        switch (type) {
        case ALL: return MatjiConstants.string(R.string.subtitle_all_post);
        case FRIEND: return MatjiConstants.string(R.string.subtitle_friend_post);
        case DOMESTIC: return MatjiConstants.string(R.string.subtitle_domestic_post);
        default : return "";
        }
    }

    public PostListView(Context context, AttributeSet attr) {
        super(context, attr, new PostAdapter(context), 10);
        init();
    }	

    protected void init() {
        setBackgroundDrawable(MatjiConstants.drawable(R.drawable.bg_01));
        setDivider(null);
        setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
        setCacheColorHint(Color.TRANSPARENT);
        setSelector(android.R.color.transparent);
    }

    public void setSubtitle(String subtitle) {
        ((PostAdapter) getMBaseAdapter()).setSubtitle(subtitle);
    }

    public HttpRequest request() {
        if (request == null || !(request instanceof PostHttpRequest)) {
            request = new PostHttpRequest(getContext());
        }

        switch(type) {
        case ALL:
            ((PostHttpRequest) request).actionList(getPage(), getLimit());
            break;
        case FRIEND:
            ((PostHttpRequest) request).actionFriendList(getPage(), getLimit());
            break;
        case DOMESTIC:
            if (Session.getInstance(getContext()).isLogin()) {
                ((PostHttpRequest) request).actionCountryList(getPage(),
                        getLimit(),
                        Session.getInstance(getContext()).getCurrentUser().getCountryCode());
            } else {
                ((PostHttpRequest) request).actionCountryList(getPage(),
                        getLimit(),
                        getContext().getResources().getConfiguration().locale.getCountry());
            }
            break;
        default:
            ((PostHttpRequest) request).actionList(getPage(), getLimit());
        }
        return request;
    }

    public void setType(Type type) {
        this.type = type;
        setSubtitle(getSubtitle(type));
    }

    public void setPosts(ArrayList<MatjiData> data) {
        getMBaseAdapter().setData(data);
    }

    public void onListItemClick(int position) {}
}
