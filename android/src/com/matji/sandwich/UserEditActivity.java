package com.matji.sandwich;

import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.cell.UserEditCell;
import com.matji.sandwich.widget.title.HomeTitle;

public class UserEditActivity extends BaseActivity {

	private UserEditCell userEditCell;
	
	private TextView introText;
	private TextView blogHompageText;
	private TextView areaText;

	public static final String USER = "EditActivity.user";
	
	public int setMainViewId() {
		return R.id.activity_user_edit;
	}

	@Override
	protected void init() {
		super.init();

		setContentView(R.layout.activity_user_edit);

		User user = getIntent().getParcelableExtra(USER);
		HomeTitle title = (HomeTitle) findViewById(R.id.Titlebar);
		title.setTitle(R.string.settings_account_edit_profile);
		userEditCell = (UserEditCell) findViewById(R.id.UserEditCell);
		introText = (TextView) findViewById(R.id.edit_intro_content);
		blogHompageText = (TextView) findViewById(R.id.edit_blog_hompage_content);
		areaText = (TextView) findViewById(R.id.edit_area_content);
		
		setUser(user);
	}
	
	public void setUser(User user) {
		userEditCell.setUser(user);
		introText.setText(user.getIntro());
		blogHompageText.setText("홈페이지");
		areaText.setText("한국인가?");
	}
}