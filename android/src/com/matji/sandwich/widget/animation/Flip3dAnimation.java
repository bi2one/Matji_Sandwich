package com.matji.sandwich.widget.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Flip3dAnimation  extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private Camera mCamera;

    public Flip3dAnimation(float fromDegrees, float toDegrees,
			   float centerX, float centerY) {
	mFromDegrees = fromDegrees;
	mToDegrees = toDegrees;
	mCenterX = centerX;
	mCenterY = centerY;
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
	super.initialize(width, height, parentWidth, parentHeight);
	mCamera = new Camera();
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
	final float fromDegrees = mFromDegrees;
	float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

	final float centerX = mCenterX;
	final float centerY = mCenterY;
	final Camera camera = mCamera;

	final Matrix matrix = t.getMatrix();

	camera.save();

	camera.rotateY(degrees);

	camera.getMatrix(matrix);
	camera.restore();

	matrix.preTranslate(-centerX, -centerY);
	matrix.postTranslate(centerX, centerY);
    }
}