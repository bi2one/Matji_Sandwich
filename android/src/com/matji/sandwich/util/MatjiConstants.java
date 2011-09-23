package com.matji.sandwich.util;

import java.util.Locale;

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
    
    public static final int MIN_NICK_LENGTH = 2;
    public static final int MAX_NICK_LENGTH = 12;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 20;
    public static final int MIN_FOOD_NAME_LENGTH = 1;
    public static final int MAX_FOOD_NAME_LENGTH = 12;

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
    
    public static final String lang() {
    	Locale locale = mResources.getConfiguration().locale;
    	return locale.getLanguage();
    }

}
