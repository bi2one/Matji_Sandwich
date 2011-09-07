package com.matji.sandwich.http.util;

import android.graphics.Bitmap;

import com.matji.sandwich.util.ImageUtil;
import com.matji.sandwich.util.DisplayUtil;

public class RoundRectConvertable implements ImageLoader.ImageConvertable {
    public Bitmap convert(Bitmap bitmap) {
	return ImageUtil.getRoundedCornerBitmap(bitmap, DisplayUtil.PixelFromDP(4), (int)DisplayUtil.PixelFromDP(1));
    }
}