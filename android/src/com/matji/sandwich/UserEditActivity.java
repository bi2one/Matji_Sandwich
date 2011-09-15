package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.cell.UserEditCell;
import com.matji.sandwich.widget.title.HomeTitle;

public class UserEditActivity extends BaseActivity implements OnClickListener, Refreshable {

	private UserEditCell userEditCell;
	private Session session;
	
	private HomeTitle title;
	private View introWrapper;
	private TextView introText;
	private View websiteWrapper;
	private TextView websiteText;
	private View areaWrapper;
	private TextView areaText;
	
	public int setMainViewId() {
		return R.id.activity_user_edit;
	}

	@Override
	protected void init() {
		super.init();

		setContentView(R.layout.activity_user_edit);
		session = Session.getInstance(this);
		
		title = (HomeTitle) findViewById(R.id.Titlebar);
		title.setTitle(R.string.settings_account_edit_profile);
		userEditCell = (UserEditCell) findViewById(R.id.UserEditCell);
        introWrapper = findViewById(R.id.edit_intro_wrapper);
        introText = (TextView) findViewById(R.id.edit_intro_content);
        websiteWrapper = findViewById(R.id.edit_website_wrapper);
		websiteText = (TextView) findViewById(R.id.edit_website_content);
		areaWrapper = findViewById(R.id.edit_area_wrapper);
		areaText = (TextView) findViewById(R.id.edit_area_content);
		
		introWrapper.setOnClickListener(this);
		websiteWrapper.setOnClickListener(this);
		areaWrapper.setOnClickListener(this);
		
		userEditCell.addRefreshable(this);
		
		setUser(session.getCurrentUser());
	}
	
	public void setUser(User user) {
		userEditCell.setUser(user);
		introText.setText(user.getIntro());
		websiteText.setText(user.getWebsite());
		areaText.setText("한국인가?");
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    userEditCell.refresh();
	}
	
    @Override
    public void onClick(View v) {
        if (v.getId() == introWrapper.getId()) {
            onIntroWrapperClicked(v);
        } else if (v.getId() == websiteWrapper.getId()) {
            onBlogHompageWrapperClicked(v);
        } else if (v.getId() == areaWrapper.getId()) {
            onAreaWrapperClicked(v);
        }
    }

    public void onIntroWrapperClicked(View v) {
        Intent intent = new Intent(this, UserIntroEditActivity.class);
        startActivity(intent);
    }

    public void onBlogHompageWrapperClicked(View v) {
        Intent intent = new Intent(this, UserWebsiteEditActivity.class);
        startActivity(intent);
    }
    
    public void onAreaWrapperClicked(View v) {
        
    }

    @Override
    public void refresh() {
        
    }

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof User) {
            setUser((User) data);
        }
        refresh();
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {
        // TODO Auto-generated method stub
        
    }
}