//package com.matji.sandwich.adapter;
//
//import java.util.ArrayList;
//
//import com.matji.sandwich.R;
//import com.matji.sandwich.data.AttachFile;
//import com.matji.sandwich.data.MatjiData;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//public class TestAttachFileAdapter extends ArrayAdapter<MatjiData> {
//	private Context mContext;
//	private int mResource;
//	private ArrayList<MatjiData> mList;
//	private LayoutInflater mInflater;
//
//	public TestAttachFileAdapter(Context context, int resource, ArrayList<MatjiData> list) {
//		super(context, resource, list);
//		this.mContext = context;
//		this.mResource = resource;
//		this.mList = list;
//		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		AttachFile file = (AttachFile) mList.get(position);
//		if(convertView == null) {
//			convertView = mInflater.inflate(mResource, null);
//		}
//		if(file != null) {
//			TextView fileName= (TextView) convertView.findViewById(R.id.list_view_row_file_name);
//			TextView fullPath = (TextView) convertView.findViewById(R.id.list_view_row_full_path);
//			TextView id = (TextView) convertView.findViewById(R.id.list_view_row_id);
//			TextView created_at = (TextView) convertView.findViewById(R.id.list_view_row_created_at);
//			TextView updated_at = (TextView) convertView.findViewById(R.id.list_view_row_updated_at);
//			TextView user_id = (TextView) convertView.findViewById(R.id.list_view_row_user_id);
//
//			fileName.setText("File Name: " + file.getFilename());
//			fullPath.setText("Full Path: " + file.getFullpath());
//			id.setText(String.valueOf("ID: " + file.getId()));
//			created_at.setText("Created at: " + file.getCreatedAt());
//			updated_at.setText("Updated at: " + file.getUpdatedAt());
//			user_id.setText("User ID: " + String.valueOf(file.getUserId()));
//		}
//
//		return convertView;
//	}
//}