package com.matji.sandwich;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.HighlightHeader;
import com.matji.sandwich.widget.StoreDiscoverListView;
import com.matji.sandwich.widget.title.HomeTitle;

public class StoreDiscoverListActivity extends BaseActivity {
    private User user;

    private HomeTitle title;
    private StoreDiscoverListView listView;

    public static final String USER = "StoreDiscoverListActivity.user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    
    private void init() {
        setContentView(R.layout.activity_store_discover_list);

        user = (User) getIntent().getParcelableExtra(USER);
        title = (HomeTitle) findViewById(R.id.Titlebar);
        title.setTitle(R.string.store_discover_list);

        listView = (StoreDiscoverListView) findViewById(R.id.store_discover_list);
        String highlight = String.format(
                MatjiConstants.string(R.string.highlight_find_stores),
                user.getNick());
        if (Session.getInstance(this).isCurrentUser(user))
            highlight = MatjiConstants.string(R.string.highlight_find_stores_me);
        listView.addHeaderView(new HighlightHeader(this,highlight));
        listView.setActivity(this);
        listView.setUser(user);
    }

    @Override
    protected void onResume() {
        super.onResume();

        listView.requestReload();
    }    

    @Override
    public int setMainViewId() {
        // TODO Auto-generated method stub
        return R.id.activity_store_discover;
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
}