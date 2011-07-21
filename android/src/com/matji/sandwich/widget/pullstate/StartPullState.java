package com.matji.sandwich.widget.pullstate;

import com.matji.sandwich.widget.PullToRefreshListView;
import android.util.Log;

public class StartPullState implements PullState {
    private PullToRefreshListView view;
    
    public StartPullState(PullToRefreshListView view) {
	this.view = view;
    }
    
    public void onFirstElement() { }
    
    public void onNotFirstElement() {
	// view.resetHeader();
	// view.resetSelection();
	view.setState(view.getDefaultState());
    }
    
    public void onActionMove() {
	view.setPullStartedY(view.getCurrentY());
	view.setState(view.getPullToRefreshState());
    }
    
    public void onYCordinateMoveBelow() { }
    public void onYCordinateMoveAboveLessThanLimit() { }
    public void onYCordinateMoveAboveLargerThanLimit() { }
    public void onYCordinateLessThanZero() { }
    public void onYCordinateLargerThanLimit() { }
    public void onYCordinateLessThanLimit() { }
    public void onActionUp() { }
    
    public void onRefreshOk() {
	view.resetSelection();
    }
    
    public void onScrollIdle() {
	view.resetHeader();
	view.resetSelection();
	view.setState(view.getDefaultState());
    }
}