package com.matji.sandwich.widget.pullstate;

import com.matji.sandwich.widget.PullToRefreshListView;

public class ReleaseToRefreshPullState implements PullState {
    private PullToRefreshListView view;
    
    public ReleaseToRefreshPullState(PullToRefreshListView view) {
	this.view = view;
    }
    
    public void onFirstElement() { }
    public void onNotFirstElement() {
	view.resetHeader();
	view.resetSelection();
    }
    
    public void onActionMove() { }
    public void onYCordinateMoveBelow() { }
    public void onYCordinateMoveAboveLessThanLimit() { }
    
    public void onYCordinateMoveAboveLargerThanLimit() {
	view.applyHeaderPadding(view.getMovedY());
    }
    
    public void onYCordinateLessThanZero() { }
    public void onYCordinateLargerThanLimit() { }
    
    public void onYCordinateLessThanLimit() {
	view.startPullToRefreshAnimation();
	view.setState(view.getPullToRefreshState());
    }
    
    public void onActionUp() {
	view.refresh();
	view.setState(view.getReloadingState());
    }
    
    public void onRefreshOk() { }
    public void onScrollIdle() { }
}