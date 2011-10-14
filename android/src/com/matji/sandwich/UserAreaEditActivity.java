package com.matji.sandwich;

import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;

import com.matji.sandwich.adapter.SimpleAdapter;
import com.matji.sandwich.base.BaseListActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.UserHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.HomeTitle;

public class UserAreaEditActivity extends BaseListActivity implements Requestable {

    private ListView listView;
    private HttpRequest request;
    private HttpRequestManager manager;
    
    public int setMainViewId() {
        return R.id.activity_user_area_edit;
    }

    private String[] names = MatjiConstants.stringArray(R.array.country_names);
    private String[] codes = MatjiConstants.stringArray(R.array.country_codes);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    
    private void init() {
        setContentView(R.layout.activity_user_area_edit);
        
        manager = HttpRequestManager.getInstance();

        HomeTitle title = (HomeTitle) findViewById(R.id.Titlebar);
        title.setTitle(R.string.title_area);
        SimpleAdapter adapter = new SimpleAdapter(this) {
            
            @Override
            protected String getText(int position) {
                return names[position];
            }
        };
        adapter.alignText(Gravity.CENTER_VERTICAL);
        adapter.setData(new ArrayList<String>(Arrays.asList(names)));
        listView = getListView();
        listView.setAdapter(adapter);
        listView.setBackgroundColor(MatjiConstants.color(R.color.matji_white));
        listView.setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        listView.setDividerHeight((int) MatjiConstants.dimen(R.dimen.default_divider_size));
        listView.setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(android.R.color.transparent);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        manager.request(this, getMainView(), updateCountryRequest(codes[position]), HttpRequestManager.USER_UPDATE_REQUEST, this);
    }

    public HttpRequest updateCountryRequest(String country_code) {
        if (request == null || !(request instanceof UserHttpRequest)) {
            request = new UserHttpRequest(this);
        }
        
        ((UserHttpRequest) request).actionUpdateCountry(country_code);
        
        return request;
    }
    
    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) {
        case HttpRequestManager.USER_UPDATE_REQUEST:
            User user = (User) data.get(0);
            if (user != null);
            Session.getInstance(this).getCurrentUser().setCountryCode(user.getCountryCode());
            finish();
            break;
        }
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(this);
    }
}