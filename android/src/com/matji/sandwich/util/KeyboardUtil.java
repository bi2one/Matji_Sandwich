package com.matji.sandwich.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {
	public static void showKeyboard(Activity activity, View viewForFocus){
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(viewForFocus, 0);
	}

	public static void hideKeyboard(Activity activity){
		InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);	
	}
}
