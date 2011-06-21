package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.UserMileage;
import com.matji.sandwich.http.util.MatjiImageDownloader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class UserAdapter extends MBaseAdapter {  
	private MatjiImageDownloader downloader;
	private Context context;
	
	public UserAdapter(Context context) {
		super(context);
		downloader = new MatjiImageDownloader();
		this.context = context;
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
			
			convertView.findViewById(R.id.user_cell_point_text).setVisibility(View.GONE);
			convertView.setTag(userElement);
		} else {
			userElement = (UserElement) convertView.getTag();
		}
		
		/* Set User */
		downloader.downloadUserImage(user.getId(), MatjiImageDownloader.IMAGE_SSMALL, userElement.image);		
		
		UserMileage mileage = user.getMileage();
		String grade = "E";

		if (mileage != null) grade = mileage.getGrade();

		if (grade.equals("E")) {
			userElement.grade.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_jewel_01diamond, 0, 0, 0);
			userElement.grade.setText(context.getString(R.string.grade_diamond) + "E");
		} else if (grade.equals("D")) {
			userElement.grade.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_jewel_01diamond, 0, 0, 0);
			userElement.grade.setText(context.getString(R.string.grade_diamond) + "D");
		} else if (grade.equals("C")) {
			userElement.grade.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_jewel_01diamond, 0, 0, 0);
			userElement.grade.setText(context.getString(R.string.grade_diamond) + "C");
		} else if (grade.equals("B")) {
			userElement.grade.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_jewel_01diamond, 0, 0, 0);
			userElement.grade.setText(context.getString(R.string.grade_diamond) + "B");
		} else if (grade.equals("A")) {
			userElement.grade.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_jewel_01diamond, 0, 0, 0);
			userElement.grade.setText(context.getString(R.string.grade_diamond) + "A");
		}
		
		userElement.title.setText(user.getTitle());
		userElement.intro.setText(user.getIntro());
		if (user.getPost() != null) {
			userElement.post.setText(user.getPost().getPost());
		} else {
			userElement.post.setText(context.getString(R.string.default_string_recent_post));
		}
	
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