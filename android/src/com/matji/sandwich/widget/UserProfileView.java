package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.FollowingActivity;
import com.matji.sandwich.FollowingActivity.FollowingListType;
import com.matji.sandwich.R;
import com.matji.sandwich.Refreshable;
import com.matji.sandwich.StoreBookmarkListActivity;
import com.matji.sandwich.StoreDiscoverListActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.cell.UserCell;

public class UserProfileView extends RelativeLayout implements OnClickListener, Refreshable {
    private User user;

    private Session session;

    private UserCell userCell;
    private TextView introText;
    private TextView websiteText;
    private View followingCountView;
    private TextView followingCount;
    private View followerCountView;
    private TextView followerCount;
    private View findCountView; 
    private TextView findTitle;
    private TextView findCount;
    private View bookmarkDivider;
    private View bookmarkCountView; 
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
        // inflater.inflate(R.layout.user_profile_bak, this);
        inflater.inflate(R.layout.user_profile, this);

        session = Session.getInstance(getContext());

        userCell = ((UserCell) findViewById(R.id.UserCell));
        userCell.setClickable(false);
        introText = (TextView) findViewById(R.id.user_profile_intro);
        websiteText = (TextView) findViewById(R.id.user_profile_blog);
        followingCountView = findViewById(R.id.user_profile_following);
        followingCount = (TextView) findViewById(R.id.user_profile_following_count);
        followerCountView = findViewById(R.id.user_profile_follower);
        followerCount = (TextView) findViewById(R.id.user_profile_follower_count);
        findCountView = findViewById(R.id.user_profile_find_store);
        findTitle = (TextView) findViewById(R.id.user_profile_find_store_title);
        findCount = (TextView) findViewById(R.id.user_profile_find_store_count);
        bookmarkDivider = findViewById(R.id.user_profile_store_info_line);
        bookmarkCountView = findViewById(R.id.user_profile_bookmark);
        bookmarkCount = (TextView) findViewById(R.id.user_profile_bookmark_store_count);
        
        followingCountView.setOnClickListener(this);
        followerCountView.setOnClickListener(this);
        findCountView.setOnClickListener(this);
        bookmarkCountView.setOnClickListener(this);


	// bak
        // userCell = ((UserCell) findViewById(R.id.UserCell));
        // userCell.setClickable(false);
        // intro = (TextView) findViewById(R.id.user_profile_intro);
        // blog = (TextView) findViewById(R.id.user_profile_blog);
        // editInfo = (TextView) findViewById(R.id.user_profile_edit_info);
        // followingCount = (TextView) findViewById(R.id.user_profile_following_count);
        // followerCount = (TextView) findViewById(R.id.user_profile_follower_count);
        // findTitle = (TextView) findViewById(R.id.user_profile_find_store_title);
        // findCount = (TextView) findViewById(R.id.user_profile_find_store_count);
        // bookmarkCount = (TextView) findViewById(R.id.user_profile_bookmark_store_count);
    }

    public void setUser(User user) {
        this.user = user;
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

    public void onFindStoreButtonClicked(View v) {
        Intent intent = new Intent(getContext(), StoreDiscoverListActivity.class);
        intent.putExtra(StoreDiscoverListActivity.USER, (Parcelable) user);
        getContext().startActivity(intent);
    }

    public void onBookmarkStoreButtonClicked(View v) {
        Intent intent = new Intent(getContext(), StoreBookmarkListActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == followingCountView.getId()) {
            onFollowingButtonClicked(v);
        } else if (v.getId() == followerCountView.getId()) {
            onFollowerButtonClicked(v);
        } else if (v.getId() == findCountView.getId()) {
            onFindStoreButtonClicked(v);
        } else if (v.getId() == bookmarkCountView.getId()) {
            onBookmarkStoreButtonClicked(v);
        } 
    }
    
    @Override
    public void refresh() {
        String intro = user.getIntro();
        if (intro.equals("")) {
            intro = MatjiConstants.string(R.string.default_string_not_exist_intro);
        }
        introText.setText(intro);
        
        String website = user.getWebsite();
        if (website.equals("")) {
            website = MatjiConstants.string(R.string.default_string_not_exist_website);
        }
        websiteText.setText(website);
        followingCount.setText(user.getFollowingCount() + "");
        followerCount.setText(user.getFollowerCount()+"");
        findCount.setText(user.getDiscoverStoreCount()+"");
        bookmarkCount.setText(user.getBookmarkStoreCount()+"");

        if (session.isLogin() && user.getId() == session.getCurrentUser().getId()) {
            findTitle.setText(R.string.user_profile_my_find_store);
            bookmarkDivider.setVisibility(View.VISIBLE);
            bookmarkCountView.setVisibility(View.VISIBLE);
        } else {
            findTitle.setText(R.string.user_profile_find_store);
            bookmarkDivider.setVisibility(View.GONE);
            bookmarkCountView.setVisibility(View.GONE);
        }
    }

    @Override
    public void refresh(MatjiData data) {
        // TODO Auto-generated method stub
        if (data instanceof User) {
            user = (User) data;
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {
        // TODO Auto-generated method stub
        
    }
}