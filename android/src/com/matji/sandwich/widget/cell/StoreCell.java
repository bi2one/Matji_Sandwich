package com.matji.sandwich.widget.cell;

import java.util.ArrayList;

import android.app.Activity;
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
import com.matji.sandwich.StoreDetailInfoTabActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.listener.LikeListener;
import com.matji.sandwich.listener.LikeUserListListener;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.BookmarkStarToggleView;
import com.matji.sandwich.widget.title.button.LikeButton.Likeable;

/**
 * UI 상의 유저 셀 (맛집 기본 정보가 보이는 곳)
 * 
 * @author mozziluv
 *
 */
public class StoreCell extends Cell implements Likeable {
    private Store store;

    private Identifiable identifiable;
    private Session session;

    private Button likeList;
    private TextView name;
    private BookmarkStarToggleView star;

    private LikeListener likeListener;
    private ViewGroup spinnerContainer;

    private ArrayList<Refreshable> refreshables;

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

        session = Session.getInstance(getContext());
        spinnerContainer = (ViewGroup) findViewById(R.id.StoreCell);
        likeList = (Button) findViewById(R.id.cell_store_like_list_btn);
        refreshables = new ArrayList<Refreshable>();
        name = (TextView) findViewById(R.id.cell_store_name);
        star = (BookmarkStarToggleView) findViewById(R.id.cell_store_toggle_star);

    }

    /**
     * 이 StoreCell의 맛집 정보를 저장하고 해당 맛집변하지 않는 정보들을 뷰에 뿌려준다.
     * 
     * @param store
     */
    public void setStore(Store store) {
        this.store = store;
        likeListener = new LikeListener(identifiable, getContext(), store, spinnerContainer) {

            @Override
            public void postUnlikeRequest() {
                StoreCell.this.store.setLikeCount(StoreCell.this.store.getLikeCount() - 1);
                session.getCurrentUser().setLikeStoreCount(session.getCurrentUser().getLikeStoreCount() - 1);
                setStore(StoreCell.this.store);
                refresh();
            }

            @Override
            public void postLikeRequest() {
                StoreCell.this.store.setLikeCount(StoreCell.this.store.getLikeCount() + 1);
                session.getCurrentUser().setLikeStoreCount(session.getCurrentUser().getLikeStoreCount() + 1);
                setStore(StoreCell.this.store);
                refresh();
            }
        };
        name.setText(store.getName());
        likeList.setText(store.getLikeCount()+"");
        likeList.setOnClickListener(new LikeUserListListener(getContext(), store));
        star.init(store, spinnerContainer);
    }

    public void setIdentifiable(Identifiable identifiable) {
        this.identifiable = identifiable;
        likeListener.setIdentifiable(identifiable);
    }

    public void refresh() {
        for (Refreshable refreshable : refreshables) {
            refreshable.refresh(store);
        }
    }

    /**
     * 
     */
    @Override
    public void gotoDetailPage() {
        Intent intent = new Intent(getContext(), StoreDetailInfoTabActivity.class);
        intent.putExtra(StoreDetailInfoTabActivity.STORE, (Parcelable) store);
        ((Activity) getContext()).startActivityForResult(intent, BaseActivity.STORE_DETAIL_INFO_TAB_ACTIVITY);
    }

    public void showLine() {
        findViewById(R.id.cell_store_line).setVisibility(View.VISIBLE);
        findViewById(R.id.cell_store_shadow).setVisibility(View.GONE);
    }

    public void addRefreshable(Refreshable refreshable) {
        refreshables.add(refreshable);
    }

    @Override
    public void like() {
        likeListener.like();
    }

    @Override
    public boolean isLike() {
        return likeListener.isExistLike();
    }
}