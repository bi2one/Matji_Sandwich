package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.PostMainActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.PostAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

public class PostListView extends RequestableMListView implements View.OnClickListener {
	private HttpRequest request;

	public PostListView(Context context, AttributeSet attrs) {
		super(context, attrs, new PostAdapter(context), 10);
		request = new PostHttpRequest(context);
		setPage(1);
	}

	public HttpRequest request() {
		((PostHttpRequest) request).actionList(getPage(), getLimit());
		return request;
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		super.requestCallBack(tag, data);
	}

	public void requestExceptionCallBack(int tag, MatjiException e) {
		super.requestExceptionCallBack(tag, e);
	}

	public void onListItemClick(int position) {
//		MainTabActivity tabAct = MainTabActivity.class.cast(getActivity().getParent());
//		tabAct.getTabHost().setCurrentTab(1);

		Post post = (Post) getAdapterData().get(position);
		Intent intent = new Intent(getActivity(), PostMainActivity.class);

		intent.putExtra("post", (Parcelable) post);
		getActivity().startActivity(intent);
	}


	public void onClick(View v) {
		switch(v.getId()){
		case R.id.thumnail: case R.id.post_adapter_nick:
			gotoUserPage(Integer.parseInt((String)v.getTag()));
			break;

		case R.id.post_adapter_store_name:
			gotoStorePage(Integer.parseInt((String)v.getTag()));
			break;
		}	
	}
	
	protected void gotoUserPage(int position) {
		Post post = (Post)getAdapterData().get(position);
		Intent intent = new Intent(getActivity(), UserTabActivity.class);

		intent.putExtra("user", (Parcelable)post.getUser());
		getActivity().startActivity(intent);
	}	
	
	protected void gotoStorePage(int position) {
		Post post = (Post)getAdapterData().get(position);
		Intent intent = new Intent(getActivity(), StoreTabActivity.class);

		intent.putExtra("store", (Parcelable)post.getStore());
		getActivity().startActivity(intent);
	}
}
