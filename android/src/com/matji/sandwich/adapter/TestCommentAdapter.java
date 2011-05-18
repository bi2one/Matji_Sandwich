package com.matji.sandwich.adapter;

import java.util.ArrayList;

import com.matji.sandwich.R;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.MatjiData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TestCommentAdapter extends ArrayAdapter<MatjiData> {
	private Context mContext;
	private int mResource;
	private ArrayList<MatjiData> mList;
	private LayoutInflater mInflater;

	public TestCommentAdapter(Context context, int resource, ArrayList<MatjiData> list) {
		super(context, resource, list);
		this.mContext = context;
		this.mResource = resource;
		this.mList = list;
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Comment comment = (Comment) mList.get(position);
		if(convertView == null) {
			convertView = mInflater.inflate(mResource, null);
		}
		if(comment != null) {
			TextView commentContent= (TextView) convertView.findViewById(R.id.list_view_row_comment);
			TextView fromWhere = (TextView) convertView.findViewById(R.id.list_view_row_from_where);
			TextView id = (TextView) convertView.findViewById(R.id.list_view_row_id);
			TextView post_id = (TextView) convertView.findViewById(R.id.list_view_row_post_id);
			TextView created_at = (TextView) convertView.findViewById(R.id.list_view_row_created_at);
			TextView updated_at = (TextView) convertView.findViewById(R.id.list_view_row_updated_at);
			TextView user_id = (TextView) convertView.findViewById(R.id.list_view_row_user_id);

			commentContent.setText(comment.getComment());
			fromWhere.setText(comment.getFromWhere());
			id.setText(String.valueOf(comment.getId()));
			post_id.setText(String.valueOf(comment.getPostId()));
			updated_at.setText(comment.getUpdatedAt());
			created_at.setText(comment.getCreatedAt());
			user_id.setText(String.valueOf(comment.getUserId()));
		}

		return convertView;
	}
}