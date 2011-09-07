package com.matji.sandwich.widget.cell;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.Refreshable;
import com.matji.sandwich.StoreDefaultInfoActivity;
import com.matji.sandwich.StoreDetailInfoTabActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.BookmarkStarToggleView;

/**
 * UI 상의 유저 셀 (맛집 기본 정보가 보이는 곳)
 * 
 * @author mozziluv
 *
 */
public class StoreCell extends Cell implements Refreshable {
    private Store store;

    private Button likeList;
    private ViewGroup mainView;

    /**
     * 기본 생성자 (Java Code)
     * 
     * @param context
     */
    public StoreCell(Context context) {
        super(context, R.layout.cell_store);
    }

    /**
     * 기본 생성자 (XML)
     * 
     * @param context
     * @param attr
     */
    public StoreCell(Context context, AttributeSet attr) {
        super(context, attr, R.layout.cell_store);
        setId(R.id.StoreCell);
    }

    /**
     * Store 정보를 같이 전달 받을 때 사용하는 생성자.
     * 
     * @param context
     * @param store 이 Cell의 맛집데이터
     */
    public StoreCell(Context context, Store store) {
        super(context, R.layout.cell_store);
        setStore(store);
    }


    @Override
    protected void init() {
        // TODO Auto-generated method stub
        super.init();

        likeList = (Button) findViewById(R.id.cell_store_like_list_btn);
    }
    
    public void setMainView(ViewGroup mainView) {
        this.mainView = mainView;
    }
    
    /**
     * 이 StoreCell의 맛집 정보를 저장하고 해당 맛집변하지 않는 정보들을 뷰에 뿌려준다.
     * 
     * @param store
     */
    public void setStore(Store store) {
        this.store = store;

        ((TextView) findViewById(R.id.cell_store_name)).setText(store.getName());
        ((BookmarkStarToggleView) findViewById(R.id.cell_store_toggle_star)).init(store, mainView);

        refresh();
    }

    @Override
    public void refresh() {
        likeList.setText(store.getLikeCount()+"");
    }
    
    @Override
    public void refresh(MatjiData data) {
        setStore((Store) data);
        refresh();
    }
    
    @Override
    public void refresh(ArrayList<MatjiData> data) {}

    /**
     * 
     */
    @Override
    protected Intent getIntent() {
        Intent intent = new Intent(getContext(), StoreDetailInfoTabActivity.class);
        intent.putExtra(StoreDefaultInfoActivity.STORE, (Parcelable) store);
        return intent;
    }
    
    public void showLine() {
        findViewById(R.id.cell_store_line).setVisibility(View.VISIBLE);
        findViewById(R.id.cell_store_shadow).setVisibility(View.GONE);
    }
}