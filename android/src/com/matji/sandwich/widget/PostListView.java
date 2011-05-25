package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.matji.sandwich.MainTabActivity;
import com.matji.sandwich.PostInfoActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.UserTabActivity;
import com.matji.sandwich.adapter.PostAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

public class PostListView extends RequestableMListView implements View.OnClickListener {
	private HttpRequest request;
		
	public PostListView(Context context, AttributeSet attrs) {
		super(context, attrs, new PostAdapter(context), 10);
		request = new PostHttpRequest(context);
		setPage(1);
	}

	public void start(Activity activity) {
		super.start(activity);
		
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
		MainTabActivity tabAct = MainTabActivity.class.cast(getActivity().getParent());
		tabAct.getTabHost().setCurrentTab(1);
	}

	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.post_adapter_nick:
			Post post = (Post)getAdapterData().get(Integer.parseInt((String)v.getTag()));
			Log.d("-----", "" +  post.getUser().getNick());
			Intent intent = new Intent(getActivity(), UserTabActivity.class);
			intent.putExtra("user", post.getUser());
			getActivity().startActivity(intent);
			break;
		case R.id.post_adapter_store_name:
			Log.d("asdkasldkljad","2");
			break;
		}
		
		
						
	}	
}
