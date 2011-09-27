package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.session.Session.LoginListener;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.SubtitleHeader;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.tag.UserTagCloudView;
import com.matji.sandwich.widget.title.UserTitle;

public class UserTagCloudActivity extends BaseActivity implements Refreshable, LoginListener {

	private Session session;
    private boolean isMainTabActivity;
    private UserTitle title;
    private UserCell userCell;
    private SubtitleHeader tagCount;
    private UserTagCloudView tagCloudView;

    public static final String IS_MAIN_TAB_ACTIVITY = "UserTagActivity.is_main_tab_activity";

    public int setMainViewId() {
        return R.id.activity_user_tag_cloud;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tag_cloud);
        
        session = Session.getInstance(this);
        session.addLoginListener(this);
        isMainTabActivity = getIntent().getBooleanExtra(IS_MAIN_TAB_ACTIVITY, false);

        title = (UserTitle) findViewById(R.id.Titlebar);
        userCell = (UserCell) findViewById(R.id.UserCell);
        tagCount = (SubtitleHeader) findViewById(R.id.user_tag_count);
        tagCloudView = (UserTagCloudView) findViewById(R.id.user_tag_cloud);

        if (!isMainTabActivity) {
            title.setIdentifiable(this);
            title.setUser(UserProfileTabActivity.user);
            title.setFollowable(userCell);
        }

        userCell.setUser(UserProfileTabActivity.user);
        userCell.setClickable(false);
        userCell.setIdentifiable(this);
        userCell.addRefreshable(this);
        if (!isMainTabActivity) userCell.addRefreshable(title);
        else dismissTitle();
        userCell.addRefreshable(tagCloudView);

        tagCloudView.setSpinnerContainer(getMainView());

    }

    public void showTitle() {
        title.setVisibility(View.VISIBLE);
    }

    public void dismissTitle() {
        title.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        userCell.refresh();
    }

    @Override
    public void refresh() {
        String numTag = String.format(
                MatjiConstants.string(R.string.number_of_tag),
                UserProfileTabActivity.user.getTagCount());

        tagCount.setTitle(numTag);
    }

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof User) {
            UserProfileTabActivity.user = (User) data;
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
    

	@Override
	public void preLogin() {}

	@Override
	public void postLogin() {
		if (isMainTabActivity) {
			userCell.setUser(session.getCurrentUser());
			userCell.refresh();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // TODO Auto-generated method stub
	    super.onActivityResult(requestCode, resultCode, data);
	    switch (requestCode) {
        case USER_PROFILE_TAB_ACTIVITY:
            if (resultCode == RESULT_OK) {
                UserMainActivity.user = (User) data.getParcelableExtra(UserMainActivity.USER);
                userCell.setUser(UserMainActivity.user);
                userCell.refresh();
            }
            break;
	    }
	}
}
