package com.matji.sandwich;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
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
	}

	private void initImageView() {
		for (int i = 0; i < attachFileIds.length; i++) {
			RelativeLayout rl = new RelativeLayout(this);
			rl.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);

			ImageView image = new ImageView(this);
			image.setLayoutParams(params);
			image.setScaleType(ScaleType.FIT_CENTER);

			rl.addView(image);

			if (attachFileIds[i] != ImageAdapter.IMAGE_IS_NULL) {
				swipeView.addView(rl);
			}
		}
	}

	public void setImage(int currentPage) {
		int attach_file_id = attachFileIds[currentPage];

		/* Set Current Page Image */
		ImageView image = (ImageView) ((RelativeLayout) swipeView.getChildAt(currentPage)).getChildAt(0);
		downloader.downloadAttachFileImage(attach_file_id, MatjiImageDownloader.IMAGE_XLARGE, image);

		/* Set Previous Page Image */
		if (currentPage > 0) {
			attach_file_id = attachFileIds[currentPage - 1];
			if (attach_file_id != ImageAdapter.IMAGE_IS_NULL) {
				image = (ImageView) ((RelativeLayout) swipeView.getChildAt(currentPage - 1)).getChildAt(0);
				downloader.downloadAttachFileImage(attach_file_id, MatjiImageDownloader.IMAGE_XLARGE, image);
			}
		}

		/* Set Next Page Image */
		if (currentPage < attachFileIds.length - 1) {
			attach_file_id = attachFileIds[currentPage + 1];
			if (attach_file_id != ImageAdapter.IMAGE_IS_NULL) {
				image = (ImageView) ((RelativeLayout) swipeView.getChildAt(currentPage + 1)).getChildAt(0);
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
}