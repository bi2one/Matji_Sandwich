package com.matji.sandwich.widget.pullstate;

public interface PullState {
    public void onFirstElement();
    public void onNotFirstElement();
    public void onActionMove();
    public void onYCordinateMoveBelow();
    public void onYCordinateMoveAboveLessThanLimit();
    public void onYCordinateMoveAboveLargerThanLimit();
    public void onYCordinateLessThanZero();
    public void onYCordinateLargerThanLimit();
    public void onYCordinateLessThanLimit();
    public void onActionUp();
    public void onRefreshOk();
    public void onScrollIdle();
}