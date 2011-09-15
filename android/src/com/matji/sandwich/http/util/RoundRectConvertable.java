package com.matji.sandwich.http.util;

import android.graphics.Bitmap;

import com.matji.sandwich.R;
import com.matji.sandwich.util.ImageUtil;
import com.matji.sandwich.util.MatjiConstants;

public class RoundRectConvertable implements ImageLoader.ImageConvertable {

    private float pixel;
    private int inset;

    public RoundRectConvertable() {
        this(MatjiConstants.dimen(R.dimen.default_round_pixel), MatjiConstants.dimenInt(R.dimen.default_round_inset));
    }

    public RoundRectConvertable(float pixel, int inset) {
        this.pixel = pixel;
        this.inset = inset;
    }

    public Bitmap convert(Bitmap bitmap) {
        return ImageUtil.getRoundedCornerBitmap(bitmap, pixel, inset);
    }
}