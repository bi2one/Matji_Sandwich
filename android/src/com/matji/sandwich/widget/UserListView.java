package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.UserMainActivity;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.UserAdapter;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.UserHttpRequest;

public class UserListView extends RequestableMListView implements View.OnClickListener {
	private HttpRequest request;
	private int nowPosition;

	public UserListView(Context context, AttributeSet attrs) {
		super(context, attrs, new UserAdapter(context), 10);
		request = new UserHttpRequest(context);

		setPage(1);
	}

	public HttpRequest request() {
		((UserHttpRequest) request).actionList(getPage(), getLimit());
		return request;
	}

	public void onListItemClick(int position) {
		nowPosition = position;
		User user = (User) getAdapterData().get(nowPosition);
		Intent intent = new Intent(getActivity(), UserMainActivity.class);

		intent.putExtra("user", (Parcelable) user);
		getActivity().startActivity(intent);
	}


	public void onClick(View v) {
		//		int position = Integer.parseInt((String)v.getTag());
		//		User user = (User) getAdapterData().get(position);
		//		Post recentPost = user.getPosts().get(0);
		//
		//		switch(v.getId()){
		//		case R.id.thumnail: case R.id.post_adapter_nick:
		//			gotoUserPage(recentPost);
		//			break;
		//
		//		case R.id.post_adapter_store_name:
		//			gotoStorePage(recentPost);
		//			break;
		//		}	
	}

	protected void gotoUserPage(Post post) {
		Intent intent = new Intent(getActivity(), UserTabActivity.class);

		intent.putExtra("user", (Parcelable)post.getUser());
		getActivity().startActivity(intent);
	}	

	protected void gotoStorePage(Post post) {
		Intent intent = new Intent(getActivity(), StoreTabActivity.class);

		intent.putExtra("store", (Parcelable)post.getStore());
		getActivity().startActivity(intent);
	}
	
	public void syncStore(Store store) {
		getAdapterData().set(nowPosition, store);
	}
}