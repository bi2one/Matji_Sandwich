package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.SubtitleHeader;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.tag.UserTagCloudView;
import com.matji.sandwich.widget.title.UserTitle;

public class UserTagActivity extends BaseActivity implements Refreshable {

    private boolean isMainTabActivity;
    private User user;
    private UserTitle title;
    private UserCell userCell;
    private SubtitleHeader tagCount;
    private UserTagCloudView tagCloudView;

    public static final String USER = "UserTagActivity.user";
    public static final String IS_MAIN_TAB_ACTIVITY = "UserTagActivity.is_main_tab_activity";

    public int setMainViewId() {
        return R.id.activity_user_tag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tag);
        isMainTabActivity = getIntent().getBooleanExtra(IS_MAIN_TAB_ACTIVITY, false);
        user = (User) getIntent().getParcelableExtra(USER);

        title = (UserTitle) findViewById(R.id.Titlebar);
        userCell = (UserCell) findViewById(R.id.UserCell);
        tagCount = (SubtitleHeader) findViewById(R.id.user_tag_count);
        tagCloudView = (UserTagCloudView) findViewById(R.id.user_tag_cloud);

        if (!isMainTabActivity) {
            title.setIdentifiable(this);
            title.setUser(user);
            title.setFollowable(userCell);
        }

        userCell.setUser(user);
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
                user.getTagCount());

        tagCount.setTitle(numTag);
    }

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
