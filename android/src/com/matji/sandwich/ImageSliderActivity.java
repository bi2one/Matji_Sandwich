package com.matji.sandwich;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.content.Intent;

import com.matji.sandwich.adapter.ImageAdapter;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;
import com.matji.sandwich.widget.SwipeView;

public class ImageSliderActivity extends BaseActivity implements OnScrollListener {
	private Intent intent;
	private int[] attachFileIds;
	private int currentPage;
	private SwipeView swipeView;
	private MatjiImageDownloader downloader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_slider);

		intent = getIntent();
		attachFileIds = intent.getIntArrayExtra("attach_file_ids");
		currentPage = intent.getIntExtra("position", 0);
		swipeView = (SwipeView)findViewById(R.id.SwipeView);
		downloader = new MatjiImageDownloader();

		swipeView.addOnScrollListener(this);

		initImageView();
		swipeView.setCurrentPage(currentPage);
		setImage(currentPage);
		Log.d("Matji", attachFileIds.length+"");
	}

	private void initImageView() {
		for (int i = 0; i < attachFileIds.length; i++) {
			ImageView image = new ImageView(this);
			image.setScaleType(ScaleType.FIT_CENTER);
			if (attachFileIds[i] != ImageAdapter.IMAGE_IS_NULL) {
				swipeView.addView(image);
			}
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
			if (attach_file_id != ImageAdapter.IMAGE_IS_NULL) {
				image = (ImageView) swipeView.getChildAt(currentPage - 1);
				downloader.downloadAttachFileImage(attach_file_id, MatjiImageDownloader.IMAGE_XLARGE, image);
			}
		}

		/* Set Next Page Image */
		if (currentPage < attachFileIds.length - 1) {
			attach_file_id = attachFileIds[currentPage + 1];
			if (attach_file_id != ImageAdapter.IMAGE_IS_NULL) {
				image = (ImageView) swipeView.getChildAt(currentPage + 1);
				downloader.downloadAttachFileImage(attach_file_id, MatjiImageDownloader.IMAGE_XLARGE, image);
			}
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

	@Override
	protected String titleBarText() {
		return "ImageSliderActivity";
	}

	@Override
	protected boolean setTitleBarButton(Button button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onTitleBarItemClicked(View view) {
		// TODO Auto-generated method stub

	}
}