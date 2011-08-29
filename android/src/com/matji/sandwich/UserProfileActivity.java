package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.matji.sandwich.FollowingActivity.FollowingListType;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.cell.UserCell;

public class UserProfileActivity extends BaseActivity {
	private User user;
	private UserCell userCell;
	
	public final static String USER = "user";
	
	private TextView intro;
	private TextView blog;
	private TextView followingCount;
    private TextView followerCount;
    private TextView findCount;
    private TextView bookmarkCount;

    public int setMainViewId() {
	return R.id.activity_user_profile;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void init() {
		setContentView(R.layout.activity_user_profile);
		
		user = (User) getIntent().getParcelableExtra(USER);
		userCell = ((UserCell) findViewById(R.id.UserCell));
		userCell.setUser(user);
		userCell.setClickable(false);
		intro = (TextView) findViewById(R.id.user_profile_intro);
		blog = (TextView) findViewById(R.id.user_profile_blog);
		followingCount = (TextView) findViewById(R.id.user_profile_following_count);
        followerCount = (TextView) findViewById(R.id.user_profile_follower_count);
        findCount = (TextView) findViewById(R.id.user_profile_find_store_count);
        bookmarkCount = (TextView) findViewById(R.id.user_profile_bookmark_store_count);

        refresh();
	}

	@Override
	protected void onResume() {
	    super.onResume();
	    refresh();
	}
	
	final public void refresh() {
	    userCell.refresh();
	    intro.setText(user.getIntro());
	    blog.setText("블로그는??????????");
	    followingCount.setText(user.getFollowingCount()+"");
	    followerCount.setText(user.getFollowerCount()+"");
	    findCount.setText(user.getDiscoverStoreCount()+"");
	    bookmarkCount.setText(user.getBookmarkStoreCount()+"");
	}
	
    public void onFollowingButtonClicked(View v) {
        Intent intent = new Intent(this, FollowingActivity.class);
        intent.putExtra(FollowingActivity.USER, (Parcelable) user);
        intent.putExtra(FollowingActivity.TYPE, FollowingListType.FOLLOWING);
        startActivity(intent);
    }

    public void onFollowerButtonClicked(View v) {
        Intent intent = new Intent(this, FollowingActivity.class);
        intent.putExtra(FollowingActivity.USER, (Parcelable) user);
        intent.putExtra(FollowingActivity.TYPE, FollowingListType.FOLLOWER);
        startActivity(intent);
    }
}
