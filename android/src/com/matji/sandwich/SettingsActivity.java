package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.AlarmSetting;
import com.matji.sandwich.data.AlarmSetting.AlarmSettingType;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AlarmHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.HomeTitle;

public class SettingsActivity extends BaseActivity implements OnCheckedChangeListener, Requestable {

	private Session session;

	private HomeTitle title;
	private Drawable iconNew;
	private View logoffStateWrapper;
	private View logonStateWrapper;
	private TextView loginText;
	private TextView registerText;
	private TextView forgetText;
	private TextView nickText;
	private Button logoutButton;
	private View editProfileWrapper;
	//    private CheckBox availableCheck;
	//    private TextView countryText;
	private View alarmTitle;
	private View alarmWrapper;
	private CheckBox commentAlarmCheck;
	private ViewGroup commentAlarmWrapper;
	private CheckBox likeAlarmCheck;
	private ViewGroup likeAlarmWrapper;
	private CheckBox followAlarmCheck;
	private ViewGroup followAlarmWrapper;
	private CheckBox messageAlarmCheck;
	private ViewGroup messageAlarmWrapper;
	private View noticeWrapper;
	private TextView noticeText;
	private TextView guideText;
	private TextView reportText;
	private TextView versionText;

	private boolean isForceChecked = false;

	public HttpRequest request;
	public HttpRequestManager manager;

	@Override
	public int setMainViewId() {
		return R.id.activity_settings;
	}

