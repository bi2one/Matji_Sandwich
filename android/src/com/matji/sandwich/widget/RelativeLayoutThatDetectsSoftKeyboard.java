package com.matji.sandwich.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import android.widget.RelativeLayout;

/*
 * LinearLayoutThatDetectsSoftKeyboard - a variant of LinearLayout that can detect when 
 * the soft keyboard is shown and hidden (something Android can't tell you, weirdly). 
 */

public class RelativeLayoutThatDetectsSoftKeyboard extends RelativeLayout {
	private Listener listener;
	private enum KeyboardState {keyboardStateShow, keyboardStateHidden};
	private KeyboardState keyboardState;
	private static int ASSUMED_MINIMUM_KEYBOARD_HEIGHT = 128;
	
    public RelativeLayoutThatDetectsSoftKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        keyboardState = KeyboardState.keyboardStateHidden;
    }

    public interface Listener {
        public void onSoftKeyboardShown(boolean isShowing);
    }
    
    public void setListener(Listener listener) {
        this.listener = listener;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	int height = MeasureSpec.getSize(heightMeasureSpec);
        Activity activity = (Activity)getContext();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        int diff = (screenHeight - statusBarHeight) - height;
        if (listener != null) {
        	Log.d("Matji", (keyboardState) + ", " + diff+ ", " +  ASSUMED_MINIMUM_KEYBOARD_HEIGHT);
        	if (keyboardState == KeyboardState.keyboardStateShow && diff <= ASSUMED_MINIMUM_KEYBOARD_HEIGHT) {
        		keyboardState = KeyboardState.keyboardStateHidden;
        		listener.onSoftKeyboardShown(false);	
        	}else if (keyboardState == KeyboardState.keyboardStateHidden && diff > ASSUMED_MINIMUM_KEYBOARD_HEIGHT){
        		keyboardState = KeyboardState.keyboardStateShow;
        		listener.onSoftKeyboardShown(true);
        	}
             // assume all soft keyboards are at least 128 pixels high
        }
        
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
               
    }
}