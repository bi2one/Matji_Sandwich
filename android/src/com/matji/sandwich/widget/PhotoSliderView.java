package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class PhotoSliderView extends HorizontalScrollView implements OnTouchListener {

    private String TAG = "com.matji.sandwich.widget.PhotoSliderView";

    private RelativeLayout wrapper;
    private RelativeLayout left;
    private RelativeLayout center;
    private RelativeLayout right;

    private int pageWidth;

    private int currItemPos;
    private int photoCount;

    private boolean isScrolling;
    private int scrollToX;

    private enum TouchState {
        TOUCH_UP, TOUCH_DOWN,
    }

    private TouchState touchState;
    private boolean hasStarted = false;

    private static float SNAP_DISTANCE_WEIGHT = 0.4f;
    private static float SWIPE_MIN_DISTANCE= 50;
    private static float SWIPE_MIN_VALOCITY = 500;

    private GestureDetector gestureDetector;
    private final ArrayList<OnPageChangedListener> pageChangedListeners = new ArrayList<PhotoSliderView.OnPageChangedListener>();
    private final ArrayList<OnClickListener> onClickListeners = new ArrayList<PhotoSliderView.OnClickListener>();


    private class GestureDetectorForSwipe extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                float deltaX = e1.getX() - e2.getX();
                if (Math.abs(deltaX) > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VALOCITY) {
                    if (deltaX < 0) {
                        if (existLeftImage()) moveToLeft();
                        return true;
                    } else if (deltaX > 0) {
                        if (existRightImage()) moveToRight();
                        return true;
                    } 
                    

                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            return false;
        }
    }

    public PhotoSliderView(Context context) {
        super(context);
        init();
    }

    public PhotoSliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhotoSliderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        pageWidth = getResources().getDisplayMetrics().widthPixels;
        currItemPos = 0;
        photoCount = 0;

        wrapper = new RelativeLayout(getContext());

        left = createPage();
        center = createPage();
        right = createPage();

        wrapper.addView(left);
        wrapper.addView(center);
        wrapper.addView(right);

        setPageMargin();        
        addView(wrapper);

        gestureDetector = new GestureDetector(new GestureDetectorForSwipe());
        setHorizontalScrollBarEnabled(false);
        setFadingEdgeLength(0);
        setOnTouchListener(this);
    }

    private RelativeLayout createPage() {
        RelativeLayout page = new RelativeLayout(getContext());
        page.addView(createImageView());

        return page;
    }

    private ImageView createImageView() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(pageWidth, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        ImageView iv = new ImageView(getContext());
        iv.setAdjustViewBounds(true);
        iv.setLayoutParams(params);
        iv.setScaleType(ScaleType.FIT_CENTER);

        return iv;
    }

    private void setPageMargin() {
        setLeftPageMargin();
        setCenterPageMargin();
        setRightPageMargin();
    }

    private void setLeftPageMargin() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        params.leftMargin = pageWidth * (currItemPos - 1);
        left.setLayoutParams(params);
        invalidate();
    }

    private void setCenterPageMargin() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        params.leftMargin = pageWidth * (currItemPos);
        center.setLayoutParams(params);
    }

    private void setRightPageMargin() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        params.leftMargin = pageWidth * (currItemPos + 1);
        right.setLayoutParams(params);
    }

    public void scrollToPosition(int position) {
        isScrolling = true;
        scrollTo(position * pageWidth, 0);        
    }

    public void smoothScrollToPosition(int position){
        isScrolling = true;
        smoothScrollTo(position * pageWidth, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!hasStarted) {
            scrollToPosition(currItemPos);
            hasStarted = true;
        }
    }

    public void moveToLeft() {
        currItemPos--;
        scrollToX = currItemPos * pageWidth; 
        smoothScrollToPosition(currItemPos);
        RelativeLayout tmp = center;
        center = left;
        left = right;
        right = tmp; 

        setLeftPageMargin();
        for (OnPageChangedListener listener : pageChangedListeners) {
            listener.changeToLeft();
        }
    }

    public void setCurrentItemPosition(int currItemPos) {
        this.currItemPos = currItemPos;
        setPageMargin();
        if (existLeftImage()) {
            moveToLeft();moveToRight();
        }
        if (existRightImage()) {
            moveToRight();moveToLeft();
        }
    }

    public int getCurrentItemPosition() {
        return currItemPos;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
        if (photoCount == 1) {
            wrapper.removeView(left);
            wrapper.removeView(right);
        }
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public boolean existLeftImage() {
        return currItemPos-1 >= 0;
    }

    public boolean existRightImage() {
        return currItemPos+1 < photoCount;
    }
    
    public void moveToRight() {
        currItemPos++;
        scrollToX = currItemPos * pageWidth;
        smoothScrollToPosition(currItemPos);
        RelativeLayout tmp = center;
        center = right;
        right = left;
        left = tmp;

        if (currItemPos != photoCount-1)
            setRightPageMargin();

        for (OnPageChangedListener listener : pageChangedListeners) {
            listener.changeToRight();            
        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (isScrolling && scrollToX != l)
            return;
        else
            isScrolling = false;

        if (touchState == TouchState.TOUCH_DOWN) return;

        int nextPageX = currItemPos * pageWidth;
        if (nextPageX - (float)pageWidth * SNAP_DISTANCE_WEIGHT >= l) {
            // left page changed
            if (existLeftImage()) moveToLeft();
        } else if (nextPageX + (float)pageWidth * SNAP_DISTANCE_WEIGHT <= l) {
            // right page changed
            if (existRightImage()) moveToRight();
        } else {
            smoothScrollToPosition(currItemPos);
        }
    }


    private float downX;
    private float downY;
    private static final float CLICK_MIN_DISTANCE = 5.0f;
    private static final int CLICK_MIN_TIME = 300;

    @Override
    public boolean onTouch(View v, MotionEvent e) {

        if (gestureDetector.onTouchEvent(e)) return true;
        int act = e.getAction();
        switch (act) {
        case MotionEvent.ACTION_UP:

            float deltaX = Math.abs(e.getX() - downX);
            float deltaY = Math.abs(e.getY() - downY);
            long eventTime = e.getEventTime() - e.getDownTime();
            if (deltaX < CLICK_MIN_DISTANCE && deltaY < CLICK_MIN_DISTANCE && eventTime < CLICK_MIN_TIME) {
                for (OnClickListener listener : onClickListeners) {
                    listener.onClick();
                }
            }
            touchState = TouchState.TOUCH_UP;

            post(new Runnable() {

                @Override
                public void run() {
                    onScrollChanged(getScrollX(), 0, 0, 0);
                }
            });
            break;
        case MotionEvent.ACTION_DOWN:
            downX = e.getX();
            downY = e.getY();
            isScrolling = false;

            touchState = TouchState.TOUCH_DOWN;
            break;
        }

        return false;
    }

    public void addOnPageChangedLlistener(OnPageChangedListener listener) {
        pageChangedListeners.add(listener);
    }

    public void removeOnPageChangedLlistener(OnPageChangedListener listener) {
        pageChangedListeners.remove(listener);
    }

    public void addOnClickLlistener(OnClickListener listener) {
        onClickListeners.add(listener);
    }

    public void removeOnClickChangedLlistener(OnClickListener listener) {
        onClickListeners.remove(listener);
    }

    public ImageView getRightImageView() {
        return (ImageView) right.getChildAt(0);
    }

    public ImageView getCenterImageView() {
        return (ImageView) center.getChildAt(0);
    }

    public ImageView getLeftImageView() {
        return (ImageView) left.getChildAt(0);
    }

    public static interface OnClickListener {
        void onClick();
    }

    public static interface OnPageChangedListener {
        void changeToLeft();
        void changeToRight();
    }
}