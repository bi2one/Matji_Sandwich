package com.matji.sandwich;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.http.util.MatjiImageDownloader;

public abstract class MainActivity extends BaseActivity {
	protected MatjiImageDownloader downloader = new MatjiImageDownloader();

	public String getCount(int id, int count) {
		return getString(id) + ": " + count;
	}
	
	public String getCountNumberOf(int id, int count) {
		return getString(id) + ": " + count + getString(R.string.default_string_number_of);
	}
}
