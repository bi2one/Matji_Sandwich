package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.app.*;
import android.content.Context;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.matji.sandwich.*;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.adapter.PostAdapter;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

public class PostListView extends RequestableMListView implements OnClickListener {
	private PostHttpRequest postRequest;
	
	public PostListView(Context context, AttributeSet attrs) {
		super(context, attrs, new PostAdapter(context), 10);
		postRequest = new PostHttpRequest(context);
		setPage(1);
		
	 
	}

	public void start(Activity activity) {
		super.start(activity);
//		TextView storeNameView = (TextView)findViewById(R.id.post_adapter_store_name);
//		TextView nickView = (TextView)findViewById(R.id.post_adapter_nick);
//		storeNameView.setOnClickListener(this);
//		nickView.setOnClickListener(this);
	}

	public HttpRequest request() {
		postRequest.actionList(getPage(), getLimit());
		return postRequest;
	}

	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		super.requestCallBack(tag, data);
	}
	
	public void requestExceptionCallBack(int tag, MatjiException e) {
		super.requestExceptionCallBack(tag, e);
	    }

	public void onListItemClick(int position) {
//		Post post = (Post)(getAdapterData().get(position));
//		Intent intent = new Intent(getActivity(), PostInfoActivity.class);
//		intent.putExtra("post", post);
//		getActivity().startActivity(intent);
		MainTabActivity tabAct = MainTabActivity.class.cast( getActivity().getParent());
		tabAct.getTabHost().setCurrentTab(1);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
}
