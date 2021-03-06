package com.matji.sandwich.widget.pullstate;

import com.matji.sandwich.widget.PullToRefreshListView;

public class PullToRefreshPullState implements PullState {
    private PullToRefreshListView view;
    
    public PullToRefreshPullState(PullToRefreshListView view) {
	this.view = view;
    }
    
    public void onFirstElement() { }
    public void onNotFirstElement() {
	view.resetHeader();
	view.resetSelection();
	view.setState(view.getDefaultState());
    }
    
    public void onActionMove() { }
    
    public void onYCordinateMoveBelow() { }

    public void onYCordinateMoveAboveLessThanLimit() { }
    
    public void onYCordinateMoveAboveLargerThanLimit() { }
    public void onYCordinateLessThanZero() { }
    
    public void onYCordinateLargerThanLimit() {
	view.startReleaseToRefreshAnimation();
	view.setState(view.getReleaseToRefreshState());
    }
    
    public void onYCordinateLessThanLimit() { }
    
    public void onActionUp() { }
    
    public void onRefreshOk() { }
    public void onScrollIdle() {
	view.resetHeader();
	view.resetSelection();
	view.setState(view.getDefaultState());
    }
}