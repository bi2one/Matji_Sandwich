package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.http.util.DisplayUtil;
import com.matji.sandwich.http.util.MatjiImageDownloader;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class SlideImageAdapter extends BaseAdapter {

	private MatjiImageDownloader downloader;
	private Context context;
	private int[] attachFileIds;

	public SlideImageAdapter(Context context) {
		this.context = context;
		
		TypedArray arr = ((Activity)context).obtainStyledAttributes(R.styleable.Theme);
		downloader = new MatjiImageDownloader();
		arr.recycle();
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
		downloader.downloadAttachFileImage(attachFileIds[position], MatjiImageDownloader.IMAGE_SMALL, image);
		image.setLayoutParams(new Gallery.LayoutParams(150, 150));
		int padding = DisplayUtil.PixelFromDP(10);
		image.setPadding(padding, padding, padding, padding);
		image.setScaleType(ImageView.ScaleType.CENTER_CROP);
		return image;
	}
}