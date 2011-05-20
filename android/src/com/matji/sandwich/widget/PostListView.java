package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.app.*;
import android.app.Activity;
import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.matji.sandwich.*;
import com.matji.sandwich.data.*;
import com.matji.sandwich.adapter.PostAdapter;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;

public class PostListView extends RequestableMListView implements View.OnClickListener{
	private PostHttpRequest postRequest;
	
	public PostListView(Context context, AttributeSet attrs) {
		super(context, attrs, new PostAdapter(context), 10);
		postRequest = new PostHttpRequest(context);
		setPage(1);
	}

	public void start(Activity activity) {
		super.start(activity);
		
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
		MainTabActivity tabAct = MainTabActivity.class.cast( getActivity().getParent());
		tabAct.getTabHost().setCurrentTab(1);
	}

	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.post_adapter_nick:
			Post post = (Post)getAdapterData().get(Integer.parseInt((String)v.getTag()));
			Log.d("-----", "" +  post.getUser().getNick());
			Intent intent = new Intent(getActivity(), PostInfoActivity.class);
			intent.putExtra("post", post);
			getActivity().startActivity(intent);
			
			
			break;
		case R.id.post_adapter_store_name:
			Log.d("asdkasldkljad","2");
			break;
		}
		
		
						
	}

	
	
}
