package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.AlarmSetting;
import com.matji.sandwich.data.AlarmSetting.AlarmSettingType;
import com.matji.sandwich.data.AppVersion;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AlarmHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MeHttpRequest;
import com.matji.sandwich.http.request.MeHttpRequest.Service;
import com.matji.sandwich.http.request.VersionHttpRequest;
import com.matji.sandwich.http.spinner.SpinnerFactory.SpinnerType;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.HomeTitle;

public class SettingsActivity extends BaseActivity implements OnCheckedChangeListener, Requestable, OnClickListener {

    private Session session;

    private Toast notExistEmailAppToast;
    
    private HomeTitle title;
    
    private TextView tvAccountManage;
    private TextView tvNick;
    private TextView tvLinkTwitter;
    private TextView tvLinkFacebook;
    private TextView tvNotice;
    private TextView tvGuide;
    private TextView tvReport;
    private TextView tvVersion;
    private View accountManageWrapper;
    private View editProfileWrapper;
    private View alarmTitle;
    private View alarmWrapper;
    private ViewGroup commentAlarmSpinner;
    private ViewGroup likeAlarmSpinner;
    private ViewGroup followAlarmSpinner;
    private ViewGroup messageAlarmSpinner;
    private CheckBox cbCommentAlarm;
    private CheckBox cbLikeAlarm;
    private CheckBox cbFollowAlarm;
    private CheckBox cbMessageAlarm;
    private Button btnLogout;
    private Button updateButton;
    private String update_ver;
    private Drawable iconNew;
    private View noticeWrapper;

    private boolean isForceChecked = false;

    public HttpRequest request;
    public HttpRequestManager manager;

