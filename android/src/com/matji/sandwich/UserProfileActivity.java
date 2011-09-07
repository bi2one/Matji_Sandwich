package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.UserProfileView;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.title.UserTitle;

public class UserProfileActivity extends BaseActivity {
    
    private User user;
    private UserTitle title;
    private UserCell userCell;    
    private UserProfileView userProfileView;
    
    public static final String USER = "user";
    
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

	    title = (UserTitle) findViewById(R.id.Titlebar);
	    user = (User) getIntent().getParcelableExtra(USER);
	    userCell = (UserCell) findViewById(R.id.UserCell);
	    userProfileView = (UserProfileView) findViewById(R.id.user_profile_view);

        title.setUser(user);
        title.setIdentifiable(this);
        title.setFollowable(userCell);
	    userCell.setSwitchable(title.getSwitchable());
	    userProfileView.setUser(user);
	}

	@Override
	protected void onResume() {
	    super.onResume();
	    userProfileView.refresh();
	}
}
