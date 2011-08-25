package com.matji.sandwich.widget.dialog;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.matji.sandwich.R;

/**
 * Quickaction window.
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 *
 */
public class QuickActionDialog extends PopupWindows {
	private Animation mTrackAnim;
	private LayoutInflater inflater;
	private ViewGroup mTrack;
	private OnActionItemClickListener mListener;

//	private int animStyle;
	private int mChildPos;
	private boolean animateTrack;

	public static final int ANIM_GROW_FROM_LEFT = 1;
	public static final int ANIM_GROW_FROM_RIGHT = 2;
	public static final int ANIM_GROW_FROM_CENTER = 3;
	public static final int ANIM_AUTO = 4;

	/**
	 * Constructor.
	 * 
	 * @param context Context
	 */
	public QuickActionDialog(Context context) {
		super(context);

		inflater 	= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mTrackAnim 	= AnimationUtils.loadAnimation(context, R.anim.rail);

		mTrackAnim.setInterpolator(new Interpolator() {
			public float getInterpolation(float t) {
	              // Pushes past the target area, then snaps back into place.
	                // Equation for graphing: 1.2-((x*1.6)-1.1)^2
				final float inner = (t * 1.55f) - 1.1f;

	            return 1.2f - inner * inner;
	        }
		});

		setRootViewId(R.layout.dialog_quick_action);

//		animStyle		= ANIM_AUTO;
		animateTrack	= true;
		mChildPos		= 0;
	}

	/**
	 * Set root view.
	 * 
	 * @param id Layout resource id
	 */
	public void setRootViewId(int id) {
		mRootView	= (ViewGroup) inflater.inflate(id, null);
		mTrack 		= (ViewGroup) mRootView.findViewById(R.id.tracks);


		setContentView(mRootView);
	}

	/**
	 * Animate track.
	 * 
	 * @param animateTrack flag to animate track
	 */
	public void animateTrack(boolean animateTrack) {
		this.animateTrack = animateTrack;
	}

//	/**
//	 * Set animation style.
//	 * 
//	 * @param animStyle animation style, default is set to ANIM_AUTO
//	 */
//	public void setAnimStyle(int animStyle) {
//		this.animStyle = animStyle;
//	}

	/**
	 * Add action item
	 * 
	 * @param action  {@link ActionItem}
	 */
	public void addActionItem(ActionItem action) {

		Drawable icon 	= action.getIcon();

		View container	= (View) inflater.inflate(R.layout.action_item, null);

		ImageButton imgbtn 	= (ImageButton) container.findViewById(R.id.action_item);

		if (icon != null) 
			imgbtn.setImageDrawable(icon);
		else
			imgbtn.setVisibility(View.GONE);

		final int pos =  mChildPos;

		imgbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) mListener.onItemClick(pos);

				dismiss();
			}
		});

		container.setFocusable(true);
		container.setClickable(true);

		ImageView line = new ImageView(mContext);
		line.setImageResource(R.drawable.memo_popupmenu_line);
		line.setScaleType(ScaleType.FIT_XY);
		line.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
		
		
		if (mChildPos > 0) mTrack.addView(line);
		mTrack.addView(container);
		mChildPos++;
	}

	public int getChildCount() {
		return mTrack.getChildCount();
	}
	
	public void setOnActionItemClickListener(OnActionItemClickListener listener) {
		mListener = listener;
	}

	/**
	 * Show popup mWindow
	 */
	public void show (View anchor) {
		preShow();

		int[] location 		= new int[2];

		anchor.getLocationOnScreen(location);

		Rect anchorRect 	= new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] 
		                	+ anchor.getHeight());

		mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int rootWidth 		= mRootView.getMeasuredWidth();
		int rootHeight 		= mRootView.getMeasuredHeight();

		int screenWidth 	= mWindowManager.getDefaultDisplay().getWidth();
		//int screenHeight 	= mWindowManager.getDefaultDisplay().getHeight();

		int xPos            = location[0] - rootWidth;        int yPos            = location[1] - rootHeight/2 + anchor.getHeight()/2;

		setAnimationStyle(screenWidth, anchorRect.centerX());

		mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);

		if (animateTrack) mTrack.startAnimation(mTrackAnim);
	}
    
	/**
	 * Set animation style
	 * 
	 * @param screenWidth Screen width
	 * @param requestedX distance from left screen
	 * @param onTop flag to indicate where the popup should be displayed. Set TRUE if displayed on top of anchor and vice versa
	 */
	private void setAnimationStyle(int screenWidth, int requestedX) {
//		switch (animStyle) {
//		case ANIM_GROW_FROM_LEFT:
//			mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
//			break;

//		case ANIM_GROW_FROM_RIGHT:
//        mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Right : R.style.Animations_PopDownMenu_Right);
        mWindow.setAnimationStyle(R.style.Animations_PopUpMenu_ToLeft);        
//			break;
//
//		case ANIM_GROW_FROM_CENTER:
//			mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
//		break;
//
//		case ANIM_AUTO:
//			if (arrowPos <= screenWidth/4) {
//				mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
//			} else if (arrowPos > screenWidth/4 && arrowPos < 3 * (screenWidth/4)) {
//				mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
//			} else {
//				mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopDownMenu_Right : R.style.Animations_PopDownMenu_Right);
//			}
//
//			break;
//		}
	}

	/**
	 * Listener for item click
	 *
	 */
	public interface OnActionItemClickListener {
		public abstract void onItemClick(int pos);
	}
}