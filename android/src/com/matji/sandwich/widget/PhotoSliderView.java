package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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

    private int currentModeWidth;
    private int verticalModeWidth;
    private int horizontalModeWidth;

    private int currentItemPos;
    private int photoCount;

    private boolean isScrolling;
    private int scrollToX;

    private Drawable leftDrawableCache;
    private Drawable centerDrawableCache;
    private Drawable rightDrawableCache;

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
        currentModeWidth = verticalModeWidth = getResources().getDisplayMetrics().widthPixels;
        horizontalModeWidth = getResources().getDisplayMetrics().heightPixels;


        currentItemPos = 0;
        photoCount = 0;

        initViews();

        gestureDetector = new GestureDetector(new GestureDetectorForSwipe());
        setHorizontalScrollBarEnabled(false);
        setFadingEdgeLength(0);
        setOnTouchListener(this);
    }

    private void initViews() {
        wrapper = new RelativeLayout(getContext());

        left = createPage();
        center = createPage();
        right = createPage();

        wrapper.addView(left);
        wrapper.addView(center);
        wrapper.addView(right);

        setPageMargin();
        removeAllViews();
        addView(wrapper);
    }

    private RelativeLayout createPage() {
        RelativeLayout page = new RelativeLayout(getContext());
        page.addView(createImageView());

        return page;
    }

    private ImageView createImageView() {
        ImageView iv = new ImageView(getContext());
        iv.setAdjustViewBounds(true);
        iv.setLayoutParams(getImageViewLayoutParams());
        iv.setScaleType(ScaleType.FIT_CENTER);

        return iv;
    }

    private RelativeLayout.LayoutParams getImageViewLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(currentModeWidth, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        return params;
    }

    private void setPageMargin() {
        setLeftPageMargin();
        setCenterPageMargin();
        setRightPageMargin();
    }

    private void setLeftPageMargin() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        params.leftMargin = currentModeWidth * (currentItemPos - 1);
        left.setLayoutParams(params);
    }

    private void setCenterPageMargin() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        params.leftMargin = currentModeWidth * (currentItemPos);
        center.setLayoutParams(params);
    }

    private void setRightPageMargin() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        params.leftMargin = currentModeWidth * (currentItemPos + 1);
        right.setLayoutParams(params);
    }

    public void scrollToPosition(int position) {
        isScrolling = true;
        scrollTo(position * currentModeWidth, 0);        
    }

    public void smoothScrollToPosition(int position){
        isScrolling = true;
        smoothScrollTo(position * currentModeWidth, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!hasStarted) {
            scrollToPosition(currentItemPos);
            hasStarted = true;
        }
    }

    public void moveToLeft(boolean cast) {
        currentItemPos--;
        scrollToX = currentItemPos * currentModeWidth; 
        smoothScrollToPosition(currentItemPos);
        RelativeLayout tmp = center;
        center = left;
        left = right;
        right = tmp; 

        setLeftPageMargin();
        
        if (cast) {
            for (OnPageChangedListener listener : pageChangedListeners) {
                listener.changeToLeft();
            }
        }
    }

    public void moveToLeft() {
        moveToLeft(true);
    }

    public void setCurrentItemPosition(int currItemPos) {
        this.currentItemPos = currItemPos;
        setPageMargin();
        if (existLeftImage()) {
            moveToLeft(false);moveToRight(false);
        }
        if (existRightImage()) {
            moveToRight(false);moveToLeft(false);
        }
    }

    public int getCurrentItemPosition() {
        return currentItemPos;
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
        return currentItemPos-1 >= 0;
    }

    public boolean existRightImage() {
        return currentItemPos+1 < photoCount;
    }

    public void moveToRight(boolean cast) {
        currentItemPos++;
        scrollToX = currentItemPos * currentModeWidth;
        smoothScrollToPosition(currentItemPos);
        RelativeLayout tmp = center;
        center = right;
        right = left;
        left = tmp;

        if (currentItemPos != photoCount-1)
            setRightPageMargin();

        if (cast) {
            for (OnPageChangedListener listener : pageChangedListeners) {
                listener.changeToRight();            
            }
        }
    }

    public void moveToRight() {
        moveToRight(true);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (isScrolling && scrollToX != l)
            return;
        else
            isScrolling = false;

        if (touchState == TouchState.TOUCH_DOWN) return;

        int nextPageX = currentItemPos * currentModeWidth;
        if (nextPageX - (float)currentModeWidth * SNAP_DISTANCE_WEIGHT >= l) {
            // left page changed
            if (existLeftImage()) moveToLeft();
        } else if (nextPageX + (float)currentModeWidth * SNAP_DISTANCE_WEIGHT <= l) {
            // right page changed
            if (existRightImage()) moveToRight();
        } else {
            smoothScrollToPosition(currentItemPos);
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

    public void horizontalMode() {
        currentModeWidth = horizontalModeWidth;
        changedMode();
    }

    public void verticalMode() {
        currentModeWidth = verticalModeWidth;
        changedMode();
    }

    private void changedMode() {
        leftDrawableCache = getLeftImageView().getDrawable();
        centerDrawableCache = getCenterImageView().getDrawable();
        rightDrawableCache = getRightImageView().getDrawable();
        initViews();
        getLeftImageView().setImageDrawable(leftDrawableCache);
        getCenterImageView().setImageDrawable(centerDrawableCache);
        getRightImageView().setImageDrawable(rightDrawableCache);
        post(new Runnable() {
            @Override
            public void run() {
                setCurrentItemPosition(currentItemPos);
            } 
        });
    }
}