package com.matji.sandwich.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.util.Log;

import com.matji.sandwich.R;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.AttachFiles;
import com.matji.sandwich.listener.GotoImageSliderAction;
import com.matji.sandwich.http.util.ImageLoader;

public class ImageAdapter extends MBaseAdapter {
    private ViewGroup spinnerContainer;
    private ImageLoader imageLoader;

    private ArrayList<AttachFile> fileList;
	
    private int[] imageWrapperIds = {
	R.id.row_image_col1,
	R.id.row_image_col2,
	R.id.row_image_col3,
	R.id.row_image_col4,
    };

    public ImageAdapter(Context context) {
	super(context);
	this.context = context;
	imageLoader = new ImageLoader(context, R.drawable.box_restaurant);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
	ImageElement imageElement;

	AttachFiles attachFiles = (AttachFiles) data.get(position);

	if (convertView == null) {
	    imageElement = new ImageElement();
	    convertView = getLayoutInflater().inflate(R.layout.row_image, null);
	    imageElement.images = convertView.findViewById(R.id.row_image_images);
			
	    imageElement.image = new ImageView[imageWrapperIds.length];
	    imageElement.imageWrapper = new ViewGroup[imageWrapperIds.length];
	    for (int i = 0; i < imageWrapperIds.length; i++) {
		imageElement.image[i] = new ImageView(context);
		imageElement.image[i].setScaleType(ScaleType.CENTER_CROP);
		imageElement.imageWrapper[i] = (ViewGroup) convertView.findViewById(imageWrapperIds[i]);
		imageElement.imageWrapper[i].addView(imageElement.image[i]);
	    }
			
	    convertView.setTag(imageElement);
	} else {
	    imageElement = (ImageElement) convertView.getTag();
	}
		
	fileList = getAttachFiles();
		
	for (int i = 0; i < imageElement.image.length; i++) {
	    imageElement.image[i].setOnClickListener(new GotoImageSliderAction(context, fileList));
	}
		
	imageElement.images.setVisibility(View.GONE);
		
	for (int i = 0; i < imageElement.image.length; i++)
	    imageElement.imageWrapper[i].setVisibility(View.GONE);

	for (int i = 0; (i < attachFiles.getFiles().length) && (i < imageElement.image.length); i++) {
	    imageElement.images.setVisibility(View.VISIBLE);
	    AttachFile file = attachFiles.getFiles()[i];
	    if (file != null) { 
		imageElement.imageWrapper[i].setVisibility(View.VISIBLE);
		imageElement.image[i].setTag((imageWrapperIds.length * position + i)+"");
		imageLoader.DisplayImage((Activity)context,
					 ImageLoader.UrlType.ATTACH_FILE,
					 ImageLoader.ImageSize.SMALL,
					 imageElement.image[i],
					 file.getId());
	    }		        
	}

	return convertView;
    }
	
    private ArrayList<AttachFile> getAttachFiles() {
	ArrayList<AttachFile> attachFileList = new ArrayList<AttachFile>();
	for (int i = 0; i < data.size(); i++) {
	    AttachFiles files = (AttachFiles) data.get(i);
	    for (AttachFile file : files.getFiles()) {
		if (file != null) attachFileList.add(file);
	    }
	}
	    
	return attachFileList;
    }

    private class ImageElement {
	View images;
	ViewGroup[] imageWrapper;
	ImageView[] image;
    }

    public int getImageViewCount() {
	return imageWrapperIds.length;
    }
}