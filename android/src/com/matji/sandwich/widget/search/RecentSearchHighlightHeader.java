package com.matji.sandwich.widget.search;

import android.content.Context;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.HighlightHeader;

public class RecentSearchHighlightHeader extends HighlightHeader {

	public RecentSearchHighlightHeader(Context context) {
		super(context, MatjiConstants.string(R.string.highlight_recent_search));
	}

}
