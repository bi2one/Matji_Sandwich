package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.UserMainActivity;
import com.matji.sandwich.adapter.UserRankingAdapter;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.util.MatjiConstants;

/**
 * View displaying the list with sectioned header.
 * 
 * @author bizone
 */
public abstract class RankingListView extends RequestableMListView {
    private ArrayList<MatjiData> data;
    private Context context;
    
    public RankingListView(Context context, AttributeSet attr) {
	super(context, attr, new UserRankingAdapter(context), 10);

	this.context = context;
	setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
	setDividerHeight(1);
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
