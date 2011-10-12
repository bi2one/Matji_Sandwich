package com.matji.sandwich.util.async;


public class SimpleAsyncTask extends Thread implements Threadable {
    private Runnable runnable;
    private ProgressListener listener;
    
    public SimpleAsyncTask(Runnable runnable) {
	this.runnable = runnable;
    }

    public void setProgressListener(ProgressListener listener) {
	this.listener = listener;
    }

    public void run() {
	runnable.run();
	if (listener != null) listener.onFinish(this);
    }

    public interface ProgressListener {
	public void onStart(Threadable task);
	public void onFinish(Threadable task);
    }

    public void execute() {
	if (listener != null) listener.onStart(this);
	start();
    }
}
