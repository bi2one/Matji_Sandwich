package com.matji.sandwich.widget;

import com.matji.sandwich.PostMainActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.UserMainActivity;
import com.matji.sandwich.adapter.SectionedPostAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

/**
 * View displaying the list with sectioned header.
 * 
 * @author mozziluv
 * 
 */
public class PostListView extends RequestableMListView implements View.OnClickListener {
	private HttpRequest request;
	
	public PostListView(Context context, AttributeSet attr) {
		super(context, attr, new SectionedPostAdapter(context), 10);
		init();
	}	

	protected void init() {
		setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.pattern_bg));
		setDivider(null);
		setFadingEdgeLength(getResources().getDimensionPixelSize(R.dimen.fade_edge_length));
		setCacheColorHint(Color.TRANSPARENT);
		setSelector(android.R.color.transparent);
	}

	@Override
	public HttpRequest request() {
		if (request == null || !(request instanceof PostHttpRequest)) {
			request = new PostHttpRequest(getContext());
		}
		((PostHttpRequest) request).actionListWithAttachFiles(getPage(), getLimit());
		return request;
	}

	public void onClick(View v) {
		int position = Integer.parseInt((String) v.getTag());
		Post post = (Post) getAdapterData().get(position);

		switch (v.getId()) {
		case R.id.profile:
		case R.id.row_post_nick:
			gotoUserPage(post);
			break;
		case R.id.row_post_store_name:
		case R.id.activity_adapter_target:
			gotoStorePage(post);
			break;
		}
	}
	
	
	protected void gotoUserPage(Post post) {
		Intent intent = new Intent(getActivity(), UserMainActivity.class);
		intent.putExtra(UserMainActivity.USER, (Parcelable) post.getUser());
		((BaseActivity) getActivity()).startActivity(intent);
	}

	protected void gotoStorePage(Post post) {
		Intent intent = new Intent(getActivity(), StoreMainActivity.class);
		intent.putExtra(StoreMainActivity.STORE, (Parcelable) post.getStore());
		((BaseActivity) getActivity()).startActivity(intent);
	}

	@Override
	public void onListItemClick(int position) {
		Post post = (Post) getAdapterData().get(position);
		if (post.getActivityId() == 0) {
			Intent intent = new Intent(getActivity(), PostMainActivity.class);
			intent.putExtra(PostMainActivity.POSITION, position);
			intent.putExtra(PostMainActivity.POST, (Parcelable) post);
			((BaseActivity) getActivity()).startActivityForResult(intent, BaseActivity.POST_MAIN_ACTIVITY);
		}
	}

}
