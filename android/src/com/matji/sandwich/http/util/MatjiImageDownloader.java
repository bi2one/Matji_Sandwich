package com.matji.sandwich.http.util;

import android.widget.ImageView;
import java.util.HashMap;

import com.matji.sandwich.listener.ImageDownloaderListener;
import com.matji.sandwich.widget.ThumnailImageView;

public class MatjiImageDownloader implements ImageDownloaderListener {
	private static final String URL_USER_IMAGE = "http://api.matji.com/v2/users/profile";
	private static final String URL_ATTACH_FILE_IMAGE = "http://api.matji.com/v2/attach_files/image";
	public static final String IMAGE_SSMALL = "ss";
	public static final String IMAGE_SMALL = "s";
	public static final String IMAGE_MEDIUM = "m";
	public static final String IMAGE_LARGE = "l";
	public static final String IMAGE_XLARGE = "xl";

	private ImageDownloader downloader;

	public MatjiImageDownloader() {
		downloader = new ImageDownloader();
		downloader.setDownloaderListsener(this);
	}

	public void downloadUserImage(int userId, ImageView imageView) {
		downloadUserImage(userId, IMAGE_SSMALL, imageView);
	}

	public void downloadUserImage(int userId, String imageSize, ImageView imageView) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_id", userId + "");
		params.put("size", imageSize);
		downloader.download(URL_USER_IMAGE, params, imageView);
	}

	public void downloadAttachFileImage(int attachFileId, ImageView imageView) {
		downloadAttachFileImage(attachFileId, IMAGE_SSMALL, imageView);
	}	

	public void downloadAttachFileImage(int attachFileId, String imageSize, ImageView imageView) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("attach_file_id", attachFileId + "");
		params.put("size", imageSize);
		downloader.download(URL_ATTACH_FILE_IMAGE, params, imageView);
	}

	@Override
	public void postSetBitmap(ImageView view) {
		if (view instanceof ThumnailImageView) {
			((ThumnailImageView) view).convertToRoundedCornerImage();
		}
	}
}