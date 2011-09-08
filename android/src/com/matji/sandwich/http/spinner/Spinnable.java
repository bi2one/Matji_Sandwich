package com.matji.sandwich.http.spinner;

public interface Spinnable {
    public void setSpinListener(SpinListener listener);
    public void start();
    public void stop();

    public interface SpinListener {
	public void onStart(Spinnable spinnable);
	public void onStop(Spinnable spinnable);
    }
}