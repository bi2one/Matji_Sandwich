package com.matji.sandwich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.widget.pullstate.PullState;
import com.matji.sandwich.widget.pullstate.PullStateFactory;

public abstract class PullToRefreshListView extends MListView implements OnScrollListener {
    private enum State { DEFAULT, START, PULL_TO_REFRESH, RELEASE_TO_REFRESH, REFRESHING };
    private static final int FLIP_ANIMATION_DURATION = 250;
    private static final int HEADER_REFRESH_LIMIT = 70;
    private static final int LIST_BASE_INDEX = 0;
    private static final boolean IS_ON = false;
    private OnScrollListener pullDownListener;
    private OnRefreshListener onRefreshListener;
    private RotateAnimation flipAnimation;
    private RotateAnimation reverseFlipAnimation;
    private LayoutInflater inflater;
    private int baseTopPadding;
    private int refreshViewHeight;
    private int pullStartedY = -1;
    private int currentY;
    private int movedY;
    private int firstVisibleItem;
    private PullStateFactory stateFactory;
    private PullState state;
    private PullState defaultState;
    private PullState startState;
    private PullState pullToRefreshState;
    private PullState releaseToRefreshState;
    private PullState reloadingState;

    // view
    private View refreshView;
    private TextView refreshText;
    private ImageView refreshImage;
    private ProgressBar refreshProgress;
    private TextView refreshLastUpdated;

    /**
     * View의 기본 생성자, 각종 정보를 초기화합니다.
     *
     * @param context
     * @param attrs
     */
    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        flipAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        flipAnimation.setInterpolator(new LinearInterpolator());
        flipAnimation.setDuration(FLIP_ANIMATION_DURATION);
        flipAnimation.setFillAfter(true);

        reverseFlipAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseFlipAnimation.setInterpolator(new LinearInterpolator());
        reverseFlipAnimation.setDuration(FLIP_ANIMATION_DURATION);
        reverseFlipAnimation.setFillAfter(true);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        refreshView = inflater.inflate(R.layout.pull_to_refresh_header, null);
        refreshText = (TextView)refreshView.findViewById(R.id.pull_to_refresh_text);
        refreshImage = (ImageView)refreshView.findViewById(R.id.pull_to_refresh_image);
        refreshProgress = (ProgressBar)refreshView.findViewById(R.id.pull_to_refresh_progress);
        refreshLastUpdated = (TextView)refreshView.findViewById(R.id.pull_to_refresh_updated_at);
        measureView(refreshView);
        refreshViewHeight = refreshView.getMeasuredHeight();
        baseTopPadding = refreshView.getPaddingTop();

        if (IS_ON)
            super.addHeaderView(refreshView);

        stateFactory = new PullStateFactory(this);
        defaultState = stateFactory.getState(PullStateFactory.State.DEFAULT);
        startState = stateFactory.getState(PullStateFactory.State.START);
        pullToRefreshState = stateFactory.getState(PullStateFactory.State.PULL_TO_REFRESH);
        releaseToRefreshState = stateFactory.getState(PullStateFactory.State.RELEASE_TO_REFRESH);
        reloadingState = stateFactory.getState(PullStateFactory.State.RELOADING);
        state = defaultState;

