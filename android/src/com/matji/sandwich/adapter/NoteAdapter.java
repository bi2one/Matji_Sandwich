package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.StoreDetailInfo;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.StoreNoteListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteAdapter extends MBaseAdapter {
	MatjiImageDownloader downloader;

    public NoteAdapter(Context context) {
    	super(context);
    	downloader = new MatjiImageDownloader();
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {
		NoteElement noteElement;
		StoreDetailInfo info = (StoreDetailInfo) data.get(position);
		
		if (convertView == null) {
			noteElement = new NoteElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_post, null);
			
			noteElement.image = (ImageView) convertView.findViewById(R.id.post_adapter_thumnail);
			noteElement.nick = (TextView) convertView.findViewById(R.id.post_adapter_nick);
			noteElement.storeName = (TextView)convertView.findViewById(R.id.post_adapter_store_name);
			noteElement.note = (TextView) convertView.findViewById(R.id.post_adapter_post);
			noteElement.dateAgo = (TextView) convertView.findViewById(R.id.post_adapter_created_at);
			convertView.setTag(noteElement);
			
			StoreNoteListView storeNoteListView = (StoreNoteListView)parent;
			noteElement.image.setOnClickListener(storeNoteListView);
			noteElement.nick.setOnClickListener(storeNoteListView);
			noteElement.storeName.setOnClickListener(storeNoteListView);
			
		} else {
			noteElement = (NoteElement) convertView.getTag();
		}
		
		/* Set Store */
		noteElement.storeName.setText("");
		noteElement.image.setTag(position+"");
		noteElement.nick.setTag(position+"");
		noteElement.storeName.setTag(position+"");
		
		/* Set User */
		User user = info.getUser();
		downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SSMALL, noteElement.image);
		noteElement.nick.setText(user.getNick());
		noteElement.note.setText(info.getNote());
		noteElement.dateAgo.setText(TimeUtil.getAgoFromSecond(info.getAgo()));

		return convertView;
	}
	
    private class NoteElement {
    	ImageView image;
    	TextView nick;
    	TextView storeName;
    	TextView note;
    	TextView dateAgo;
    }
}