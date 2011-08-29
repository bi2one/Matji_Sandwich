package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.UserRankingAdapter;
import com.matji.sandwich.util.MatjiConstants;

/**
 * View displaying the list with sectioned header.
 * 
 * @author bizone
 */
public abstract class RankingListView extends RequestableMListView {

    public RankingListView(Context context, AttributeSet attr) {
        super(context, attr, new UserRankingAdapter(context), 10);

        setDivider(new ColorDrawable(MatjiConstants.color(R.color.listview_divider1_gray)));
        setDividerHeight(1);
        setFadingEdgeLength((int) MatjiConstants.dimen(R.dimen.fade_edge_length));
        setCacheColorHint(Color.TRANSPARENT);
        setSelector(android.R.color.transparent);
    }
    
    @Override
    public void onListItemClick(int position) {}
}
