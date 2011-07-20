package com.matji.sandwich.listener;

import android.widget.ImageView;

public interface ImageDownloaderListener {
	/**
	 * ImageDownloader 를 이용해 다운 받은 이미지를 따로 가공하기 위한 메소드.
	 * 
	 * @param view 이미지를 다운받은 ImageView
	 */
	public void postSetBitmap(ImageView view);
}
