package com.matji.sandwich;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import com.matji.sandwich.adapter.CountryAdapter;
import com.matji.sandwich.base.BaseListActivity;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.HomeTitle;

public class UserAreaEditActivity extends BaseListActivity {

    private ListView listView;
    
    public int setMainViewId() {
        return R.id.activity_user_area_edit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    
    private void init() {
        setContentView(R.layout.activity_user_area_edit);

        HomeTitle title = (HomeTitle) findViewById(R.id.Titlebar);
        title.setTitle(R.string.title_area);
        
        listView = getListView();
        listView.setAdapter(new CountryAdapter(this));
        listView.setBackgroundColor(MatjiConstants.color(R.color.matji_white));
        listView.setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        listView.setDividerHeight((int) MatjiConstants.dimen(R.dimen.default_divider_size));
        listView.setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}