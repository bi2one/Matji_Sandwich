package com.matji.sandwich.adapter;

import com.matji.sandwich.R;
import com.matji.sandwich.data.AttachFileIds;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.ImageListView;
import com.matji.sandwich.widget.WhiteBorderImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ImageAdapter extends MBaseAdapter {
	private MatjiImageDownloader downloader;
	private ImageListView parent;

	public static final int IMAGE_IS_NULL = -1;
	private static final int MARGIN = (int)(MatjiConstants.dimen(R.dimen.default_distance_half));

	private int[] imageIds = {
			R.id.image_adapter_image1,
			R.id.image_adapter_image2,
			R.id.image_adapter_image3,
			R.id.image_adapter_image4,
	};

	public ImageAdapter(Context context) {
		super(context);
		downloader = new MatjiImageDownloader(context);
		this.context = context;		
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		ImageElement imageElement;

		AttachFileIds attachFileIds = (AttachFileIds) data.get(position);

		if (convertView == null) {
			imageElement = new ImageElement();
			convertView = getLayoutInflater().inflate(R.layout.adapter_image, null);
			this.parent = (ImageListView) parent;

			imageElement.images = new WhiteBorderImageView[imageIds.length];
			for (int i = 0; i < imageIds.length; i++) {
				imageElement.images[i] = (WhiteBorderImageView) convertView.findViewById(imageIds[i]);
				imageElement.images[i].setOnClickListener(this.parent);
			}
			convertView.setTag(imageElement);
		} else {
			imageElement = (ImageElement) convertView.getTag();
		}


		int width = context.getResources().getDisplayMetrics().widthPixels/imageIds.length - MARGIN*2;
		int height = width;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);

		for (int i = 0; i < imageIds.length; i++) {
			int newMargin = (i == 0 || i == imageIds.length-1) ? MARGIN : MARGIN/2;
			params.setMargins(newMargin, newMargin, newMargin, newMargin);
			imageElement.images[i].setLayoutParams(params);
			
			if (attachFileIds.getIds()[i] == IMAGE_IS_NULL) {
				imageElement.images[i].setTag(-1+"");
				imageElement.images[i].getImageView().setImageDrawable(null);
			}else {
				imageElement.images[i].setTag((position * imageIds.length + i)+"");
				imageElement.images[i].visibleBackground();
				downloader.downloadAttachFileImage(attachFileIds.getIds()[i], MatjiImageDownloader.IMAGE_MEDIUM, imageElement.images[i].getImageView());
			}
		}

		return convertView;
	}

	private class ImageElement {
		WhiteBorderImageView[] images;
	}

	public int getImageViewCount() {
		return imageIds.length;
	}
}