package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.FollowingListView;
import com.matji.sandwich.widget.HighlightHeader;
import com.matji.sandwich.widget.title.HomeTitle;

public class FollowingActivity extends BaseActivity {
    private FollowingListView listView; 
    public enum FollowingListType {
        FOLLOWER, FOLLOWING
    }

    public static final String USER = "FollowingActivity.user";
    public static final String TYPE = "FollowingActivity.type";

    private HomeTitle title;
    private User user;

    public int setMainViewId() {
        return R.id.layout_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        title = (HomeTitle) findViewById(R.id.Titlebar);

        user = getIntent().getParcelableExtra(USER);
        FollowingListType type = (FollowingListType) getIntent().getSerializableExtra(TYPE);

        HighlightHeader header = null;
        String highlight = "";
        switch(type) {
        case FOLLOWER:
            title.setTitle(R.string.following_follower);

            if (Session.getInstance(this).isCurrentUser(user))
                highlight = MatjiConstants.string(R.string.highlight_followers_me);
            else
                highlight = String.format(
                        MatjiConstants.string(R.string.highlight_followers),
                        user.getNick());

            header = new HighlightHeader(this, highlight, user.getFollowerCount());
            break;
        case FOLLOWING:
            title.setTitle(R.string.following_following);

            if (Session.getInstance(this).isCurrentUser(user))
                highlight = MatjiConstants.string(R.string.highlight_followings_me);
            else
                highlight = String.format(
                        MatjiConstants.string(R.string.highlight_followings),
                        user.getNick());
            header = new HighlightHeader(this, highlight, user.getFollowingCount());
            break;
        }
        listView = (FollowingListView) findViewById(R.id.following_activity_list);
        listView.setUser(user);
        listView.setListType(type);
        listView.addHeaderView(header);
        listView.setActivity(this);
        listView.requestReload();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.dataRefresh();
    }
}