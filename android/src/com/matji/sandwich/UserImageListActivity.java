package com.matji.sandwich;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.SubtitleHeader;
import com.matji.sandwich.widget.UserImageListView;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.title.UserTitle;

public class UserImageListActivity extends BaseActivity implements Refreshable, ActivityStartable {

	private UserTitle title;
	private UserCell userCell;
	private UserImageListView listView;

	public int setMainViewId() {
		return R.id.activity_user_image_list;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_image_list);

		title = ((UserTitle) findViewById(R.id.Titlebar));
		userCell = new UserCell(this, UserMainActivity.user);

		title.setIdentifiable(this);
		title.setUser(UserMainActivity.user);
		title.setFollowable(userCell);
		title.setTitle(R.string.title_user_image);

		userCell.setIdentifiable(this);
		userCell.addRefreshable(title);
		userCell.addRefreshable(this);

		listView = (UserImageListView) findViewById(R.id.user_image_list_view);
		listView.addHeaderView(userCell);
		listView.addHeaderView(new SubtitleHeader(
				this,
				String.format(
						MatjiConstants.string(R.string.subtitle_user_image),
						UserMainActivity.user.getImageCount(),
						UserMainActivity.user.getNick())));
		listView.setUser(UserMainActivity.user);
		listView.setActivity(this);
		listView.requestReload();
	}

	@Override
	protected void onResume() {
		super.onResume();
		userCell.refresh();
	}

	@Override
	public void refresh() {}

	@Override
	public void refresh(MatjiData data) {
		if (data instanceof User) {
			UserMainActivity.user = (User) data;
			refresh();
		}
	}

	@Override
	public void refresh(ArrayList<MatjiData> data) {}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case USER_PROFILE_TAB_ACTIVITY:
			if (resultCode == RESULT_OK) {
				UserMainActivity.user = (User) data.getParcelableExtra(UserMainActivity.USER);
				userCell.setUser(UserMainActivity.user);
				userCell.refresh();
			}
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_reload:
			listView.refresh();
			return true;
		}
		return false;
	}

	@Override
	public void activityResultDelivered(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case STORE_MAIN_ACTIVITY: case USER_MAIN_ACTIVITY: case IMAGE_SLIDER_ACTIVITY:
			if (resultCode == Activity.RESULT_OK)
				setIsFlow(true);
			break;
		}
	}
}