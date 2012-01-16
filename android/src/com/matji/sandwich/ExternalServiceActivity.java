package com.matji.sandwich;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.widget.title.CompletableTitle;

public class ExternalServiceActivity extends BaseActivity implements CompletableTitle.Completable {
	public static final String DATA_TWITTER = "ExternalServiceActivity.isTwitter";
	public static final String DATA_FACEBOOK = "ExternalServiceActivity.isFacebook";
	public static final String DATA_USER = "ExtenalServiceActivity.user";

	private boolean passedT;
	private boolean passedF;
	
	private Session session;
	private Intent passedIntent;

	private CompletableTitle titleBar;
	private TextView facebookText;
	private TextView twitterText;
	private CheckBox tCheckBox;
	private CheckBox fCheckBox;
		
	public int setMainViewId() {
		return R.id.activity_external_service;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_external_service);
		
		session = Session.getInstance(this);
		
		titleBar = (CompletableTitle) findViewById(R.id.activity_external_service_title);

		titleBar.setTitle(R.string.title_export);
		titleBar.setCompletable(this);

		twitterText = (TextView) findViewById(R.id.export_to_twitter_text);
		facebookText = (TextView) findViewById(R.id.export_to_facebook_text);
		tCheckBox = (CheckBox) findViewById(R.id.twitter_check);
		fCheckBox = (CheckBox) findViewById(R.id.facebook_check);

		passedIntent = getIntent();
		passedT = passedIntent.getBooleanExtra(DATA_TWITTER, false);
		passedF = passedIntent.getBooleanExtra(DATA_FACEBOOK, false);

		if (!session.getCurrentUser().getExternalAccount().isLinkedTwitter()) {
			tCheckBox.setEnabled(false);
			twitterText.setTextColor(R.color.matji_light_gray);
		}
		if (!session.getCurrentUser().getExternalAccount().isLinkedFacebook()) {
			fCheckBox.setEnabled(false);
			facebookText.setTextColor(R.color.matji_light_gray);
		}
		
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