        setOnScrollListener(this);
    }

    /**
     * 윈도우에 이 뷰가 올라갈때 실행하는 메소드, 초기 리스트 위치를 정해준다.
     */
    protected void onAttachedToWindow() {
        resetSelection();
    }

    /**
     * 이 뷰에 연결할 ListAdapter를 설정한다.
     *
     * @param adapter 연결할 ListAdapter
     */
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        resetSelection();
    }

    /**
     * 이 뷰의 refresh동작에 반응하기 위한 리스너를 등록한다.
     *
     * @param onRefreshListener 실제 refresh를 수행하는 listener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    /**
     * 이 클래스에서는 위쪽으로 스크롤 한 것만 지원하므로, 이 함수를
     * 이용해서 아래쪽 스크롤을 담당할 리스너를 세팅한다.
     *
     * @param l 아래쪽 스크롤로 등록할 OnScrollListener
     */
    public void setPullDownScrollListener(OnScrollListener l) {
        pullDownListener = l;
    }

    /**
     * 뷰의 refresh를 담당하는 부분
     */
    public void refresh() {
//        resetHeaderPadding();
        setSelection(0);
        refreshImage.setVisibility(View.GONE);
        refreshImage.setImageDrawable(null);
        refreshProgress.setVisibility(View.VISIBLE);

        refreshText.setText(R.string.pull_to_refresh_refreshing_label);

        onRefresh();
        onRefreshListener.onRefresh();
        setSelection(0);
    }

    public void onRefresh() { };

    /**
     * refreshView의 위쪽 padding을 변경한다.
     *
     * @param topPadding 위쪽 padding값
     */
    public void applyHeaderPadding(int topPadding) {
        refreshView.setPadding(refreshView.getPaddingLeft(),
                topPadding,
                refreshView.getPaddingRight(),
                refreshView.getPaddingBottom());
    }

    /**
     * refreshView의 padding을 원위치 시킨다.
     */
    public void resetHeaderPadding() {
        applyHeaderPadding(baseTopPadding);
    }

    /**
     * refreshView를 초기화하고 원위치 시킨다.
     */
    public void resetHeader() {
        resetHeaderPadding();
        pullStartedY = -1;
        movedY = 0;
        refreshText.setText(R.string.pull_to_refresh_pull_label);
        refreshImage.setImageResource(R.drawable.ic_pulltorefresh_arrow);
        refreshImage.clearAnimation();
        refreshProgress.setVisibility(View.GONE);
        refreshImage.setVisibility(View.VISIBLE);
    }

    /**
     * 인자로 받은 뷰를 측정해서, 가로/세로길이를 리턴할 수 있도록 준비시킨다.
     *
     * @param child 측정 준비할 View
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0,
                0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 뷰의 터치 이벤트를 감시한다. State에 따라 reloading여부를
     * 판단하고 reloading을 한다.
     *
     * @param event 받은 이벤트
     * @return 다른 하위 이벤트 감시 함수들을 추가로 허용할지 여부
     */
    public boolean onTouchEvent(MotionEvent event) {
        if(IS_ON) {
            currentY = (int) event.getY();
            if (pullStartedY != -1)
                movedY = currentY - pullStartedY;

            switch(event.getAction()) {
            case MotionEvent.ACTION_UP:
                state.onActionUp();
                break;
            case MotionEvent.ACTION_MOVE:
                state.onActionMove();
                if (movedY <= HEADER_REFRESH_LIMIT) {
                    state.onYCordinateLessThanLimit();
                } else {
                    state.onYCordinateLargerThanLimit();
                }

                if (movedY < 0) {
                    state.onYCordinateMoveBelow();
                } else if (movedY <= HEADER_REFRESH_LIMIT) {
                    state.onYCordinateMoveAboveLessThanLimit();
                } else {
                    state.onYCordinateMoveAboveLargerThanLimit();
                }

                break;
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * scroll시 실행하는 callback function
     *
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (IS_ON) {
            if (firstVisibleItem == 0) {
                state.onFirstElement();
            } else if (firstVisibleItem != 0) {
                state.onNotFirstElement();
            }
        }

        if (pullDownListener != null)
            pullDownListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch(scrollState) {
        case OnScrollListener.SCROLL_STATE_IDLE:
            state.onScrollIdle();
        }
    }

    /**
     * state의 setter함수
     *
     * @param state 새로 설정할 state
     */
    public void setState(PullState state) {
        this.state = state;
    }

    /**
     * Resets the list to a normal state after a refresh.
     */
    public void onRefreshComplete() {
        if (IS_ON) {
            resetHeader();
            state.onRefreshOk();
            showRefreshView();
        }

        // If refresh view is visible when loading completes, scroll down to
        // the next item.

        // if (refreshView.getBottom() > 0) {
        //     invalidateViews();
        //     resetSelection();
        // }
        // resetSelection();
    }

    public void resetSelection() {
        setSelection(LIST_BASE_INDEX);
    }

    public PullState getDefaultState() {
        return defaultState;
    }

    public PullState getStartState() {
        return startState;
    }

    public PullState getPullToRefreshState() {
        return pullToRefreshState;
    }

    public PullState getReleaseToRefreshState() {
        return releaseToRefreshState;
    }

    public PullState getReloadingState() {
        return reloadingState;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setPullStartedY(int y) {
        pullStartedY = y;
    }

    public int getMovedY() {
        return movedY;
    }

    public void startReleaseToRefreshAnimation() {
        refreshText.setText(R.string.pull_to_refresh_release_label);
        refreshImage.clearAnimation();
        refreshImage.startAnimation(flipAnimation);
    }

    public void startPullToRefreshAnimation() {
        refreshText.setText(R.string.pull_to_refresh_pull_label);
        refreshImage.clearAnimation();
        refreshImage.startAnimation(reverseFlipAnimation);
    }

    /**
     * Interface definition for a callback to be invoked when list should be
     * refreshed.
     */
    public interface OnRefreshListener {
        /**
         * Called when the list should be refreshed.
         * <p>
         * A call to {@link PullToRefreshListView #onRefreshComplete()} is
         * expected to indicate that the refresh has completed.
         */
        public void onRefresh();
    }

    public void hideRefreshView() {
        refreshView.setVisibility(View.GONE);
    }

    public void showRefreshView() {
        refreshView.setVisibility(View.VISIBLE);
    }
}