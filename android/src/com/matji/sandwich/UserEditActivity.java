package com.matji.sandwich;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.matji.sandwich.FileUploadAsyncTask.FileUploader;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.AttachFileHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.PhotoUtil;
import com.matji.sandwich.util.PhotoUtil.IntentType;
import com.matji.sandwich.widget.cell.UserEditCell;
import com.matji.sandwich.widget.title.HomeTitle;

public class UserEditActivity extends BaseActivity implements OnClickListener, Refreshable, UserEditCell.OnItemClickListener, Requestable, FileUploader {

    private UserEditCell userEditCell;
    private Session session;
    private PhotoUtil photoUtil;
    private HttpRequestManager manager;
    private AttachFileHttpRequest fileRequest;

    private HomeTitle title;
    private View vIntro;
    private TextView tvIntro;
    private View vWebsite;
    private TextView tvWebsite;
    private View vArea;
    private TextView tvArea;
    private TextView tvEditPassword;
    
    public int setMainViewId() {
        return R.id.activity_user_edit;
    }

    @Override
    protected void init() {
        super.init();

        setContentView(R.layout.activity_user_edit);
        session = Session.getInstance(this);
        photoUtil = new PhotoUtil(this);
        manager = HttpRequestManager.getInstance(this);
        fileRequest = new AttachFileHttpRequest(this);

        title = (HomeTitle) findViewById(R.id.Titlebar);
        title.setTitle(R.string.settings_account_edit_profile);
        userEditCell = (UserEditCell) findViewById(R.id.UserEditCell);
        vIntro = findViewById(R.id.edit_intro_wrapper);
        tvIntro = (TextView) findViewById(R.id.edit_intro_content);
        vWebsite = findViewById(R.id.edit_website_wrapper);
        tvWebsite = (TextView) findViewById(R.id.edit_website_content);
        vArea = findViewById(R.id.edit_area_wrapper);
        tvArea = (TextView) findViewById(R.id.edit_area_content);
        tvEditPassword = (TextView) findViewById(R.id.edit_password);

        vIntro.setOnClickListener(this);
        vWebsite.setOnClickListener(this);
        vArea.setOnClickListener(this);
        tvEditPassword.setOnClickListener(this);

        userEditCell.addRefreshable(this);
        userEditCell.setEditProfileClickListener(this);

        setUser(session.getCurrentUser());
    }

    public void setUser(User user) {
        userEditCell.setUser(user);
        String intro = user.getIntro();
        if (intro.equals("")) {
            intro = MatjiConstants.string(R.string.default_string_not_exist_intro);
        }
        tvIntro.setText(intro);

        String website = user.getWebsite();
        if (website.equals("")) {
            website = MatjiConstants.string(R.string.default_string_not_exist_website);
        }
        tvWebsite.setText(website);
        tvArea.setText("한국인가?");
    }

    @Override
    protected void onResume() {
        super.onResume();
        userEditCell.refresh();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == vIntro.getId()) {
            onIntroWrapperClicked(v);
        } else if (viewId == vWebsite.getId()) {
            onBlogHompageWrapperClicked(v);
        } else if (viewId == vArea.getId()) {
            onAreaWrapperClicked(v);
        } else if (viewId == tvEditPassword.getId()) {
            onEditPasswordClicked(v);
        }
    }

    public void onIntroWrapperClicked(View v) {
        Intent intent = new Intent(this, UserIntroEditActivity.class);
        startActivity(intent);
    }

    public void onBlogHompageWrapperClicked(View v) {
        Intent intent = new Intent(this, UserWebsiteEditActivity.class);
        startActivity(intent);
    }

    public void onAreaWrapperClicked(View v) {

    }
    
    public void onEditPasswordClicked(View v) {
        Intent intent = new Intent(this, UserPasswordEditActivity.class);
        startActivity(intent);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void refresh(MatjiData data) {
        if (data instanceof User) {
            setUser((User) data);
        }
        refresh();
    }

    @Override
    public void refresh(ArrayList<MatjiData> data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCameraClicked() {
        Intent intent = photoUtil.getIntent(IntentType.FROM_CAMERA);
        manager.turnOff();
        startActivityForResult(intent, FROM_CAMERA);
    }

    @Override
    public void onAlbumClicked() {
        Intent intent = photoUtil.getIntent(IntentType.FROM_ALBUM);
        manager.turnOff();
        startActivityForResult(intent, FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return ;

        File imageFile = null;

        switch(requestCode) {
        case FROM_CAMERA:
            imageFile = photoUtil.getFileFromIntent(PhotoUtil.IntentType.FROM_CAMERA, data);
            break;
        case FROM_ALBUM:
            imageFile = photoUtil.getFileFromIntent(PhotoUtil.IntentType.FROM_ALBUM, data);
            break;
        }
        
        new FileUploadAsyncTask(this, this).execute(imageFile);
    }


    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        // cache clear.
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(this);
    }

    @Override
    public void upload(File file) {
        fileRequest.actionProfileUpload(file);
        try {
            fileRequest.request();
        } catch (MatjiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
}