	@Override
	protected void init() {
		// TODO 일단 연결이 어느정도 되면 코드 정리.
		super.init();
		setContentView(R.layout.activity_settings);

		manager = HttpRequestManager.getInstance(this);

		iconNew = MatjiConstants.drawable(R.drawable.icon_new);
		iconNew.setBounds(0, 0, iconNew.getIntrinsicWidth(), iconNew.getIntrinsicHeight());
		title = (HomeTitle) findViewById(R.id.Titlebar);
		logoffStateWrapper = findViewById(R.id.settings_account_manages_wrapper);
		logonStateWrapper = findViewById(R.id.settings_account_manages_wrapper_atho);
		loginText = (TextView) findViewById(R.id.settings_account_login);
		nickText = (TextView) findViewById(R.id.settings_account_nick);
		logoutButton = (Button) findViewById(R.id.settings_account_logout_btn);
		editProfileWrapper = findViewById(R.id.settings_account_edit_profile);
		noticeWrapper = findViewById(R.id.settings_service_notice_wrapper);
		noticeText = (TextView) findViewById(R.id.settings_service_notice);
		//        availableCheck = (CheckBox) findViewById(R.id.settings_location_information_available_check);

		alarmTitle = findViewById(R.id.settings_alarm_settings);
		alarmWrapper = findViewById(R.id.settings_alarm_settings_wrapper);
		commentAlarmCheck = (CheckBox) findViewById(R.id.settings_alarm_new_comment_alarm_check);
		commentAlarmWrapper = (ViewGroup) findViewById(R.id.settings_alarm_new_comment_alarm);
		likeAlarmCheck = (CheckBox) findViewById(R.id.settings_alarm_new_like_alarm_check);
		likeAlarmWrapper = (ViewGroup) findViewById(R.id.settings_alarm_new_like_alarm);
		followAlarmCheck = (CheckBox) findViewById(R.id.settings_alarm_new_follow_alarm_check);
		followAlarmWrapper = (ViewGroup) findViewById(R.id.settings_alarm_new_follow_alarm);
		messageAlarmCheck = (CheckBox) findViewById(R.id.settings_alarm_new_message_alarm_check);
		messageAlarmWrapper = (ViewGroup) findViewById(R.id.settings_alarm_new_message_alarm);

		title.setTitle(R.string.default_string_settings);
		commentAlarmCheck.setOnCheckedChangeListener(this);
		commentAlarmCheck.setTag(R.string.setting_alarm_permit, AlarmSettingType.COMMENT);
		commentAlarmCheck.setTag(R.string.setting_alarm_view, commentAlarmWrapper);
		likeAlarmCheck.setOnCheckedChangeListener(this);
		likeAlarmCheck.setTag(R.string.setting_alarm_permit, AlarmSettingType.LIKEPOST);
		likeAlarmCheck.setTag(R.string.setting_alarm_view, likeAlarmWrapper);
		followAlarmCheck.setOnCheckedChangeListener(this);
		followAlarmCheck.setTag(R.string.setting_alarm_permit, AlarmSettingType.FOLLOWING);
		followAlarmCheck.setTag(R.string.setting_alarm_view, followAlarmWrapper);
		messageAlarmCheck.setOnCheckedChangeListener(this);
		messageAlarmCheck.setTag(R.string.setting_alarm_permit, AlarmSettingType.MESSAGE);
		messageAlarmCheck.setTag(R.string.setting_alarm_view, messageAlarmWrapper);

		session = Session.getInstance(this);

		editProfileWrapper.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this, UserEditActivity.class);
				intent.putExtra(UserEditActivity.USER, (Parcelable) session.getCurrentUser());
				startActivity(intent);
			}
		});
		
		loginText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});

		logoutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Session.getInstance(SettingsActivity.this).logout();
				refresh();
			}
		});

		noticeWrapper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this, NoticeActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refresh();
	}

	public void refresh() {
		if (session.isLogin()) {
			logonStateWrapper.setVisibility(View.VISIBLE);
			logoffStateWrapper.setVisibility(View.GONE);
			nickText.setText(session.getCurrentUser().getNick());
			alarmTitle.setVisibility(View.VISIBLE);
			alarmWrapper.setVisibility(View.VISIBLE);
			checkBoxRefresh();
		} else {
			logonStateWrapper.setVisibility(View.GONE);
			logoffStateWrapper.setVisibility(View.VISIBLE);
			alarmTitle.setVisibility(View.GONE);
			alarmWrapper.setVisibility(View.GONE);
		}

		refreshIconNew();
	}

	public void refreshIconNew() {
		if (session.getPrivateUtil().getNewNoticeCount() > 0) {
			noticeText.setCompoundDrawables(null, null, iconNew, null);
		} else {
			noticeText.setCompoundDrawables(null, null, null, null);
		}
	}

	public void checkBoxRefresh() {
		if (session.isLogin()) {
			AlarmSetting alarmSetting = session.getCurrentUser().getAlarmSetting();

			isForceChecked = true;
			commentAlarmCheck.setChecked(alarmSetting.getComment());
			likeAlarmCheck.setChecked(alarmSetting.getLikepost());
			followAlarmCheck.setChecked(alarmSetting.getFollowing());
			messageAlarmCheck.setChecked(alarmSetting.getMessage());
			isForceChecked = false;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
		if (!isForceChecked) {
			if (request == null || !(request instanceof AlarmHttpRequest)) { 
				request = new AlarmHttpRequest(this);
			}
			((AlarmHttpRequest) request).actionUpdateAlarmPermit(
					(AlarmSettingType) cb.getTag(R.string.setting_alarm_permit), 
					isChecked);
			manager.request((ViewGroup) cb.getTag(R.string.setting_alarm_view),
					request, HttpRequestManager.UPDATE_ALARM_PERMIT_REQUEST,
					this);
		}
	}

	@Override
	public void requestCallBack(int tag, ArrayList<MatjiData> data) {
		switch (tag) {
		case HttpRequestManager.UPDATE_ALARM_PERMIT_REQUEST:
			session.getCurrentUser().setAlarmSetting((AlarmSetting) data.get(0));
			checkBoxRefresh();
			break;
		}

	}

	@Override
	public void requestExceptionCallBack(int tag, MatjiException e) {
		e.performExceptionHandling(this);        
	}
}