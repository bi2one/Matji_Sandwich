package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.util.DisplayUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class SlideImageAdapter extends BaseAdapter {
	private MatjiImageDownloader downloader;
	private Context context; 
	private int[] attachFileIds;
	
	private static final int IMAGE_HEIGHT = DisplayUtil.PixelFromDP(150);
	private static final int IMAGE_PADDING = DisplayUtil.PixelFromDP(5);

	public SlideImageAdapter(Context context) {
		this.context = context;
		
		downloader = new MatjiImageDownloader(context);
	}

	public void setAttachFileIds(int[] attachFileIds) {
		this.attachFileIds = attachFileIds;
	}
	
	public int getCount() {
		return attachFileIds.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView image = new ImageView(context);
		downloader.downloadAttachFileImage(attachFileIds[position], MatjiImageDownloader.IMAGE_MEDIUM, image);
		image.setPadding(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING);
		image.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img_border));
		image.setMaxHeight(IMAGE_HEIGHT);
		image.setAdjustViewBounds(true);

		return image;
	}
}