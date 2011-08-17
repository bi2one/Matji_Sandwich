package com.matji.sandwich.widget.search;

import android.content.Context;

import com.matji.sandwich.R;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.HighlightHeader;

/**
 * 
 * @author mozziluv
 */
public class SearchHighlightHeader extends HighlightHeader {

    public SearchHighlightHeader(Context context) {
        super(context, MatjiConstants.string(R.string.search_highlight));
    }
}