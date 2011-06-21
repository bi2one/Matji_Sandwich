package com.matji.sandwich.base;

public class ActivityEnterForeGroundDetector{
	public enum ActivityState {NONDETERMIND, ONRESUME, ONSTOP, ONPAUSE} ;
	private static ActivityState state;
	private static volatile ActivityEnterForeGroundDetector activityPref;
	
	
	private ActivityEnterForeGroundDetector(){}
	
	public static ActivityEnterForeGroundDetector getInstance(){
		if (activityPref == null) {
			synchronized(ActivityEnterForeGroundDetector.class) {
				if (activityPref == null) {
					activityPref = new ActivityEnterForeGroundDetector();
					
					state = ActivityState.NONDETERMIND;
				}
			}
		}
		return activityPref;
	}

	public void setState(ActivityState activityState, ActivityEnterForeGroundListener listener) {
		if (state == ActivityState.ONSTOP && activityState == ActivityState.ONRESUME)
			listener.didEnterForeGround();
		
		state = activityState;
	}

	public ActivityState getState() {
		return state;
	}
	
	
	
	
//	private void didEnterForeGround(){
//		Log.d("LifeCycle", "Activity did enter foreground!!!!");
//		if (session.isLogin())
//			session.sessionValidate();
//	}

	
	public interface ActivityEnterForeGroundListener{
		abstract public void didEnterForeGround();
	}
	
}
