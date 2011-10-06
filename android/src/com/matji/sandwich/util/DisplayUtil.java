package com.matji.sandwich.util;

import android.content.Context;
import android.content.res.Resources;
import java.lang.ref.WeakReference;

public class DisplayUtil {
    private static WeakReference<Resources> resourceRef;

    public static void setContext(Context context) {
	resourceRef = new WeakReference(context.getResources());
    }
    
    /** 
     * 픽셀단위를 현재 디스플레이 화면에 비례한 크기로 반환합니다. 
     *  
     * @param pixel 픽셀 
     * @return 변환된 값 (DP) 
     */
    public static int DPFromPixel(int pixel)   {
	return (int)(pixel / resourceRef.get().getDisplayMetrics().density); 
    } 

    /** 
     * 현재 디스플레이 화면에 비례한 DP단위를 픽셀 크기로 반환합니다. 
     *  
     * @param  DP 픽셀 
     * @return 변환된 값 (pixel) 
     */ 
    public static int PixelFromDP(double DP)   {
        return (int)(DP * resourceRef.get().getDisplayMetrics().density);
    } 

    /** 
     * 현재 디스플레이 화면에 비례한 DP단위를 픽셀 크기로 반환합니다. 
     *  
     * @param  DP 픽셀 
     * @return 변환된 값 (pixel) 
     */ 
    public static int PixelFromDP(float DP)   {
        return (int)(DP * resourceRef.get().getDisplayMetrics().density);
    } 
}
