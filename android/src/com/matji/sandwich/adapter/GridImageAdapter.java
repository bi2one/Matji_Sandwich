package com.matji.sandwich.adapter;

import com.matji.sandwich.http.util.DisplayUtil;
import com.matji.sandwich.http.util.MatjiImageDownloader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridImageAdapter extends BaseAdapter {
	private MatjiImageDownloader downloader;
	private Context context;
	private int[] attachFileIds;
	private static final int IMAGE_WIDTH = DisplayUtil.PixelFromDP(150);
	private static final int IMAGE_HEIGHT = DisplayUtil.PixelFromDP(150);

	public GridImageAdapter(Context context) {
		this.context = context;
		
		downloader = new MatjiImageDownloader();
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
		image.setLayoutParams(new GridView.LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT));
		image.setScaleType(ImageView.ScaleType.CENTER_CROP);
		return image;
	}
}