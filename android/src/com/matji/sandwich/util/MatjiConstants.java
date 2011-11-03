package com.matji.sandwich.util;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.matji.sandwich.R;

/**
 * string, dimen 등에서 정의된 상수를 가져올 때 사용하는 클래스.
 * 주로 자바 코드에서 Contants로 string, dimen 등에서 정의 된 것을 등록할 때 사용한다.
 * 
 * @author mozziluv
 *
 */
public class MatjiConstants {
    private static WeakReference<Resources> mResourcesRef;

    public static final int MIN_NICK_LENGTH = 2;
    public static final int MAX_NICK_LENGTH = 12;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 20;
    public static final int MIN_FOOD_NAME_LENGTH = 1;
    public static final int MAX_FOOD_NAME_LENGTH = 12;

    public static void setContext(Context context) {
        mResourcesRef = new WeakReference<Resources>(context.getResources());
    }

    public static final String[] stringArray(int id) {
        return mResourcesRef.get().getStringArray(id);
    }

    public static final String string(int id) {
        return mResourcesRef.get().getString(id);
    }

    public static final String plurals(int id, int quantity) {
        return mResourcesRef.get().getQuantityString(id, quantity);
    }

    public static final float dimen(int id) {
        return mResourcesRef.get().getDimension(id);
    }

    public static final int dimenInt(int id) {
        return mResourcesRef.get().getDimensionPixelSize(id);
    }

    public static final int color(int id) {
        return mResourcesRef.get().getColor(id);
    }

    public static final Drawable drawable(int id) {
        return mResourcesRef.get().getDrawable(id);
    }

    public static final String target() {
        return mResourcesRef.get().getString(R.string.target);
    }
    public static final String language() {
        return mResourcesRef.get().getConfiguration().locale.getLanguage();
    }
    
    public static final String countryName(String code) {
        String[] names = MatjiConstants.stringArray(R.array.country_names);
        String[] codes = MatjiConstants.stringArray(R.array.country_codes);
        for (int i = 0; i < codes.length; i++) {
            if (code.equals(codes[i])) return names[i];
        }
        
        return null;
    }   
    
    public static final String countryCode(String name) {
        String[] names = MatjiConstants.stringArray(R.array.country_names);
        String[] codes = MatjiConstants.stringArray(R.array.country_codes);
        for (int i = 0; i < codes.length; i++) {
            if (name.equals(names[i])) return codes[i];
        }
        
        return null;
    }
}
