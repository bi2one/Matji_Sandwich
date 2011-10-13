package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.http.spinner.SpinnerFactory.SpinnerType;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.listener.GotoPostMainAction;
import com.matji.sandwich.listener.GotoStoreMainAction;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.PhotoSliderView;
import com.matji.sandwich.widget.PhotoSliderView.OnClickListener;
import com.matji.sandwich.widget.PhotoSliderView.OnPageChangedListener;

public class ImageSliderActivity extends BaseActivity implements Requestable, OnClickListener, OnPageChangedListener {
    private AttachFile[] attachFiles;
    private boolean isShowingPost = true;

    private HttpRequest request;
    private HttpRequestManager manager;
    private ImageLoader imageLoader;

    private TextView count;

    private PhotoSliderView slider;
    private ViewGroup contentWrapper;
    private View postContentWrapper;
    private View moresign;
    private TextView nick;
    private TextView at;
    private TextView storeName;
    private TextView post;
    private TextView ago;
    private TextView commentCount;
    private TextView likeCount;    

    private int prevPostId = -1;

    public static final String ATTACH_FILES = "attach_files";
    public static final String POSITION = "position";

    public int setMainViewId() {
        return R.id.activity_image_slider;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected void init() {
        setContentView(R.layout.activity_image_slider);

        Parcelable[] tmp = getIntent().getParcelableArrayExtra(ATTACH_FILES);
        attachFiles = new AttachFile[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            attachFiles[i] = (AttachFile) tmp[i];
        }

        manager = HttpRequestManager.getInstance();
        imageLoader = new ImageLoader(this);

        findViews();
        slider.setPhotoCount(attachFiles.length);
        slider.addOnPageChangedLlistener(this);
        slider.addOnClickLlistener(this);
        slider.setCurrentItemPosition(getIntent().getIntExtra(POSITION, 0));
        changedPage();
        refreshAll();
    }

    private void findViews() {
        slider = (PhotoSliderView) findViewById(R.id.image_slider);
        count = (TextView) findViewById(R.id.image_slider_count);
        contentWrapper = (ViewGroup) findViewById(R.id.image_slider_content_wrapper);
        postContentWrapper = (ViewGroup) findViewById(R.id.image_slider_post_wrapper);
        moresign = findViewById(R.id.image_slider_moresign);
        nick = (TextView) findViewById(R.id.image_slider_nick);
        at = (TextView) findViewById(R.id.image_slider_at);
        storeName = (TextView) findViewById(R.id.image_slider_store_name);
        post = (TextView) findViewById(R.id.image_slider_post);
        ago = (TextView) findViewById(R.id.image_slider_created_at);
        commentCount = (TextView) findViewById(R.id.image_slider_comment_count);
        likeCount = (TextView) findViewById(R.id.image_slider_like_count);
    }

    public void postRequest(int postId) {
        if (!(postId == prevPostId)) {
            preLoading();

            if (request == null || !(request instanceof PostHttpRequest)) {
                request = new PostHttpRequest(this);
            }

            ((PostHttpRequest) request).actionShow(postId);
            manager.request(this, contentWrapper, SpinnerType.SMALL, request, HttpRequestManager.POST_SHOW_REQUEST, this);
            prevPostId = postId;
        }
    }

    public void setPost(Post post) {
        nick.setText(post.getUser().getNick()+ " ");
        nick.setOnClickListener(new GotoUserMainAction(this, post.getUser()));
        if (post.getStore() != null) {
            at.setVisibility(View.VISIBLE);
            storeName.setText(" " + post.getStore().getName());
            storeName.setOnClickListener(new GotoStoreMainAction(this, post.getStore()));
        } else {
            at.setVisibility(View.GONE);
            storeName.setText("");
            storeName.setOnClickListener(null);
        }
        this.post.setText(post.getPost());
        ago.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
        commentCount.setText(post.getCommentCount()+"");
        likeCount.setText(post.getLikeCount()+"");
        contentWrapper.setOnClickListener(new GotoPostMainAction(this, post));
    }

    public void showPost() {
        count.setVisibility(View.VISIBLE);
        contentWrapper.setVisibility(View.VISIBLE);
        isShowingPost = true;
    }

    public void dismissPost() {
        count.setVisibility(View.GONE);
        contentWrapper.setVisibility(View.GONE);
        isShowingPost = false;
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) {
        case HttpRequestManager.POST_SHOW_REQUEST:
            if (data != null && !data.isEmpty()) {
                setPost((Post) data.get(0));
                postLoading();
            } else {
                // TODO error message
            }
        }
    }

    public void preLoading() {
        postContentWrapper.setVisibility(View.GONE);
        moresign.setVisibility(View.GONE);
    }

    public void postLoading() {
        postContentWrapper.setVisibility(View.VISIBLE);
        moresign.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(this);
    }    

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    @Override
    public void onClick() {
        if (isShowingPost) {
            dismissPost();
        } else {
            showPost();
        }     
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case POST_MAIN_ACTIVITY:
            if (resultCode == RESULT_OK) {
                setPost((Post) data.getParcelableArrayListExtra(PostMainActivity.POSTS).get(0));
            }
            break;
        }
    }

    public void refreshAll() {
        refreshLeftImageView();
        refreshCenterImageView();
        refreshRightImageView();
    }

    public void refreshLeftImageView() {
        if (slider.isExistLeftImage()) {
            imageLoader.cancel(slider.getLeftImageView());
            slider.getLeftImageView().setImageDrawable(null);
            imageLoader.DisplayImage(this,
                    ImageLoader.UrlType.ATTACH_FILE,
                    ImageLoader.ImageSize.XLARGE,
                    slider.getLeftImageView(),
                    attachFiles[slider.getCurrentItemPosition()-1].getId());
        }
    }



    public void refreshCenterImageView() {
        imageLoader.cancel(slider.getCenterImageView());
        imageLoader.DisplayImage(this,
                ImageLoader.UrlType.ATTACH_FILE,
                ImageLoader.ImageSize.XLARGE,
                slider.getCenterImageView(),
                attachFiles[slider.getCurrentItemPosition()].getId());
    }


    public void refreshRightImageView() {
        if (slider.isExistRightImage()) {
            imageLoader.cancel(slider.getRightImageView());
            slider.getRightImageView().setImageDrawable(null);
            imageLoader.DisplayImage(this,
                    ImageLoader.UrlType.ATTACH_FILE,
                    ImageLoader.ImageSize.XLARGE,
                    slider.getRightImageView(),
                    attachFiles[slider.getCurrentItemPosition()+1].getId());
        }
    }


    public void changedPage() {
        manager.cancelTask();
        count.setText(
                MatjiConstants.string(R.string.default_string_image) 
                + " " + (slider.getCurrentItemPosition()+1) + "/" + attachFiles.length);
        postRequest(attachFiles[slider.getCurrentItemPosition()].getPostId());
    }

    @Override
    public void changeToLeft() {
        changedPage();
        refreshLeftImageView();
    }

    @Override
    public void changeToRight() {
        changedPage();
        refreshRightImageView();
    }
}