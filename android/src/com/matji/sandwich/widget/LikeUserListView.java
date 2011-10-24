package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.SimpleUserAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.exception.NotSupportedMatjiException;
import com.matji.sandwich.http.request.RequestCommand;
import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.util.MatjiConstants;

public class LikeUserListView extends RequestableMListView {
    private MatjiData data;
    private UserHttpRequest userRequest;
    private int type = MatjiData.UNSELECTED;

    public LikeUserListView(Context context, AttributeSet attrs) {
        super(context, attrs, new SimpleUserAdapter(context), 10);
        init();
    }

    public void init() {
        setBackgroundResource(R.color.matji_white);
        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight((int) MatjiConstants.dimen(R.dimen.default_divider_size));
        setFadingEdgeLength(0);
        setCacheColorHint(Color.TRANSPARENT);
        setSelector(android.R.color.transparent);
        userRequest = new UserHttpRequest(getContext());
    }
    
    public void setData(MatjiData data) {
        this.data = data;
        if (data instanceof Store) {
            type = MatjiData.STORE;
            Store store = (Store) data;
            String title =
                String.format(
                        MatjiConstants.string(R.string.highlight_store_like_users),
                        store.getName());

            addHeaderView(
                    new HighlightHeader(
                            getContext(), 
                            title, 
                            store.getLikeCount()));
        } else if (data instanceof Post) {
            type = MatjiData.POST;
            Post post = (Post) data;
            addHeaderView(
                    new HighlightHeader(
                            getContext(), 
                            MatjiConstants.string(R.string.highlight_post_like_users), 
                            post.getLikeCount()));
        } else if (data instanceof StoreFood) {
            type = MatjiData.STORE_FOOD;
            StoreFood food = (StoreFood) data;
            addHeaderView(
                    new HighlightHeader(
                            getContext(),
                            MatjiConstants.string(R.string.highlight_store_food_like_users),
                            food.getLikeCount()));
        } else {
            try {
                throw new NotSupportedMatjiException();
            } catch (NotSupportedMatjiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void onListItemClick(int position) {}

    @Override
    public RequestCommand request() {
        switch (type) {
        case MatjiData.STORE:
            userRequest.actionStoreLikeList(((Store) data).getId(), getPage(), getLimit());
            break;
        case MatjiData.POST:
            userRequest.actionPostLikeList(((Post) data).getId(), getPage(), getLimit());
            break;
        case MatjiData.STORE_FOOD:
            userRequest.actionStoreFoodLikeList(((StoreFood) data).getId(), getPage(), getLimit());
            break;
        default:
            try {
                throw new NotSupportedMatjiException();
            } catch (NotSupportedMatjiException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return userRequest;
    }
}