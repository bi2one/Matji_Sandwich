package com.matji.sandwich.widget.quick;

import com.matji.sandwich.R;

import android.content.Context;

import android.graphics.drawable.Drawable;

import android.widget.ImageView;

import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

public class QuickAction extends PopupWindows {
	private Animation mTrackAnim;
	private LayoutInflater inflater;
	private ViewGroup mTrack;
	private OnActionItemClickListener mListener;

	private int mChildPos;

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            Context
	 */
	public QuickAction(Context context) {
		super(context);

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mTrackAnim = AnimationUtils.loadAnimation(context, R.anim.rail);

		mTrackAnim.setInterpolator(new Interpolator() {
			public float getInterpolation(float t) {
				// Pushes past the target area, then snaps back into place.
				// Equation for graphing: 1.2-((x*1.6)-1.1)^2
				final float inner = (t * 1.55f) - 1.1f;
				return 1.2f - inner * inner;
			}
		});

		setRootViewId(R.layout.quick_action);

		mChildPos = 0;
	}

	/**
	 * Set root view.
	 * 
	 * @param id
	 *            Layout resource id
	 */
	public void setRootViewId(int id) {
		mRootView = (ViewGroup) inflater.inflate(id, null);
		mTrack = (ViewGroup) mRootView.findViewById(R.id.tracks);

		setContentView(mRootView);
	}

	/**
	 * Add action item
	 * 
	 * @param action
	 *            {@link ActionItem}
	 */
	public void addActionItem(ActionItem action) {

		Drawable icon = action.getIcon();

		View container = (View) inflater.inflate(R.layout.action_item, null);

		ImageView img = (ImageView) container.findViewById(R.id.iv_icon);

		final int pos = mChildPos;

		if (icon != null)
			img.setImageDrawable(icon);
		else
			img.setVisibility(View.GONE);

		container.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null)
					mListener.onItemClick(pos);

				dismiss();
			}
		});

		container.setFocusable(true);
		container.setClickable(true);

		mTrack.addView(container, mChildPos + 1);

		mChildPos++;
	}

	public void setOnActionItemClickListener(OnActionItemClickListener listener) {
		mListener = listener;
	}

	/**
	 * Show popup mWindow
	 */
	public void show(View anchor) {
		preShow();

		mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int xPos = anchor.getLeft() - 50;
		int yPos = anchor.getBottom();

		mWindow.setAnimationStyle(R.style.Animations_PopUpMenu_Right);

		mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);

	}

	/**
	 * Listener for item click
	 * 
	 */
	public interface OnActionItemClickListener {
		public abstract void onItemClick(int pos);
	}
}