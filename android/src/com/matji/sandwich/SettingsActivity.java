package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
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
import com.matji.sandwich.http.spinner.SpinnerFactory.SpinnerType;
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
    private ViewGroup commentAlarmSpinner;
    private CheckBox likeAlarmCheck;
    private ViewGroup likeAlarmSpinner;
    private CheckBox followAlarmCheck;
    private ViewGroup followAlarmSpinner;
    private CheckBox messageAlarmCheck;
    private ViewGroup messageAlarmSpinner;
    private View noticeWrapper;
    private TextView noticeText;
    private TextView guideText;
    private TextView reportText;
    private TextView versionText;
    private Button updateButton;
    
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
        registerText = (TextView) findViewById(R.id.settings_account_register);
        editProfileWrapper = findViewById(R.id.settings_account_edit_profile);
        noticeWrapper = findViewById(R.id.settings_service_notice_wrapper);
        noticeText = (TextView) findViewById(R.id.settings_service_notice);
        versionText = (TextView) findViewById(R.id.settings_service_version_name);
        versionText.setText("Ver " + this.getResources().getString(R.string.settings_service_version_name));
        updateButton = (Button) findViewById(R.id.settings_service_update_btn);
        //        availableCheck = (CheckBox) findViewById(R.id.settings_location_information_available_check);

        alarmTitle = findViewById(R.id.settings_alarm_settings);
        alarmWrapper = findViewById(R.id.settings_alarm_settings_wrapper);
        commentAlarmCheck = (CheckBox) findViewById(R.id.settings_alarm_new_comment_alarm_check);
        commentAlarmSpinner = (ViewGroup) findViewById(R.id.settings_alarm_new_comment_alarm_spinner);
        likeAlarmCheck = (CheckBox) findViewById(R.id.settings_alarm_new_like_alarm_check);
        likeAlarmSpinner = (ViewGroup) findViewById(R.id.settings_alarm_new_like_alarm_spinner);
        followAlarmCheck = (CheckBox) findViewById(R.id.settings_alarm_new_follow_alarm_check);
        followAlarmSpinner = (ViewGroup) findViewById(R.id.settings_alarm_new_follow_alarm_spinner);
        messageAlarmCheck = (CheckBox) findViewById(R.id.settings_alarm_new_message_alarm_check);
        messageAlarmSpinner = (ViewGroup) findViewById(R.id.settings_alarm_new_message_alarm_spinner);

        title.setTitle(R.string.default_string_settings);
        commentAlarmCheck.setOnCheckedChangeListener(this);
        commentAlarmCheck.setTag(R.string.setting_alarm_permit, AlarmSettingType.COMMENT);
        commentAlarmCheck.setTag(R.string.setting_alarm_spinner, commentAlarmSpinner);
        likeAlarmCheck.setOnCheckedChangeListener(this);
        likeAlarmCheck.setTag(R.string.setting_alarm_permit, AlarmSettingType.LIKEPOST);
        likeAlarmCheck.setTag(R.string.setting_alarm_spinner, likeAlarmSpinner);
        followAlarmCheck.setOnCheckedChangeListener(this);
        followAlarmCheck.setTag(R.string.setting_alarm_permit, AlarmSettingType.FOLLOWING);
        followAlarmCheck.setTag(R.string.setting_alarm_spinner, followAlarmSpinner);
        messageAlarmCheck.setOnCheckedChangeListener(this);
        messageAlarmCheck.setTag(R.string.setting_alarm_permit, AlarmSettingType.MESSAGE);
        messageAlarmCheck.setTag(R.string.setting_alarm_spinner, messageAlarmSpinner);

        session = Session.getInstance(this);

        editProfileWrapper.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, UserEditActivity.class);
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
        
        registerText.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(SettingsActivity.this, RegisterActivity.class);
                startActivity(intent);                
            }
        });
        
        updateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=맛있는지도"));
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
            if (!manager.isRunning()) {
                manager.request((ViewGroup) cb.getTag(R.string.setting_alarm_spinner),
                        SpinnerType.SSMALL,
                        request, HttpRequestManager.UPDATE_ALARM_PERMIT_REQUEST,
                        this);
            }
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