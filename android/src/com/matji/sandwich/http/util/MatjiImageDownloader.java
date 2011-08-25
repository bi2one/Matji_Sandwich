package com.matji.sandwich.http.util;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.matji.sandwich.R;
import com.matji.sandwich.listener.ImageDownloaderListener;
import com.matji.sandwich.util.ImageUtil;
import com.matji.sandwich.widget.ProfileImageView;

public class MatjiImageDownloader implements ImageDownloaderListener {
//	private static final String URL_USER_IMAGE = "http://api.matji.com/v2/users/profile";
//	private static final String URL_ATTACH_FILE_IMAGE = "http://api.matji.com/v2/attach_files/image";
	private static final String URL_USER_IMAGE = "http://222.122.205.227/v2/users/profile";
	private static final String URL_ATTACH_FILE_IMAGE = "http://222.122.205.227/v2/attach_files/image";
	public static final String IMAGE_SSMALL = "ss";
	public static final String IMAGE_SMALL = "s";
	public static final String IMAGE_MEDIUM = "m";
	public static final String IMAGE_LARGE = "l";
	public static final String IMAGE_XLARGE = "xl";
	private HashMap<String, String> params;

	private ImageDownloader downloader;
	
    private Bitmap defaultUserImageSmall;
    private Bitmap defaultUserImageLarge;
	private Bitmap defaultAttachedImage;
		
	public MatjiImageDownloader(Context context){
		downloader = new ImageDownloader();
		params = new HashMap<String, String>();
		downloader.setDownloaderListsener(this);
        defaultUserImageSmall = ImageUtil.getBitmap(context.getResources().getDrawable(R.drawable.user_img54));
        defaultUserImageLarge = ImageUtil.getBitmap(context.getResources().getDrawable(R.drawable.user_img90));
		defaultAttachedImage = ImageUtil.getBitmap(context.getResources().getDrawable(R.drawable.img_thumbnail_bg));
	}
	

	public void downloadUserImage(int userId, ImageView imageView) {
		downloadUserImage(userId, IMAGE_SSMALL, imageView);
	}

	public void downloadUserImage(int userId, String imageSize, ImageView imageView) {
		params.clear();
		
		params.put("user_id", userId + "");
		params.put("size", imageSize);
		if (imageSize.equals(IMAGE_SSMALL)) {
            downloader.setDefaultBitmap(defaultUserImageSmall);
            downloader.download(URL_USER_IMAGE, params, imageView);
		} else {
            downloader.setDefaultBitmap(defaultUserImageLarge);
            downloader.download(URL_USER_IMAGE, params, imageView);
		}
	}

	public void downloadAttachFileImage(int attachFileId, ImageView imageView) {
		downloadAttachFileImage(attachFileId, IMAGE_SSMALL, imageView);
	}	

	public void downloadAttachFileImage(int attachFileId, String imageSize, ImageView imageView) {
		params.clear();
		params.put("attach_file_id", attachFileId + "");
		params.put("size", imageSize);
		downloader.setDefaultBitmap(defaultAttachedImage);
		downloader.download(URL_ATTACH_FILE_IMAGE, params, imageView);
	}

	@Override
	public void postSetBitmap(ImageView view) {
		if (view instanceof ProfileImageView) {
			((ProfileImageView) view).convertToRoundedCornerImage();
		}
	}
}