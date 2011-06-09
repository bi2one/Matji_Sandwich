package com.matji.sandwich;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.app.Activity;
import android.content.Intent;

import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;
import com.matji.sandwich.widget.RequestableMListView;
import com.matji.sandwich.widget.SwipeView;

public class ImageSliderActivity extends Activity implements OnScrollListener {
	private Intent intent;
	private int[] attachFileIds;
	private int position;
	private int currentPage;
	private SwipeView swipeView;
	private MatjiImageDownloader downloader;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_slider);

		intent = getIntent();
		attachFileIds = intent.getIntArrayExtra("attach_file_ids");
		position = intent.getIntExtra("position", 0);
		currentPage = position;
		swipeView = (SwipeView)findViewById(R.id.SwipeView);
		downloader = new MatjiImageDownloader();

		swipeView.addOnScrollListener(this);
		
		initImageView();
		swipeView.setCurrentPage(currentPage);
		setImage(currentPage);
	}

	private void initImageView() {
		for (int i = 0; i < attachFileIds.length; i++) {
			ImageView image = new ImageView(this);
			image.setScaleType(ScaleType.FIT_CENTER);
			swipeView.addView(image);
		}
	}

	public void setImage(int currentPage) {
		int attach_file_id = attachFileIds[currentPage];
		/* Set Current Page Image */
		ImageView image = (ImageView) swipeView.getChildAt(currentPage);
		downloader.downloadAttachFileImage(attach_file_id, MatjiImageDownloader.IMAGE_XLARGE, image);

		/* Set Previous Page Image */
		if (currentPage > 0) {
			attach_file_id = attachFileIds[currentPage - 1];
			image = (ImageView) swipeView.getChildAt(currentPage - 1);
			downloader.downloadAttachFileImage(attach_file_id, MatjiImageDownloader.IMAGE_XLARGE, image);
		}
		
		/* Set Next Page Image */
		if (currentPage < attachFileIds.length - 1) {
			attach_file_id = attachFileIds[currentPage + 1];
			image = (ImageView) swipeView.getChildAt(currentPage + 1);
			downloader.downloadAttachFileImage(attach_file_id, MatjiImageDownloader.IMAGE_XLARGE, image);
		}
	}

	
	public void onScroll(int scrollX) {
		// TODO Auto-generated method stub

	}

	
	public void onViewScrollFinished(int currentPage) {
		if (this.currentPage != currentPage) {
			this.currentPage = currentPage;
			setImage(currentPage);
		}
	}
}