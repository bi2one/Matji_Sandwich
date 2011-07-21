package com.matji.sandwich.widget;

import com.matji.sandwich.PostMainActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.StoreTabActivity;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.SectionedPostAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

import android.app.Activity;
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
public class SectionedPostListView extends RequestableMListView implements View.OnClickListener {
	private HttpRequest request;

	public SectionedPostListView(Context context, AttributeSet attr) {
		super(context, attr, new SectionedPostAdapter(context), 10);
		commonInitialisation();
	}

	protected final void commonInitialisation() {
		setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.pattern_bg));
		setScrollingCacheEnabled(false);
		setDivider(null);
	}

	@Override
	public void setActivity(Activity activity) {
		super.setActivity(activity);
		((SectionedPostAdapter) getMBaseAdapter()).setActivity(activity);
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
		case R.id.row_post_thumnail: case R.id.row_post_nick:
			gotoUserPage(post);
			break;

		case R.id.row_post_store_name: case R.id.activity_adapter_target:
			gotoStorePage(post);
			break;
		}
	}

	protected void gotoUserPage(Post post) {
		Intent intent = new Intent(getActivity(), UserTabActivity.class);
		((BaseActivity) getActivity()).startActivityWithMatjiData(intent, post.getUser());
	}	

	protected void gotoStorePage(Post post) {
		Intent intent = new Intent(getActivity(), StoreTabActivity.class);
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