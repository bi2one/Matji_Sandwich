package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.User;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.widget.UserListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class UserAdapter extends MBaseAdapter {  
	MatjiImageDownloader downloader;
	
	public UserAdapter(Context context) {
		super(context);
		downloader = new MatjiImageDownloader();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserElement userElement;
		User user = (User) data.get(position);

		if (convertView == null) {
			userElement = new UserElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_user, null);

			userElement.image = (ImageView) convertView.findViewById(R.id.user_cell_thumnail);
			userElement.grade = (TextView) convertView.findViewById(R.id.user_cell_grade);
			userElement.title = (TextView) convertView.findViewById(R.id.user_cell_title);
			userElement.intro = (TextView) convertView.findViewById(R.id.user_cell_intro);
			userElement.post = (TextView) convertView.findViewById(R.id.user_adapter_post);
			convertView.setTag(userElement);
//			UserListView userListView = (UserListView) parent;
		} else {
			userElement = (UserElement) convertView.getTag();
		}
		/* Set User */
		downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SSMALL, userElement.image);
		userElement.grade.setText("다이아몬드");
		userElement.title.setText(user.getTitle());
		userElement.intro.setText(user.getIntro());
		if (user.getPost() != null) {
			userElement.post.setText(user.getPost().getPost());
		}
		
		//TODO add recent post
	
		return convertView;
	}

	private class UserElement {
		ImageView image;
		TextView grade;
		TextView title;
		TextView intro;
		TextView post;
	}    
}