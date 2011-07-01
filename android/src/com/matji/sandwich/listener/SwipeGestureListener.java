package com.matji.sandwich.listener;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.matji.sandwich.util.DisplayUtil;

public abstract class SwipeGestureListener implements OnTouchListener {
	public abstract void swipeToLeft(int x, int y);
	public abstract void swipeToRight(int x, int y);
	public abstract void onItemClicked(int x, int y);

	private boolean hasEnded = false;

	private static final int SWIPE_MIN_DISTANCE = DisplayUtil.PixelFromDP(120);
	private static final int SWIPE_AUTO_DISTANCE = DisplayUtil.PixelFromDP(160);
	private static final int SWIPE_MAX_OFF_PATH = DisplayUtil.PixelFromDP(250);

	private GestureDetector gestureDetector;

	public SwipeGestureListener(Context context) {
		gestureDetector = new GestureDetector(new SwipeGestureDetector());
	}

	
	public boolean onTouch(View v, MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		return false;
	}

	class SwipeGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			Log.d("Matji", "single");
			onItemClicked((int) e.getX(), (int) e.getY());
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			Log.d("Matji", "long");
			int x = (int) e.getX();
			int y = (int) e.getY();

			swipeToLeft(x, y);
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			Log.d("Matji", "double");
			onItemClicked((int) e.getX(), (int) e.getY());
			return false;
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			hasEnded = false;
			return super.onDown(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return swipe(e1, e2, SWIPE_AUTO_DISTANCE);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return swipe(e1, e2, SWIPE_MIN_DISTANCE);
		}

		public boolean swipe(MotionEvent e1, MotionEvent e2, int distance) {
			if (e1 == null) return false;
			
			int x = (int) e1.getX();
			int y = (int) e1.getY();

			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;

				if (Math.abs(e1.getX() - e2.getX()) > distance && !hasEnded) {
					if(e1.getX() > e2.getX()) {
						swipeToLeft(x, y);
					}  else {
						swipeToRight(x, y);
					}
					hasEnded = true;
				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}
	}
}