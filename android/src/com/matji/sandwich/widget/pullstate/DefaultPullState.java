package com.matji.sandwich.widget.pullstate;

import com.matji.sandwich.widget.PullToRefreshListView;

public class DefaultPullState implements PullState {
    private PullToRefreshListView view;
    
    public DefaultPullState(PullToRefreshListView view) {
	this.view = view;
    }
    
    public void onFirstElement() {
	view.setState(view.getStartState());
    }

    public void onNotFirstElement() { }
    public void onActionMove() { }
    public void onYCordinateMoveBelow() { }
    public void onYCordinateMoveAboveLessThanLimit() { }
    public void onYCordinateMoveAboveLargerThanLimit() { }
    public void onYCordinateLessThanZero() { }
    public void onYCordinateLargerThanLimit() { }
    public void onYCordinateLessThanLimit() { }
    public void onActionUp() { }
    public void onRefreshOk() { }
}