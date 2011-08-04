package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.FoodAdapter;
import com.matji.sandwich.base.Base;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.LikeHttpRequest;
import com.matji.sandwich.http.request.StoreFoodHttpRequest;

public class StoreMenuListView extends RequestableMListView implements OnClickListener {
	private Context context;
	private HttpRequest request;
	private DBProvider dbProvider;
	private Store store;
	private View currentClickedView;
	private StoreFood currentClickedStoreFood;

	private static final int LIKE_REQUEST = 11;
	private static final int UN_LIKE_REQUEST = 12;

	public StoreMenuListView(Context context, AttributeSet attrs) {
		super(context, attrs, new FoodAdapter(context), 10);
		this.context = context;

		dbProvider = DBProvider.getInstance(context);

		setPage(1);
	}
	
	public void setStore(Store store) {
		this.store = store;
	}	
	
	public void addMenu(StoreFood food) {
		for (MatjiData data : getAdapterData()) {
			if (food.getFood().getName().equals(((StoreFood) data).getFood().getName())) {
				Toast.makeText(context, R.string.store_menu_already_exist_menu, Toast.LENGTH_SHORT).show();
				return;
			}
		}

		getAdapterData().add(0, food);

		if (getAdapterData().size() > getLimit() * (getPage() - 1)) {
			requestSetOn();
			getAdapterData().remove(getAdapterData().size() - 1);
		}
		getMBaseAdapter().notifyDataSetChanged();
	}

	public HttpRequest request() {
		if (request == null || !(request instanceof StoreFoodHttpRequest)) {
			request = new StoreFoodHttpRequest(context);
		}
		((StoreFoodHttpRequest) request).actionList(store.getId(), getPage(), getLimit());

		return request;
	}

	private void likeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(context);
		}

		((LikeHttpRequest) request).actionFoodLike(currentClickedStoreFood.getId());
		getHttpRequestManager().request(getActivity(), request, LIKE_REQUEST, this);
	}

	private void unlikeRequest() {
		if (request == null || !(request instanceof LikeHttpRequest)) {
			request = new LikeHttpRequest(context);
		}

		((LikeHttpRequest) request).actionFoodUnLike(currentClickedStoreFood.getId());
		getHttpRequestManager().request(getActivity(), request, UN_LIKE_REQUEST, this);
	}	
	
	private void postLikeRequest() {
		Like like = new Like();
		like.setForeignKey(currentClickedStoreFood.getId());
		like.setObject("StoreFood");

		dbProvider.insertLike(like);
		currentClickedStoreFood.setLikeCount(currentClickedStoreFood.getLikeCount() + 1);
		currentClickedView.setClickable(true);
	}

	private void postUnLikeRequest() {
		dbProvider.deleteLike(currentClickedStoreFood.getId(), "StoreFood");
		currentClickedStoreFood.setLikeCount(currentClickedStoreFood.getLikeCount() - 1);
		currentClickedView.setClickable(true);
	}

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		if (tag == LIKE_REQUEST || tag == UN_LIKE_REQUEST) {
			switch (tag) {
			case LIKE_REQUEST:
				postLikeRequest();
				break;
			case UN_LIKE_REQUEST:
				postUnLikeRequest();
				break;
			}
			
			getMBaseAdapter().notifyDataSetChanged();
		}

		else {
			super.requestCallBack(tag, data);
		}
	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		if (currentClickedView != null) currentClickedView.setClickable(true);
		super.requestExceptionCallBack(tag, e);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_adapter_like_btn:
			if (((Base) getActivity()).loginRequired()) {
				if (!getHttpRequestManager().isRunning(getActivity())) {
					v.setClickable(false);
					currentClickedView = v;
					int position = Integer.parseInt((String) v.getTag());
					currentClickedStoreFood = ((StoreFood) getAdapterData().get(position));
					
					if (dbProvider.isExistLike(currentClickedStoreFood.getId(), "StoreFood")) {
						// api request
						unlikeRequest();
					}else {
						// api request
						likeRequest();
					}
				}
			}
		}
	}
	
	public void onListItemClick(int position) {}
}