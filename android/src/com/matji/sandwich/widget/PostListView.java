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
		commonInitialisation();
	}

	protected final void commonInitialisation() {
		setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.pattern_bg));
		setScrollingCacheEnabled(false);
		setDivider(null);
		setFadingEdgeLength(getResources().getDimensionPixelSize(R.dimen.fade_edge_length));
		setCacheColorHint(getResources().getColor(R.color.fading_edge_color));
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
		int position = Integer.parseInt((String)v.getTag());
		Post post = (Post) getAdapterData().get(position);

		switch(v.getId()){
		case R.id.profile: case R.id.row_post_nick:
			gotoUserPage(post);
			break;

		case R.id.row_post_store_name: case R.id.activity_adapter_target:
			gotoStorePage(post);
			break;
		}
	}

	protected void gotoUserPage(Post post) {
		Intent intent = new Intent(getActivity(), UserMainActivity.class);
		((BaseActivity) getActivity()).startActivityWithMatjiData(intent, post.getUser());
	}	

	protected void gotoStorePage(Post post) {
		Intent intent = new Intent(getActivity(), StoreMainActivity.class);
		((BaseActivity) getActivity()).startActivityWithMatjiData(intent, post.getStore());
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
}
