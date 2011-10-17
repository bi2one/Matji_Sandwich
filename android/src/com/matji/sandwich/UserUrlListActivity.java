package com.matji.sandwich;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.UrlHttpRequest.UrlType;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.SubtitleHeader;
import com.matji.sandwich.widget.UrlListView;
import com.matji.sandwich.widget.cell.UserCell;
import com.matji.sandwich.widget.title.UserTitle;

public class UserUrlListActivity extends BaseActivity implements Refreshable {

    private UserTitle title;
    private UserCell userCell;
    private UrlListView listView;

    //    public static final String STORE = "StoreUrlListActivity.store";

    public int setMainViewId() {
        return R.id.activity_user_url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_url);

        title = (UserTitle) findViewById(R.id.Titlebar);
        userCell = new UserCell(this, UserMainActivity.user);

        title.setIdentifiable(this);
        title.setUser(UserMainActivity.user);
        title.setFollowable(userCell);
        title.setTitle(R.string.title_user_url);

        userCell.setIdentifiable(this);
        userCell.addRefreshable(this);
        userCell.addRefreshable(title);

        listView = (UrlListView) findViewById(R.id.user_url_list);
        listView.setUrlType(UrlType.USER);
        listView.setId(UserMainActivity.user.getId());
        listView.addHeaderView(userCell);
        listView.addHeaderView(
                new SubtitleHeader(
                        this, 
                        String.format(
                                MatjiConstants.string(R.string.subtitle_url),
                                UserMainActivity.user.getUrlCount())));
        listView.setActivity(this);
        listView.requestReload();    
    }

    @Override
    protected void onNotFlowResume() {
        super.onNotFlowResume();
        userCell.refresh();
    }

    @Override
    public void refresh() {
        listView.refresh();
    }

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof Store) {
            UserMainActivity.user= (User) data;
            listView.setId(UserMainActivity.user.getId());
            refresh();
        }

    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case STORE_DETAIL_INFO_TAB_ACTIVITY:
            if (resultCode == Activity.RESULT_OK) {
                UserMainActivity.user = (User) data.getParcelableExtra(UserMainActivity.USER);
                userCell.setUser(UserMainActivity.user);
                userCell.refresh();
                setIsFlow(true);
            }
            break;
        }        
    }
}