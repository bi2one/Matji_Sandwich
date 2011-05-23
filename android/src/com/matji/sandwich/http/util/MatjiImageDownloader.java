package com.matji.sandwich.http.util;

import android.widget.ImageView;
import java.util.HashMap;

public class MatjiImageDownloader {
    private static final String URL_USER_IMAGE = "http://api.matji.com/v2/users/profile";
    private static final String URL_ATTACH_FILE_IMAGE = "http://api.matji.com/v2/attach_files/image";

    private ImageDownloader downloader;

    public MatjiImageDownloader() {
	downloader = new ImageDownloader();
    }
    
    public void downloadUserImage(int userId, ImageView imageView) {
	HashMap<String, String> params = new HashMap<String, String>();
	params.put("user_id", userId + "");
	downloader.download(URL_USER_IMAGE, params, imageView);
    }

    public void downloadAttachFileImage(int attachFileId, ImageView imageView) {
	HashMap<String, String> params = new HashMap<String, String>();
	params.put("attach_file_id", attachFileId + "");
	downloader.download(URL_ATTACH_FILE_IMAGE, params, imageView);
    }
}