package com.matji.sandwich.http.util;

import android.graphics.Bitmap;
import android.util.Log;

import com.matji.sandwich.util.DisplayUtil;
import com.matji.sandwich.util.ImageUtil;

public class RoundRectConvertable implements ImageLoader.ImageConvertable {
    public Bitmap convert(Bitmap bitmap) {
        return ImageUtil.getRoundedCornerBitmap(bitmap, DisplayUtil.PixelFromDP(4), (int)DisplayUtil.PixelFromDP(1));
    }
}