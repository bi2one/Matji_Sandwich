package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.SimpleStoreAdapter;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.RequestCommand;
import com.matji.sandwich.http.request.StoreHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class LikeStoreListView extends RequestableMListView {
    private User user;
    private StoreHttpRequest storeRequest;

    public LikeStoreListView(Context context, AttributeSet attrs) {
        super(context, attrs, new SimpleStoreAdapter(context), 10);
        storeRequest = new StoreHttpRequest(context);
    }

    public void setUser(User user) {
        this.user = user;
        String title =
            "'" + user.getNick() + "'" 
            + MatjiConstants.string(R.string.highlight_like_stores);
        addHeaderView(
                new HighlightHeader(getContext(), title, user.getLikeStoreCount()));
    }

    @Override
    public void onListItemClick(int position) {}

    @Override
    public RequestCommand request() {
        storeRequest.actionLikeList(user.getId(), getPage(), getLimit());

        return storeRequest;
    }
}