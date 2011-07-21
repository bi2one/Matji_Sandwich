package com.matji.sandwich.widget.pullstate;

import com.matji.sandwich.widget.PullToRefreshListView;

public class PullStateFactory {
    public static enum State { DEFAULT, START, PULL_TO_REFRESH, RELEASE_TO_REFRESH, RELOADING }
    PullState defaultState;
    PullState startState;
    PullState pullToRefreshState;
    PullState releaseToRefreshState;
    PullState reloadingState;

    /**
     * 구상 state객체를 생성한다.
     *
     * @param v state객체가 활동할 listview
     */
    public PullStateFactory(PullToRefreshListView v) {
	defaultState = new DefaultPullState(v);
	startState = new StartPullState(v);
	pullToRefreshState = new PullToRefreshPullState(v);
	releaseToRefreshState = new ReleaseToRefreshPullState(v);
	reloadingState = new ReloadingPullState(v);
    }

    /**
     * factory로부터 PullState객체를 리턴한다.
     *
     * @param state 리턴받을 state의 종류
     * @return state에 따라서 리턴되는 PullState객체
     */

    public PullState getState(State state) {
	switch(state) {
	case DEFAULT:
	    return defaultState;
	case START:
	    return startState;
	case PULL_TO_REFRESH:
	    return pullToRefreshState;
	case RELEASE_TO_REFRESH:
	    return releaseToRefreshState;
	case RELOADING:
	    return reloadingState;
	default:
	    return null;
	}
    }
}