package com.matji.sandwich.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.matji.sandwich.PostMainActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.SectionAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.PostForSeparator;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

public class PostListView extends RequestableMListView implements View.OnClickListener {
	private PostHttpRequest postRequest;

	public PostListView(Context context, AttributeSet attrs) {
		super(context, attrs, new SectionAdapter(context), 10);
		postRequest = new PostHttpRequest(context);

		setPage(1);
		//TODO
		setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pattern_bg));
		setScrollingCacheEnabled(false);
		setDivider(null);
	}

	@Override
	public HttpRequest request() {
		postRequest.actionList(getPage(), getLimit());
		return postRequest;
	}

	@Override
	public void onListItemClick(int position) {
		Post post = (Post) getAdapterData().get(position);
		if (post.getActivityId() == 0) {
			Intent intent = new Intent(getActivity(), PostMainActivity.class);
			intent.putExtra("position", position);
			((BaseActivity) getActivity()).startActivityForResultWithMatjiData(intent, BaseActivity.POST_MAIN_ACTIVITY, post);
		}
	}

	public void onClick(View v) {
		int position = Integer.parseInt((String)v.getTag());
		Post post = ((PostForSeparator) getAdapterData().get(position)).post;

		switch(v.getId()){
		case R.id.post_adapter_thumnail: case R.id.post_adapter_nick: case R.id.activity_adapter_nick:
			gotoUserPage(post);
			break;

		case R.id.post_adapter_store_name: case R.id.activity_adapter_target:
			gotoStorePage(post);
			break;
		}	
	}

	public void delete(int position) {
		getAdapterData().remove(position);
		getMBaseAdapter().notifyDataSetChanged();
	}

	protected void gotoUserPage(Post post) {
		Intent intent = new Intent(getActivity(), UserTabActivity.class);
		((BaseActivity) getActivity()).startActivityWithMatjiData(intent, post.getUser());
	}	

	protected void gotoStorePage(Post post) {
		Intent intent = new Intent(getActivity(), StoreTabActivity.class);
		((BaseActivity) getActivity()).startActivityWithMatjiData(intent, post.getStore());
	}
}