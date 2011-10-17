package com.matji.sandwich;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.AppVersion;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.request.VersionHttpRequest;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.async.SimpleAsyncTask;
import com.matji.sandwich.util.async.Threadable;
import com.matji.sandwich.util.async.TimeAsyncTask;
import com.matji.sandwich.widget.dialog.BrandingDialog;
import com.matji.sandwich.widget.dialog.BrandingDialog.BrandingDialogListener;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;

public class IntroActivity extends BaseActivity implements TimeAsyncTask.TimeListener, 
SimpleAsyncTask.ProgressListener,
BrandingDialogListener {
    private static final long LOADING_MIN_TIME = 1000;
    private static final long DIALOG_MIN_TIME = 2000;
    private ProgressDialog dialog;
    private TimeAsyncTask timeAsyncTask;
    private SimpleAsyncTask simpleAsyncTask;
    private long lastElapsedTime;
    private String current_ver;
    private String update_ver;
    private BrandingDialog brandingDialog;
    private SimpleAlertDialog updateDialog;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        MatjiConstants.setContext(getApplicationContext());
        setContentView(R.layout.activity_intro);
        brandingDialog = new BrandingDialog.Builder(this).create();
        brandingDialog.setBrandingPopupListener(this);

        updateDialog = new SimpleAlertDialog(IntroActivity.this, "최신 버전으로 업데이트 하세요.");
        dialog = new ProgressDialog(this);
        dialog.setMessage(MatjiConstants.string(R.string.dialog_intro_loading));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);

        timeAsyncTask = new TimeAsyncTask();
        timeAsyncTask.setTimeListener(this);

        simpleAsyncTask = new SimpleAsyncTask(new SessionRunnable());
        simpleAsyncTask.setProgressListener(this);

        current_ver = MatjiConstants.string(R.string.settings_service_version_name);
    }

    public synchronized void onElapsedTime(@SuppressWarnings("rawtypes") AsyncTask task, long startTime, long currentTime, long elapsedTime) {
        lastElapsedTime = elapsedTime;
        if (elapsedTime > DIALOG_MIN_TIME) {
            dialog.show();
        }
    }

    public void onStart(Threadable task) { }
    public void onFinish(Threadable task) {
        timeAsyncTask.setTimeListener(null);
        if (lastElapsedTime < LOADING_MIN_TIME) {
            try {
                Thread.sleep(LOADING_MIN_TIME - lastElapsedTime);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        dialog.dismiss();
        if (needUpdate(current_ver, update_ver)) {
            setListeners();
        } else {
            finish();
        }
    }

    protected void onResume() {
        super.onResume();
        brandingDialog.show();
    }

    public void onStop() {
        super.onStop();
        updateDialog.cancel();
    }

    @Override
    public int setMainViewId() {
        return R.id.activity_intro;
    }

    public class SessionRunnable implements Runnable {
        public void run() {
            Session session = Session.getInstance(IntroActivity.this);
            if (session.isLogin()) session.unsyncSessionValidate();
            session.notificationValidate();
            ImageLoader.clearCache(getApplicationContext());
            VersionHttpRequest request = new VersionHttpRequest(IntroActivity.this);
            request.actionAppVersion("ANDROID", current_ver);
            try {
                ArrayList<MatjiData> data = request.request();

                if (data != null && data.size() > 0) {
                    AppVersion app_version = (AppVersion) data.get(0);
                    update_ver = app_version.getVersion();
                } else {
                    update_ver = current_ver;
                }
            } catch (final MatjiException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        e.performExceptionHandling(IntroActivity.this);
                    }
                });
            }

            if (needUpdate(current_ver, update_ver)) { 
                runOnUiThread(new UpdateMessage());
            } else {
                startActivity(new Intent(IntroActivity.this, MainTabActivity.class));
            }
        }
    }

    public class UpdateMessage implements Runnable {
        public void run() {
            updateDialog.show();
        }

    }
    private void setListeners() {
        updateDialog.setOnClickListener(new SimpleAlertDialog.OnClickListener() {
            @Override
            public void onConfirmClick(SimpleDialog dialog) {
                IntroActivity.this.finish();
            }
        });
    }


    private Boolean needUpdate(String current_ver, String update_ver) {
        if (current_ver == null || update_ver == null) {
            return false;
        }

        String cv = current_ver.replaceAll("\\.","");
        String uv = update_ver.replaceAll("\\.","");
        while (cv.length() != uv.length()){
            if (cv.length() > uv.length()) {
                uv += "0";
            } else {
                cv += "0";
            }
        }
        if (Integer.parseInt(cv) < Integer.parseInt(uv)) {
            return true;
        } else {
            return false;
        }
    }

    public void onBackPressed() {}

    @Override
    public void shown() {}

    @Override
    public void dismissed() {
        timeAsyncTask.execute();
        simpleAsyncTask.execute();
    }
}