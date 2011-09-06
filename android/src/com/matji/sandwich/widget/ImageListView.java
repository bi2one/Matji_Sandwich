package com.matji.sandwich.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.adapter.ImageAdapter;
import com.matji.sandwich.http.request.AttachFilesHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.util.MatjiConstants;


public abstract class ImageListView extends RequestableMListView {
	protected HttpRequest request;

	protected int imageCount;
	
	private static final int LIMIT = 6;

	/**
	 * 헤더에 추가될 텍스트.
	 * ex) 777개의 MOZZILUV 사진
	 * 
	 * @return
	 */
	abstract protected String getTotalImageCountText();
	
	public ImageListView(Context context, AttributeSet attrs) {
	    super(context, attrs, new ImageAdapter(context), LIMIT);
	}
	
	protected void init() {
		imageCount = ((ImageAdapter) getMBaseAdapter()).getImageViewCount();

		addHeaderView(createHeader());
		setDivider(null);
		setSelector(android.R.color.transparent);
		setCacheColorHint(Color.TRANSPARENT);
		setVerticalScrollBarEnabled(false);
		setBackgroundDrawable(MatjiConstants.drawable(R.drawable.bg_01));

	}

	private TextView createHeader() {
		TextView totalImageCount = new TextView(getContext());
		totalImageCount.setText(getTotalImageCountText());
		totalImageCount.setTextColor(MatjiConstants.color(R.color.matji_chocolate));
//		totalImageCount.setTextSize(MatjiConstants.dimen(R.dimen.text_large));
		// TODO 이상하게 파라미터로 픽셀이 아니라 DIP로 받는 것 같네...?
		totalImageCount.setTextSize(17);
		int padding = (int) MatjiConstants.dimen(R.dimen.default_distance);
		totalImageCount.setPadding(padding, padding*2, padding, 0);
		
		return totalImageCount;
	}
	

	
	protected void createRequest() {
		if (request == null || !(request instanceof AttachFilesHttpRequest)) {
			request = new AttachFilesHttpRequest(getContext(), imageCount);
		}
	}
		
	public void onListItemClick(int position) {}
}