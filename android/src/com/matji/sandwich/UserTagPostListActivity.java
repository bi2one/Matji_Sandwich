package com.matji.sandwich;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Tag;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.PostHttpRequest.TagByType;
import com.matji.sandwich.widget.TagPostListView;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.title.UserTitle;

public class UserTagPostListActivity extends BaseActivity implements Refreshable {
    
    private UserTitle title;
    private TagPostListView listView;
    private UserCell userCell;
    private Tag tag;
    public static final String TAG = "UserTagPostListActivity.tag";

    public int setMainViewId() {
        return R.id.activity_user_tag_post;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tag_post);

        tag = getIntent().getParcelableExtra(TAG);
        
        title = (UserTitle) findViewById(R.id.Titlebar);
        userCell = new UserCell(this, UserProfileTabActivity.user);
        listView = (TagPostListView) findViewById(R.id.user_tag_post_list_view);
        
        title.setIdentifiable(this);
        title.setUser(UserProfileTabActivity.user);
        title.setFollowable(userCell);

        userCell.setIdentifiable(this);
        userCell.addRefreshable(this);
        userCell.addRefreshable(title);

        listView.addHeaderView(userCell);
        listView.setTag(tag);
        listView.setType(TagByType.USER);
        listView.setActivity(this);
        listView.requestReload();
        
        listView.tagDialog.setOnClickListener(new SimpleAlertDialog.OnClickListener() {
			@Override
			public void onConfirmClick(SimpleDialog dialog) {
				finish();
			}
		});
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        listView.dataRefresh();
        userCell.refresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case POST_MAIN_ACTIVITY:
            if (resultCode == RESULT_OK) {
                ArrayList<MatjiData> posts = data.getParcelableArrayListExtra(PostMainActivity.POSTS);
                listView.setPosts(posts);
                setIsFlow(true);
            }
            break;
        case USER_PROFILE_TAB_ACTIVITY:
            if (resultCode == RESULT_OK) {
                UserProfileTabActivity.user = (User) data.getParcelableExtra(UserProfileTabActivity.USER);
                userCell.setUser(UserProfileTabActivity.user);
                userCell.refresh();
            }
            break;
        case STORE_MAIN_ACTIVITY: case USER_MAIN_ACTIVITY: case IMAGE_SLIDER_ACTIVITY:            
            if (resultCode == Activity.RESULT_OK) {
                setIsFlow(true);
            }
            break;
        }
    }
    
    @Override
    public void setIsFlow(boolean isFlow) {
        if (getParent() instanceof BaseTabActivity) {
            ((BaseTabActivity) getParent()).setIsFlow(true);
        }
        super.setIsFlow(isFlow);
    }

    @Override
    public void refresh() {}

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof User){
            UserProfileTabActivity.user = (User) data;
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
    
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
}