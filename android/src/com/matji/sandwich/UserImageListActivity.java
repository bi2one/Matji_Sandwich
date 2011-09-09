package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.SubtitleHeader;
import com.matji.sandwich.widget.UserImageListView;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.title.UserTitle;

public class UserImageListActivity extends BaseActivity implements Refreshable {

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
        userCell = new UserCell(this, user);

        title.setIdentifiable(this);
        title.setUser(user);
        title.setFollowable(userCell);
        
        userCell.setIdentifiable(this);
        userCell.addRefreshable(title);
        userCell.addRefreshable(this);

        UserImageListView listView = (UserImageListView) findViewById(R.id.user_image_list_view);
        listView.addHeaderView(userCell);
        listView.addHeaderView(new SubtitleHeader(
                this,
                String.format(
                        MatjiConstants.string(R.string.subtitle_user_image),
                        user.getImageCount(),
                        user.getNick())));
        listView.setUser(user);
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
            this.user = (User) data;
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
}