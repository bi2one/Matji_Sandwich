package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.widget.title.CompletableTitle;

public class ExternalServiceActivity extends BaseActivity implements CompletableTitle.Completable {
	public static final String DATA_TWITTER = "ExternalServiceActivity.isTwitter";
	public static final String DATA_FACEBOOK = "ExternalServiceActivity.isFacebook";
	private CompletableTitle titleBar;
	private CheckBox tCheckBox;
	private CheckBox fCheckBox;
	private Intent passedIntent;
	private Boolean passedT;
	private Boolean passedF;
	public int setMainViewId() {
		return R.id.activity_external_service;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_external_service);
		
		titleBar = (CompletableTitle) findViewById(R.id.activity_external_service_title);

		titleBar.setTitle(R.string.title_export);
		titleBar.setCompletable(this);
		
		passedIntent = getIntent();
		passedT = passedIntent.getBooleanExtra(DATA_TWITTER, false);
		passedF = passedIntent.getBooleanExtra(DATA_FACEBOOK, false);
		tCheckBox = (CheckBox) findViewById(R.id.twitter_check);
		fCheckBox = (CheckBox) findViewById(R.id.facebook_check);
		tCheckBox.setChecked(passedT);
		fCheckBox.setChecked(passedF);
		
	}

	public void complete() {
		Intent result = new Intent();
		result.putExtra(DATA_TWITTER, tCheckBox.isChecked());
		result.putExtra(DATA_FACEBOOK, fCheckBox.isChecked());
		setResult(RESULT_OK,result);
		finish();
	}
}
