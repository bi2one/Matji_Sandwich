package com.matji.sandwich.widget;

import com.matji.sandwich.R;
import com.matji.sandwich.UserMainActivity;
import com.matji.sandwich.adapter.RankingAdapter;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;

import android.os.Parcelable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;

import java.util.ArrayList;

/**
 * View displaying the list with sectioned header.
 * 
 * @author bizone
 */
public abstract class RankingListView extends RequestableMListView {
    private ArrayList<MatjiData> data;
    private Context context;
    
    public RankingListView(Context context, AttributeSet attr) {
	super(context, attr, new RankingAdapter(context), 10);

	this.context = context;
	setBackgroundDrawable(MatjiConstants.drawable(R.drawable.pattern_bg));
	setDivider(null);
	setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
	setCacheColorHint(Color.TRANSPARENT);
	setSelector(android.R.color.transparent);
	data = getAdapterData();
    }
    
    public void onListItemClick(int position) {
	User user = (User)data.get(position);
	Intent intent = new Intent(context, UserMainActivity.class);
	intent.putExtra(UserMainActivity.USER, (Parcelable)user);
	context.startActivity(intent);
    }
}
