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
        super(context, MatjiConstants.string(R.string.highlight_search_result));
    }
    
    public void search(String keyword) {
        setTitle("'" + keyword + "' " + MatjiConstants.string(R.string.highlight_search_result));
    }
}