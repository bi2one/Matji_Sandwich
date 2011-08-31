package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.UserProfileView;

public class UserProfileActivity extends BaseActivity {
    
    private User user;
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
	    user = (User) getIntent().getParcelableExtra(USER);
	    userProfileView = (UserProfileView) findViewById(R.id.user_profile_view);
	    userProfileView.setUser(user);
	}

	@Override
	protected void onResume() {
	    super.onResume();
	    userProfileView.refresh();
	}
}
