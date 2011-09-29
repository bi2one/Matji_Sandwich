package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.SubtitleHeader;
import com.matji.sandwich.widget.cell.StoreCell;
import com.matji.sandwich.widget.tag.StoreTagCloudView;
import com.matji.sandwich.widget.title.StoreTitle;

public class StoreTagCloudActivity extends BaseActivity implements Refreshable {

    private StoreTitle title;
    private StoreCell storeCell;
    private SubtitleHeader tagCount;
    private StoreTagCloudView tagCloudView;

    public int setMainViewId() {
        return R.id.activity_store_tag_cloud;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_tag_cloud);

        title = (StoreTitle) findViewById(R.id.Titlebar);
        storeCell = (StoreCell) findViewById(R.id.StoreCell);
        tagCloudView = (StoreTagCloudView) findViewById(R.id.store_tag_cloud);

        tagCount = (SubtitleHeader) findViewById(R.id.store_tag_count);

        title.setIdentifiable(this);
        title.setStore(StoreDetailInfoTabActivity.store);
        title.setLikeable(storeCell);

        storeCell.setStore(StoreDetailInfoTabActivity.store);
        storeCell.setClickable(false);
        storeCell.setIdentifiable(this);
        storeCell.addRefreshable(this);
        storeCell.addRefreshable(title);
        storeCell.addRefreshable(tagCloudView);
        
        tagCloudView.setSpinnerContainer(getMainView());
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
