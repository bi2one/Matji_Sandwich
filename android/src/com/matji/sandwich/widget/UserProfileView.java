package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.FollowingActivity;
import com.matji.sandwich.FollowingActivity.FollowingListType;
import com.matji.sandwich.R;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.cell.UserCell;

public class UserProfileView extends RelativeLayout {
    private User user;
    private UserCell userCell;
    
    private TextView intro;
    private TextView blog;
    private View followingCountView;
    private TextView followingCount;
    private View followerCountView;
    private TextView followerCount;
    private TextView findCount;
    private TextView bookmarkCount;
    
    public UserProfileView(Context context) {
        super(context);
        init();
    }

    public UserProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.user_profile, this);

        userCell = ((UserCell) findViewById(R.id.UserCell));
        userCell.setClickable(false);
        intro = (TextView) findViewById(R.id.user_profile_intro);
        blog = (TextView) findViewById(R.id.user_profile_blog);
        followingCountView = findViewById(R.id.user_profile_following);
        followingCount = (TextView) findViewById(R.id.user_profile_following_count);
        followerCountView = findViewById(R.id.user_profile_follower);
        followerCount = (TextView) findViewById(R.id.user_profile_follower_count);
        findCount = (TextView) findViewById(R.id.user_profile_find_store_count);
        bookmarkCount = (TextView) findViewById(R.id.user_profile_bookmark_store_count);
        
        followingCountView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                onFollowingButtonClicked(v);
            }
        });
        
        followerCountView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                onFollowerButtonClicked(v);
            }
        });
    }
    
    public void setUser(User user) {
        this.user = user;
        refresh();
    }
    
    public void refresh() {
        userCell.setUser(user);
        intro.setText(user.getIntro());
        blog.setText("블로그는??????????");
        followingCount.setText(user.getFollowingCount()+"");
        followerCount.setText(user.getFollowerCount()+"");
        findCount.setText(user.getDiscoverStoreCount()+"");
        bookmarkCount.setText(user.getBookmarkStoreCount()+"");
    }
    
    public void onFollowingButtonClicked(View v) {
        Intent intent = new Intent(getContext(), FollowingActivity.class);
        intent.putExtra(FollowingActivity.USER, (Parcelable) user);
        intent.putExtra(FollowingActivity.TYPE, FollowingListType.FOLLOWING);
        getContext().startActivity(intent);
    }

    public void onFollowerButtonClicked(View v) {
        Intent intent = new Intent(getContext(), FollowingActivity.class);
        intent.putExtra(FollowingActivity.USER, (Parcelable) user);
        intent.putExtra(FollowingActivity.TYPE, FollowingListType.FOLLOWER);
        getContext().startActivity(intent);
    }
}