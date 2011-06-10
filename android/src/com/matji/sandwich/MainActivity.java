package com.matji.sandwich;

import com.matji.sandwich.http.util.MatjiImageDownloader;

import android.app.Activity;

public abstract class MainActivity extends Activity {
	protected MatjiImageDownloader downloader = new MatjiImageDownloader();
	
	public abstract void initInfo();
	public abstract void setInfo();

	public String getCount(int id, int count) {
		return getString(id) + ": " + count;
	}
	
	public String getCountNumberOf(int id, int count) {
		return getString(id) + ": " + count + getString(R.string.default_string_number_of);
	}
}
