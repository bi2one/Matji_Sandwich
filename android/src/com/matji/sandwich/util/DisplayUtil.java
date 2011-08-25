package com.matji.sandwich.util;

import android.content.Context;

public class DisplayUtil { 
	private static Context mContext;

	public static void setContext(Context context) {
		mContext = context;
	}
	/** 
	 * 픽셀단위를 현재 디스플레이 화면에 비례한 크기로 반환합니다. 
	 *  
	 * @param pixel 픽셀 
	 * @return 변환된 값 (DP) 
	 */
	public static int DPFromPixel(int pixel)   {
		return (int)(pixel / mContext.getResources().getDisplayMetrics().density); 
	} 

    /** 
     * 현재 디스플레이 화면에 비례한 DP단위를 픽셀 크기로 반환합니다. 
     *  
     * @param  DP 픽셀 
     * @return 변환된 값 (pixel) 
     */ 
    public static int PixelFromDP(double DP)   {
        return (int)(DP * mContext.getResources().getDisplayMetrics().density);
    } 

    /** 
     * 현재 디스플레이 화면에 비례한 DP단위를 픽셀 크기로 반환합니다. 
     *  
     * @param  DP 픽셀 
     * @return 변환된 값 (pixel) 
     */ 
    public static int PixelFromDP(float DP)   {
        return (int)(DP * mContext.getResources().getDisplayMetrics().density);
    } 
}
