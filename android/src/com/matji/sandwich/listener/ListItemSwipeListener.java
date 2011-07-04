package com.matji.sandwich.listener;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.RequestableMListView;

public abstract class ListItemSwipeListener extends SwipeGestureListener {
	public abstract void onListItemClicked(int position);
	public abstract boolean isMyItem(int position);
	
	private RequestableMListView listView;

	private int frontId;
	private int rearId;

	private View currentFront;
	private View currentRear;
	private View prevFront;
	private View prevRear;

	private int headerPos;
	private int prevPosition = -1;
	private boolean hasAnimationStarted;

	private Animation rightIn;
	private Animation rightOut;
	private Animation leftIn;
	private Animation leftOut;

	private Animation prevLeftIn;
	private Animation prevRightIn;

	public ListItemSwipeListener(Context context, RequestableMListView listView, int frontId, int rearId, int headerPos) {
		super(context);
		this.listView = listView;
		this.frontId = frontId;
		this.rearId = rearId;
		this.headerPos = headerPos;

		rightIn = AnimationUtils.loadAnimation(context, R.anim.to_right_in);
		rightOut = AnimationUtils.loadAnimation(context, R.anim.to_right_out);
		leftIn = AnimationUtils.loadAnimation(context, R.anim.to_left_in);
		leftOut = AnimationUtils.loadAnimation(context, R.anim.to_left_out);

		prevRightIn = AnimationUtils.loadAnimation(context, R.anim.to_right_in);
		prevLeftIn = AnimationUtils.loadAnimation(context, R.anim.to_left_in);

		rightIn.setAnimationListener(new InAnimationListener());
		rightOut.setAnimationListener(new OutAnimationListener());
		leftIn.setAnimationListener(new InAnimationListener());
		leftOut.setAnimationListener(new OutAnimationListener());

		prevRightIn.setAnimationListener(new PrevInAnimationListener());
		prevLeftIn.setAnimationListener(new PrevInAnimationListener());
	}

	private int getPosition(int x, int y) {
		return listView.pointToPosition(x, y) + headerPos;
	}
	
	private int getChildAtPosition(int position) {
		return position - listView.getFirstVisiblePosition() - headerPos;
	}

	public void initItemVisible() {
		prevPosition = -1;
		hasAnimationStarted = false;

		View frontView;
		View rearView;
		for (int i = 0; i < listView.getChildCount(); i++) {
			frontView = listView.getChildAt(i).findViewById(frontId);
			rearView = listView.getChildAt(i).findViewById(rearId);
			if (frontView != null && rearView != null) {
				frontView.setVisibility(View.VISIBLE);
				rearView.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onItemClicked(int x, int y) {
		int position = getPosition(x, y);
		if (position <= headerPos) return;
		View item = listView.getChildAt(getChildAtPosition(position));
		if (item != null) {
			View thread = item.findViewById(frontId);
			if (position > 0 && thread.getVisibility() == View.VISIBLE) {
				onListItemClicked(position);
			}
		}
	};

	public void swipeAnimation(int position, Animation inAnim, Animation outAnim, Animation prevInAnim) {
		int currentViewPosition = getChildAtPosition(position);
		int prevViewPositoin = getChildAtPosition(prevPosition);

		if (isMyItem(position-headerPos-1) && !hasAnimationStarted && listView.getChildAt(currentViewPosition) != null) {
			currentFront = listView.getChildAt(currentViewPosition).findViewById(frontId);
			currentRear = listView.getChildAt(currentViewPosition).findViewById(rearId);

			switch (currentFront.getVisibility()) {
			case View.GONE:
				currentFront.startAnimation(inAnim);
				if (prevPosition == position) {
					prevPosition = -1;
				}
				break;
			case View.VISIBLE:
				if (prevPosition > 0 
						&& (prevPosition < listView.getFirstVisiblePosition() || prevPosition - headerPos > listView.getLastVisiblePosition())) {
					prevPosition = -1;
				}
				currentFront.startAnimation(outAnim);

				if (prevPosition > 0 && prevPosition != position) {
					prevFront = listView.getChildAt(prevViewPositoin).findViewById(frontId);
					prevRear = listView.getChildAt(prevViewPositoin).findViewById(rearId);
					if (prevFront.getVisibility() != View.VISIBLE) {
						prevFront.startAnimation(prevInAnim);
					}
				}
				prevPosition = position;
				break;
			}
		}
	}

	@Override
	public void swipeToLeft(int x, int y) {
		int position = getPosition(x, y);
		swipeAnimation(position, leftIn, leftOut, prevRightIn);
	}

	@Override
	public void swipeToRight(int x, int y) {
		int position = getPosition(x, y);
		swipeAnimation(position, rightIn, rightOut, prevLeftIn);
	}



	class PrevInAnimationListener implements AnimationListener {

		
		public void onAnimationEnd(Animation arg0) {
			prevRear.setVisibility(View.GONE);
		}

		
		public void onAnimationRepeat(Animation arg0) {}

		
		public void onAnimationStart(Animation arg0) {
			prevFront.setVisibility(View.VISIBLE);
		}
	}

	class InAnimationListener implements AnimationListener {
		
		public void onAnimationEnd(Animation arg0) {
			currentRear.setVisibility(View.GONE);
			hasAnimationStarted = false;
		}

		public void onAnimationRepeat(Animation arg0) {}

		
		public void onAnimationStart(Animation arg0) {
			currentFront.setVisibility(View.VISIBLE);
			hasAnimationStarted = true;
		}
	}

	class OutAnimationListener implements AnimationListener {

		
		public void onAnimationEnd(Animation arg0) {
			currentFront.setVisibility(View.GONE);
			hasAnimationStarted = false;
		} 

		public void onAnimationRepeat(Animation arg0) {}

		
		public void onAnimationStart(Animation arg0) {
			currentRear.setVisibility(View.VISIBLE);
			hasAnimationStarted = true;
		}
	}
}