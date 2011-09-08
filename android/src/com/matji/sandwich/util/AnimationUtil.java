package com.matji.sandwich.util;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.matji.sandwich.widget.animation.Flip3dAnimation;
import com.matji.sandwich.widget.animation.DisplayNextView;

public class AnimationUtil {
    private static final long ANIMATION_DURATION = 300;
    /**
     * 관성이 적용된 slide애니메이션 객체를 리턴한다.
     *
     * @param fromXDelta 움직일 X좌표의 시작점
     * @param toXDelta 움직일 X좌표의 끝점
     * @param fromYDelta 움직일 Y좌표의 시작점
     * @param toYDelta 움직일 Y좌표의 끝점
     *
     * @return 관성이 적용된 slide애니메이션 객체
     */
    public static Animation getAccelerateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        Animation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, fromXDelta,
						     Animation.RELATIVE_TO_PARENT, toXDelta,
						     Animation.RELATIVE_TO_PARENT, fromYDelta,
						     Animation.RELATIVE_TO_PARENT, toYDelta);
        animation.setDuration(ANIMATION_DURATION);
        animation.setInterpolator(new AccelerateInterpolator());
        return animation;
    }

    public static Animation outToTopAnimation() {
	return getAccelerateAnimation(0.0f, 0.0f, 0.0f, -1.0f);
    }

    public static Animation upToTopAnimation() {
	return getAccelerateAnimation(0.0f, 0.0f, 0.0f, -0.3f);
    }
    
    /**
     * 오른쪽에서 나타나는 애니메이션
     *
     * @return 오른쪽에서 나타나게 하는 애니메이션 객체
     */
    public static Animation inFromRightAnimation() {
        return getAccelerateAnimation(+1.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * 왼쪽에서 나타나는 애니메이션
     *
     * @return 왼쪽에서 나타나게 하는 애니메이션 객체
     */
    public static Animation inFromLeftAnimation() {
        return getAccelerateAnimation(-1.0f, 0.0f, 0.0f, 0.0f);
    }

    /**
     * 왼쪽으로 사라지는 애니메이션
     *
     * @return 왼쪽으로 사라지게 하는 애니메이션 객체
     */
    public static Animation outToLeftAnimation() {
        return getAccelerateAnimation(0.0f, -1.0f, 0.0f, 0.0f);
    }

    /**
     * 오른쪽으로 사라지는 애니메이션
     *
     * @return 오른쪽으로 사라지게 하는 애니메이션 객체
     */
    public static Animation outToRightAnimation() {
        return getAccelerateAnimation(0.0f, +1.0f, 0.0f, 0.0f);
    }
}
