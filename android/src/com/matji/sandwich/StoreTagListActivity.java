package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.SubtitleHeader;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.tag.StoreTagCloudView;
import com.matji.sandwich.widget.tag.StoreTagListView;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreTagListActivity extends BaseActivity implements Refreshable {

    private StoreTitle title;
    private StoreCell storeCell;
    private SubtitleHeader tagCount;
    private Button toggleButton;
    private StoreTagCloudView tagCloudView;
    private StoreTagListView tagListView;
    private boolean isVisibleCloudView;

    public int setMainViewId() {
        return R.id.activity_store_tag_list;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_tag_list);

        title = (StoreTitle) findViewById(R.id.Titlebar);
        storeCell = (StoreCell) findViewById(R.id.StoreCell);
        tagCount = (SubtitleHeader) findViewById(R.id.store_tag_count);
        toggleButton = (Button) findViewById(R.id.store_tag_toggle_btn);
        tagCloudView = (StoreTagCloudView) findViewById(R.id.store_tag_cloud);
        tagListView = (StoreTagListView) findViewById(R.id.store_tag_all);

        title.setIdentifiable(this);
        title.setStore(StoreDetailInfoTabActivity.store);
        title.setLikeable(storeCell);

        storeCell.setStore(StoreDetailInfoTabActivity.store);
        storeCell.setClickable(false);
        storeCell.setIdentifiable(this);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);
        storeCell.addRefreshable(tagCloudView);
        storeCell.addRefreshable(tagListView);

        tagCloudView.setSpinnerContainer(getMainView());
        tagListView.setSpinnerContainer(getMainView());
        
        toggleButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                toggleTagView();
            }
        });
        visibleCloudView();
    }
    
    public void toggleTagView() {
        if (isVisibleCloudView) {
            visibleListView();
        } else {
            visibleCloudView();
        }
    }

    public void visibleCloudView() {
        toggleButton.setText(R.string.all_tag);
        tagListView.setVisibility(View.GONE);
        tagCloudView.setVisibility(View.VISIBLE);

        isVisibleCloudView = true;
    }
    
    public void visibleListView() {
        toggleButton.setText(R.string.top_tag);
        tagListView.setVisibility(View.VISIBLE);
        tagCloudView.setVisibility(View.GONE);
        
        isVisibleCloudView = false;
    }
    
    @Override
    protected void onNotFlowResume() {
        // TODO Auto-generated method stub
        super.onNotFlowResume();
        storeCell.refresh();
    }

    public void setStore(Store store) {
        StoreDetailInfoTabActivity.store = store;
    }

    @Override
    public void refresh() {
        String numTag = String.format(
                MatjiConstants.string(R.string.number_of_tag),
                StoreDetailInfoTabActivity.store.getTagCount());
        tagCount.setTitle(numTag);
    }

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof Store) {
            StoreDetailInfoTabActivity.store = (Store) data;
            refresh();
        }
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {}
}
