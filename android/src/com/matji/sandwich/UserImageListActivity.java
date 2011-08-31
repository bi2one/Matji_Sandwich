package com.matji.sandwich;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.widget.UserImageListView;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.title.UserTitle;

public class UserImageListActivity extends BaseActivity {

    private User user;
    private UserTitle title;
    private UserCell userCell;

    public static final String USER = "user";

    public int setMainViewId() {
        return R.id.activity_user_image_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_image_list);

        user = (User) getIntent().getParcelableExtra(USER); 

        title = ((UserTitle) findViewById(R.id.Titlebar));
        title.setUser(user);
        title.setFollowable(userCell);
        title.setIdentifiable(this);
        userCell = new UserCell(this, user, title.getSwitchable());
        
        UserImageListView listView = (UserImageListView) findViewById(R.id.user_image_list_view);
        listView.addHeaderView(userCell);
        listView.setUser(user);
        listView.setActivity(this);
        listView.requestReload();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userCell.refresh();
    }
}