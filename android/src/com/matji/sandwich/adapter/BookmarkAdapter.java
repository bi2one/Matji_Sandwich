package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Region;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookmarkAdapter extends MBaseAdapter {  
    private Context context;
	
    public BookmarkAdapter(Context context) {
	super(context);
	this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
	BookmarkElement bookmarkElement;
	Region region = (Region) data.get(position);

	if (convertView == null) {
	    bookmarkElement = new BookmarkElement();
	    convertView = getLayoutInflater().inflate(R.layout.adapter_bookmark, null);

	    bookmarkElement.desc = (TextView) convertView.findViewById(R.id.bookmark_desc);

	    convertView.setTag(bookmarkElement);
	} else {
	    bookmarkElement = (BookmarkElement) convertView.getTag();
	}
	
	bookmarkElement.desc.setText(region.getDescription());
	
	return convertView;
    }

    private class BookmarkElement {
	TextView desc;
    }    
}