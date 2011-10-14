package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.TitleContainer;

public class ServiceGuideActivity extends BaseActivity implements OnClickListener {
    
    private TextView tvFindStore;
    private TextView tvManageStore;
    private TextView tvWritePost;
    private TextView tvMenuNTag;
    private TextView tvSNSFunction;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }
    
    private void init() {
        setContentView(R.layout.activity_service_guide);
        setTitle();
        findViews();
        setListeners();
    }

    private void setTitle() {
        ((TitleContainer) findViewById(R.id.Titlebar)).setTitle(R.string.title_service_guide);
    }
    
    private void findViews() {
        tvFindStore = (TextView) findViewById(R.id.service_guide_find_store);
        tvManageStore = (TextView) findViewById(R.id.service_guide_manage_store);
        tvWritePost = (TextView) findViewById(R.id.service_guide_write_story);
        tvMenuNTag = (TextView) findViewById(R.id.service_guide_menu_n_tag);
        tvSNSFunction = (TextView) findViewById(R.id.service_guide_sns_function);
    }    
    
    private void setListeners() {
        tvFindStore.setOnClickListener(this);
        tvManageStore.setOnClickListener(this);
        tvWritePost.setOnClickListener(this);
        tvMenuNTag.setOnClickListener(this);
        tvSNSFunction.setOnClickListener(this);
    }
    
    @Override
    public int setMainViewId() {
        // TODO Auto-generated method stub
        return R.id.activity_service_guide;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        
        if (vId == tvFindStore.getId()) {
            startWebview(R.string.service_guide_find_store_url, R.string.service_guide_find_store);
        } else if (vId == tvManageStore.getId()) {
            startWebview(R.string.service_guide_manage_store_url, R.string.service_guide_manage_store);
        } else if (vId == tvWritePost.getId()) {
            startWebview(R.string.service_guide_write_story_url, R.string.service_guide_write_story);
        } else if (vId == tvMenuNTag.getId()) {
            startWebview(R.string.service_guide_menu_n_tag_url, R.string.service_guide_menu_n_tag);
        } else if (vId == tvSNSFunction.getId()) {
            startWebview(R.string.service_guide_sns_function_url, R.string.service_guide_sns_function);
        }
    }

    public void startWebview(int actionRef, int titleRef) {
        Intent intent = new Intent(this, SimpleWebviewActivity.class);
        intent.putExtra(SimpleWebviewActivity.URL, MatjiConstants.string(R.string.service_guide_domain_url)+MatjiConstants.string(actionRef));
        intent.putExtra(SimpleWebviewActivity.TITLE, MatjiConstants.string(titleRef));
        startActivity(intent);
    }
}
