package com.matji.sandwich.adapter;

import java.text.*;
import java.util.*;

import com.matji.sandwich.R;
import com.matji.sandwich.data.*;
import com.matji.sandwich.widget.*;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.method.*;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PostAdapter extends MBaseAdapter {
    public PostAdapter(Context context) {
    	super(context);
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {
		PostElement postElement;
		Post post = (Post) data.get(position);
		
		if (convertView == null) {
			postElement = new PostElement();
			convertView = getLayoutInflater().inflate(R.layout.post_adapter, null);
			
			postElement.image = (ImageView) convertView.findViewById(R.id.thumnail);
			postElement.nick = (TextView) convertView.findViewById(R.id.post_adapter_nick);
			postElement.storeName = (TextView)convertView.findViewById(R.id.post_adapter_store_name);
			postElement.post = (TextView) convertView.findViewById(R.id.post_adapter_post);
			postElement.dateAgo = (TextView) convertView.findViewById(R.id.post_adapter_created_at);
			convertView.setTag(postElement);
			
			
			PostListView postListView = (PostListView)parent;
			postElement.nick.setOnClickListener(postListView);
			postElement.storeName.setOnClickListener(postListView);
			
		} else {
			postElement = (PostElement) convertView.getTag();
		}
		
		
				
		Store store = post.getStore();
		if (store != null)
			postElement.storeName.setText(" @" + store.getName());
		else 
			postElement.storeName.setText("");
		
		postElement.nick.setTag(position+"");
		postElement.storeName.setTag(position+"");
		
		postElement.nick.setText(post.getUser().getNick());
		postElement.post.setText(post.getPost());
		postElement.dateAgo.setText(getAgoFromDate(post.getCreatedAt()));
		return convertView;
	}
	
	private String getAgoFromDate(String dateString){
		dateString = dateString.replace('T', ' ');
		dateString = dateString.replaceAll("Z", "");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;		
		try {
			date = (Date)format.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date now = new Date();
		long nowSeconds = now.getTime() / 1000;
	    long postSeconds = date.getTime() / 1000;
	    
	    
	    long timeInterval = nowSeconds - postSeconds;
		
	    return timestampWithTimeInterval(timeInterval);
	}
	
	
	private String timestampWithTimeInterval(long timeInterval) {
	    long x = 0;
		long time = timeInterval;
		String timestampText;
		String year = "year ago";
		String years = "years ago";
		String month = "month ago";
		String months = "months ago";
		String week = "week ago";
		String weeks = "weeks ago";
		String day = "day ago";
		String days = "days ago";
		String hour = "hour ago";
		String hours = "hours ago";
		String min = "min ago";
		String sec = "sec ago";
		
		if ((x = time / 31104000) > 0) { // year
			if (x == 1) {
				timestampText = x + " " + year; // singular
			} else {
				timestampText = x + " " + years;	// plural
			}
		} else {
			if ((x = time / 2592000) > 0) { // month
				if (x == 1) {
					timestampText = x+ " " + month; // singular
				} else {
					timestampText = x+ " " + months; // plural
				}
			} else {
				if ((x = time / 604800) > 0) { // week
					if (x == 1) {
						timestampText = x+ " " + week; // singular
					} else {
						timestampText = x+ " " + weeks; // plural
					}
				} else {
					if ((x = time / 86400) > 0) { // day
						if (x == 1) {
							timestampText = x+ " " + day; // singular
						} else {
							timestampText = x+ " " + days; // plural
						}
					} else {
						if ((x = time / 3600) > 0) { // hour
							if (x == 1) {
								timestampText = x+ " " + hour; // singular
							} else {
								timestampText = x+ " " + hours; // plural
							}
						} else {
							if ((x = time / 60) > 0) { // min
								timestampText = x+ " " + min;
							} else { // sec
								timestampText = time+ " " + sec;
							}
						}
					}
				}
			}
		}		
		
		return timestampText;
	}

	
	
    private class PostElement {
    	ImageView image;
    	TextView nick;
    	TextView storeName;
    	TextView post;
    	TextView dateAgo;
    }



		

}