    @Override
    public int setMainViewId() {
        return R.id.activity_settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    
    private void init() {
        setContentView(R.layout.activity_settings);

        notExistEmailAppToast = Toast.makeText(this, MatjiConstants.string(R.string.not_exist_email_client_app), Toast.LENGTH_SHORT);
        
        session = Session.getInstance(this);
        manager = HttpRequestManager.getInstance();

        iconNew = MatjiConstants.drawable(R.drawable.icon_new);
        iconNew.setBounds(0, 0, iconNew.getIntrinsicWidth(), iconNew.getIntrinsicHeight());

        findViews();

        title.setTitle(R.string.default_string_settings);
        tvVersion.setText("Ver " + this.getResources().getString(R.string.settings_service_version_name));

        setTags();
        setListeners();
        
        updateRequest();
    }

	private void findViews() {
        title = (HomeTitle) findViewById(R.id.Titlebar);
        tvAccountManage = (TextView) findViewById(R.id.settings_account_manages);
        accountManageWrapper = findViewById(R.id.settings_account_manages_wrapper_atho);
        tvNick = (TextView) findViewById(R.id.settings_account_nick);
        btnLogout = (Button) findViewById(R.id.settings_account_logout_btn);
        tvLinkTwitter = (TextView) findViewById(R.id.settings_account_link_twitter);
        tvLinkFacebook = (TextView) findViewById(R.id.settings_account_link_facebook);
        editProfileWrapper = findViewById(R.id.settings_account_edit_profile);
        noticeWrapper = findViewById(R.id.settings_service_notice_wrapper);
        tvNotice = (TextView) findViewById(R.id.settings_service_notice);
        tvGuide = (TextView) findViewById(R.id.settings_service_guide);
        tvReport = (TextView) findViewById(R.id.settings_service_report);
        tvVersion = (TextView) findViewById(R.id.settings_service_version_name);
        updateButton = (Button) findViewById(R.id.settings_service_update_btn);
        alarmTitle = findViewById(R.id.settings_alarm_settings);
        alarmWrapper = findViewById(R.id.settings_alarm_settings_wrapper);
        cbCommentAlarm = (CheckBox) findViewById(R.id.settings_alarm_new_comment_alarm_check);
        commentAlarmSpinner = (ViewGroup) findViewById(R.id.settings_alarm_new_comment_alarm_spinner);
        cbLikeAlarm = (CheckBox) findViewById(R.id.settings_alarm_new_like_alarm_check);
        likeAlarmSpinner = (ViewGroup) findViewById(R.id.settings_alarm_new_like_alarm_spinner);
        cbFollowAlarm = (CheckBox) findViewById(R.id.settings_alarm_new_follow_alarm_check);
        followAlarmSpinner = (ViewGroup) findViewById(R.id.settings_alarm_new_follow_alarm_spinner);
        cbMessageAlarm = (CheckBox) findViewById(R.id.settings_alarm_new_message_alarm_check);
        messageAlarmSpinner = (ViewGroup) findViewById(R.id.settings_alarm_new_message_alarm_spinner);
    }

    private void setTags() {
        cbCommentAlarm.setTag(R.string.setting_alarm_permit, AlarmSettingType.COMMENT);
        cbCommentAlarm.setTag(R.string.setting_alarm_spinner, commentAlarmSpinner);
        cbLikeAlarm.setTag(R.string.setting_alarm_permit, AlarmSettingType.LIKEPOST);
        cbLikeAlarm.setTag(R.string.setting_alarm_spinner, likeAlarmSpinner);
        cbFollowAlarm.setTag(R.string.setting_alarm_permit, AlarmSettingType.FOLLOWING);
        cbFollowAlarm.setTag(R.string.setting_alarm_spinner, followAlarmSpinner);
        cbMessageAlarm.setTag(R.string.setting_alarm_permit, AlarmSettingType.MESSAGE);
        cbMessageAlarm.setTag(R.string.setting_alarm_spinner, messageAlarmSpinner);
    }

    private void setListeners() {
        cbCommentAlarm.setOnCheckedChangeListener(this);
        cbLikeAlarm.setOnCheckedChangeListener(this);
        cbFollowAlarm.setOnCheckedChangeListener(this);
        cbMessageAlarm.setOnCheckedChangeListener(this);
        editProfileWrapper.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        tvLinkTwitter.setOnClickListener(this);
        tvLinkFacebook.setOnClickListener(this);
        noticeWrapper.setOnClickListener(this);        
        tvGuide.setOnClickListener(this);
        tvReport.setOnClickListener(this);
        updateButton.setOnClickListener(this);
    }

    private void updateRequest() {
    	String current_ver = MatjiConstants.string(R.string.settings_service_version_name);
    	VersionHttpRequest request = new VersionHttpRequest(SettingsActivity.this);
    	request.actionAppVersion("ANDROID", current_ver);

    	try {
			ArrayList<MatjiData> data = request.request();
			if (data != null && data.size() > 0) {
				AppVersion app_version = (AppVersion) data.get(0);
				update_ver = app_version.getVersion();
			} else {
				update_ver = current_ver;
			}
		} catch (MatjiException e) {
			e.performExceptionHandling(SettingsActivity.this);
		}

    	if (update_ver == current_ver) {
    		updateButton.setVisibility(View.GONE);
    	}
	}

    public void editProfileClicked() {
        Intent intent = new Intent(this, UserEditActivity.class);
        startActivity(intent);
    }

    public void logoutClicked() {                
        Session.getInstance(this).logout();
        refresh();
    }

    public void linkTwitterClicked() {
        if (session.isLogin()) {
            MeHttpRequest request = new MeHttpRequest(this);
            if (session.getCurrentUser().getExternalAccount().isLinkedTwitter()) {
                request.deleteExternalAccount(this, Service.TWITTER);
                manager.request(
                        this,
                        (ViewGroup) findViewById(R.id.settings_account_link_twitter_wrapper),
                        SpinnerType.SSMALL,
                        request,
                        HttpRequestManager.TWITTER_EXTERNAL_ACCOUNT_DELETE_REQUEST,
                        this);
            } else {
                request.newExternalAccount(this, Service.TWITTER);
            }
        }
    }

    public void linkFacebookClicked() {
        if (session.isLogin()) {
            MeHttpRequest request = new MeHttpRequest(this);
            if (session.getCurrentUser().getExternalAccount().isLinkedFacebook()) {
                request.deleteExternalAccount(this, Service.FACEBOOK);
                manager.request(
                        this,
                        (ViewGroup) findViewById(R.id.settings_account_link_facebook_wrapper),
                        SpinnerType.SSMALL,
                        request,
                        HttpRequestManager.FACEBOOK_EXTERNAL_ACCOUNT_DELETE_REQUEST,
                        this);
            } else {
                request.newExternalAccount(this, Service.FACEBOOK);
            }
        }
    }

    public void noticeClicked() {                
        Intent intent = new Intent(this, NoticeActivity.class);
        startActivity(intent);
    }

    public void guideClicked() {
        Intent intent = new Intent(this, ServiceGuideActivity.class);
        startActivity(intent);
    }
    
    public void reportClicked() {
        
        String[] matjiEmail = {MatjiConstants.string(R.string.default_string_matji_email)};
        String title = (session.isLogin()) ?
                String.format(
                        MatjiConstants.string(R.string.settings_service_report_title),
                        session.getCurrentUser().getNick())
                :MatjiConstants.string(R.string.settings_service_report_title_guest);
        String content = MatjiConstants.string(R.string.settings_service_report_content);
        
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("plain/text");
        i.putExtra(Intent.EXTRA_EMAIL, matjiEmail);
        i.putExtra(Intent.EXTRA_SUBJECT, title);
        i.putExtra(Intent.EXTRA_TEXT, content);
        try{
            startActivity(Intent.createChooser(i, MatjiConstants.string(R.string.settings_service_report)));
        }catch(android.content.ActivityNotFoundException ex){
            notExistEmailAppToast.show();
        }
        
    }
    
    public void updateClicked() {
        String marketAddr = MatjiConstants.string(R.string.default_string_market_address);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(marketAddr));
        startActivity(intent);
    }




    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        refresh();
    }

    public void refresh() {
        if (session.isLogin()) {
            tvAccountManage.setVisibility(View.VISIBLE);
            accountManageWrapper.setVisibility(View.VISIBLE);
            tvNick.setText(session.getCurrentUser().getNick());
            alarmTitle.setVisibility(View.VISIBLE);
            alarmWrapper.setVisibility(View.VISIBLE);
            refreshExternalAccount();
            checkBoxRefresh();
        } else {
            tvAccountManage.setVisibility(View.GONE);
            accountManageWrapper.setVisibility(View.GONE);
            alarmTitle.setVisibility(View.GONE);
            alarmWrapper.setVisibility(View.GONE);
        }

        refreshIconNew();
    }

    public void refreshExternalAccount() {
        if (session.isLogin()) {
            User user = session.getCurrentUser();
            tvLinkTwitter.setText(
                    user.getExternalAccount().isLinkedTwitter() ?
                            R.string.settings_account_unlink_twitter
                            : R.string.settings_account_link_twitter);

            tvLinkFacebook.setText(
                    user.getExternalAccount().isLinkedFacebook() ?
                            R.string.settings_account_unlink_facebook
                            : R.string.settings_account_link_facebook);
        }
    }

    public void refreshIconNew() {
        if (session.getPrivateUtil().getNewNoticeCount() > 0) {
            tvNotice.setCompoundDrawables(null, null, iconNew, null);
        } else {
            tvNotice.setCompoundDrawables(null, null, null, null);
        }
    }

    public void checkBoxRefresh() {
        if (session.isLogin()) {
            AlarmSetting alarmSetting = session.getCurrentUser().getAlarmSetting();

            isForceChecked = true;
            cbCommentAlarm.setChecked(alarmSetting.getComment());
            cbLikeAlarm.setChecked(alarmSetting.getLikepost());
            cbFollowAlarm.setChecked(alarmSetting.getFollowing());
            cbMessageAlarm.setChecked(alarmSetting.getMessage());
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
                manager.request(getApplicationContext(), (ViewGroup) cb.getTag(R.string.setting_alarm_spinner),
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
        case HttpRequestManager.TWITTER_EXTERNAL_ACCOUNT_DELETE_REQUEST:
            session.getCurrentUser().getExternalAccount().setLinkedTwitter(false);
            refreshExternalAccount();
            break;
        case HttpRequestManager.FACEBOOK_EXTERNAL_ACCOUNT_DELETE_REQUEST:
            session.getCurrentUser().getExternalAccount().setLinkedFacebook(false);
            refreshExternalAccount();
            break;
        }

    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(this);        
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == editProfileWrapper.getId() ) {
            editProfileClicked();
        } else if (viewId == btnLogout.getId()) {
            logoutClicked();
        } else if (viewId == tvLinkTwitter.getId()) {
            linkTwitterClicked();
        } else if (viewId == tvLinkFacebook.getId()) {
            linkFacebookClicked();
        } else if (viewId == noticeWrapper.getId()) {
            noticeClicked();
        } else if (viewId == tvGuide.getId()) {
            guideClicked();
        } else if (viewId == tvReport.getId()) {
            reportClicked();
        } else if (viewId == updateButton.getId()) {
            updateClicked();
        }
    }
}