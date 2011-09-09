package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
        setBackgroundColor(MatjiConstants.color(R.color.matji_white));
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight((int) MatjiConstants.dimen(R.dimen.default_divider_size));
        storeRequest = new StoreHttpRequest(context);
    }

    public void setUser(User user) {
        this.user = user;
        String title = String.format(MatjiConstants.string(R.string.highlight_like_stores), user.getNick());
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