package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.FollowingActivity.FollowingListType;
import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.request.FollowingHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;

public class FollowingListView extends UserListView implements View.OnClickListener {
	private HttpRequest request;
	private Context context;
	private int user_id;
	private FollowingListType listType;
	
	public FollowingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		request = new FollowingHttpRequest(context);

		setPage(1);
	}
	
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
	public void setListType(FollowingListType type) {
		this.listType = type;
	}

	public HttpRequest request() {
		switch(listType) {
		case FOLLOWER:
			((FollowingHttpRequest) request).actionFollowerList(user_id, getPage(), getLimit(), true);
			break;
		case FOLLOWING:
			((FollowingHttpRequest) request).actionFollowingList(user_id, getPage(), getLimit(), true);
			break;
		}
		return request;
	}

	public void onListItemClick(int position) {
		User user = (User) getAdapterData().get(position);
		Intent intent = new Intent(getActivity(), UserTabActivity.class);

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
}