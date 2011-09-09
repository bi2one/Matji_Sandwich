package com.matji.sandwich.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * string, dimen 등에서 정의된 상수를 가져올 때 사용하는 클래스.
 * 주로 자바 코드에서 Contants로 string, dimen 등에서 정의 된 것을 등록할 때 사용한다.
 * 
 * @author mozziluv
 *
 */
public class MatjiConstants {
    private static Resources mResources;

    public static void setContext(Context context) {
        mResources = context.getResources();
    }

    public static final String[] stringArray(int id) {
	return mResources.getStringArray(id);
    }
	
    public static final String string(int id) {
	return mResources.getString(id);
    }
    
    public static final String plurals(int id, int quantity) {
        return mResources.getQuantityString(id, quantity);
    }

    public static final float dimen(int id) {
        return mResources.getDimension(id);
    }
	
    public static final int dimenInt(int id) {
        return mResources.getDimensionPixelSize(id);
    }
    
    public static final int color(int id) {
	return mResources.getColor(id);
    }
	
    public static final Drawable drawable(int id) {
	return mResources.getDrawable(id);
    }
}
