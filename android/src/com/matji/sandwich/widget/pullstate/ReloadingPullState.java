package com.matji.sandwich.widget.pullstate;

import com.matji.sandwich.widget.PullToRefreshListView;

public class ReloadingPullState implements PullState {
    private PullToRefreshListView view;
    
    public ReloadingPullState(PullToRefreshListView view) {
	this.view = view;
    }
    
    public void onFirstElement() { }
    public void onNotFirstElement() { }
    public void onActionMove() { }
    public void onYCordinateMoveBelow() { }
    public void onYCordinateMoveAboveLessThanLimit() { }
    public void onYCordinateMoveAboveLargerThanLimit() { }
    public void onYCordinateLessThanZero() { }
    public void onYCordinateLargerThanLimit() { }
    public void onYCordinateLessThanLimit() { }
    public void onActionUp() { }
    public void onRefreshOk() {
	view.resetHeader();
	view.resetSelection();
	view.setState(view.getDefaultState());
    }
}