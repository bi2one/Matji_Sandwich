package com.matji.sandwich.adapter;


import com.matji.sandwich.R;
import com.matji.sandwich.http.util.DisplayUtil;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.widget.ImageListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class ImageAdapter extends MBaseAdapter {
	private MatjiImageDownloader downloader;
	private ImageListView parent;

	public static final int IMAGE_IS_NULL = -1;
	private static final int MARGIN = DisplayUtil.DPFromPixel(5);
	private int[] imageIds = 
	{
			R.id.image_adapter_image1,
			R.id.image_adapter_image2,
			R.id.image_adapter_image3,
	};	


	public ImageAdapter(Context context) {
		super(context);
		downloader = new MatjiImageDownloader();
		this.context = context;		
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		ImageElement imageElement;

		int[] attach_file_ids = (int[]) data.get(position);

		if (convertView == null) {
			imageElement = new ImageElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_image, null);
			this.parent = (ImageListView) parent;

			imageElement.images = new ImageView[imageIds.length];
			for (int i = 0; i < imageIds.length; i++) {
				imageElement.images[i] = (ImageView) convertView.findViewById(imageIds[i]);
				imageElement.images[i].setOnClickListener(this.parent);
			}
			convertView.setTag(imageElement);
		} else {
			imageElement = (ImageElement) convertView.getTag();
		}


		for (int i = 0; i < imageIds.length; i++) {
			if (attach_file_ids[i] == IMAGE_IS_NULL) {
				imageElement.images[i].setImageDrawable(null);
				imageElement.images[i].setOnClickListener(null);
			}else {
				imageElement.images[i].setTag((position * imageIds.length + i)+"");
				imageElement.images[i].setAnimation(null);
				downloader.downloadAttachFileImage(attach_file_ids[i], MatjiImageDownloader.IMAGE_MEDIUM, imageElement.images[i]);
			}
		}

		int width = context.getResources().getDisplayMetrics().widthPixels/imageIds.length - MARGIN*2;
		int height = width;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
		params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);

		for (int i = 0; i < imageIds.length; i++) {
			imageElement.images[i].setScaleType(ScaleType.CENTER_CROP);
			imageElement.images[i].setLayoutParams(params);
		}

		return convertView;
	}

	private class ImageElement {
		ImageView[] images;
	}

	public int getImageViewCount() {
		return imageIds.length;
	}
}