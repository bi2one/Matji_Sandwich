//package com.matji.sandwich;
//
//import java.util.ArrayList;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ImageView.ScaleType;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.matji.sandwich.base.BaseActivity;
//import com.matji.sandwich.data.AttachFile;
//import com.matji.sandwich.data.MatjiData;
//import com.matji.sandwich.data.Post;
//import com.matji.sandwich.exception.MatjiException;
//import com.matji.sandwich.http.HttpRequestManager;
//import com.matji.sandwich.http.request.HttpRequest;
//import com.matji.sandwich.http.request.PostHttpRequest;
//import com.matji.sandwich.http.util.ImageLoader;
//import com.matji.sandwich.listener.GotoPostMainAction;
//import com.matji.sandwich.listener.GotoStoreMainAction;
//import com.matji.sandwich.listener.GotoUserMainAction;
//import com.matji.sandwich.util.MatjiConstants;
//import com.matji.sandwich.util.TimeUtil;
//import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;
//import com.matji.sandwich.widget.SwipeView;
//
//public class ImageSliderActivity extends BaseActivity implements OnScrollListener, Requestable, OnClickListener {
//    private AttachFile[] attachFiles;
//    private boolean isShowingPost = true;
//    private int currentPage;
//    private SwipeView swipeView;
//    
//    private ImageView leftImage;
//    private ImageView centerImage;
//    private ImageView rightImage;
//
//    private HttpRequest request;
//    private HttpRequestManager manager;
//    private ImageLoader imageLoader;
//
//    private TextView count;
//
//    private View contentWrapper;
//    private View postContentWrapper;
//    private View moresign;
//    private TextView nick;
//    private TextView at;
//    private TextView storeName;
//    private TextView post;
//    private TextView ago;
//    private TextView commentCount;
//    private TextView likeCount;    
//
//    private int prevPostId = -1;
//
//    public static final String ATTACH_FILES = "attach_files";
//    public static final String POSITION = "position";
//
//    public int setMainViewId() {
//        return R.id.activity_image_slider;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    protected void init() {
//        setContentView(R.layout.activity_image_slider);
//
//        Parcelable[] tmp = getIntent().getParcelableArrayExtra(ATTACH_FILES);
//        attachFiles = new AttachFile[tmp.length];
//        for (int i = 0; i < tmp.length; i++) {
//            attachFiles[i] = (AttachFile) tmp[i];
//        }
//
//        currentPage = getIntent().getIntExtra(POSITION, 0);
//        manager = HttpRequestManager.getInstance();
//        imageLoader = new ImageLoader(this);
//        imageLoader.setScalable(false);
//
//        count = (TextView) findViewById(R.id.image_slider_count);
//        swipeView = (SwipeView) findViewById(R.id.SwipeView);
//        contentWrapper = findViewById(R.id.image_slider_content_wrapper);
//        postContentWrapper= findViewById(R.id.image_slider_post_wrapper);
//        moresign = findViewById(R.id.image_slider_moresign);
//        nick = (TextView) findViewById(R.id.image_slider_nick);
//        at = (TextView) findViewById(R.id.image_slider_at);
//        storeName = (TextView) findViewById(R.id.image_slider_store_name);
//        post = (TextView) findViewById(R.id.image_slider_post);
//        ago = (TextView) findViewById(R.id.image_slider_created_at);
//        commentCount = (TextView) findViewById(R.id.image_slider_comment_count);
//        likeCount = (TextView) findViewById(R.id.image_slider_like_count);
//
//        swipeView.addOnScrollListener(this);
//
//        initImageView();
//        swipeView.setCurrentPage(currentPage);
//        setPage(currentPage);
//        setImage(currentPage);
//        preLoading();
//        postRequest(attachFiles[currentPage].getPostId());
//        super.init();
//    }
//
//    private void initImageView() {
//	// leftLayout = (RelativeLayout)findViewById(R.id.image_slider_left_layout);
//	// centerLayout = (RelativeLayout)findViewById(R.id.image_slider_center_layout);
//	// rightLayout = (RelativeLayout)findViewById(R.id.image_slider_right_layout);
//
//	// leftImage = (ImageView)findViewById(R.id.image_slider_left_image);
//	// centerImage = (ImageView)findViewById(R.id.image_slider_center_image);
//	// rightImage = (ImageView)findViewById(R.id.image_slider_right_image);
//
//	// centerImage.setOnClickListener(this);
//	
//	RelativeLayout.LayoutParams imageParam = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//	imageParam.addRule(RelativeLayout.CENTER_IN_PARENT);
//
//	leftImage = new ImageView(this);
//	leftImage.setLayoutParams(imageParam);
//	leftImage.setScaleType(ScaleType.FIT_CENTER);
//
//	centerImage = new ImageView(this);
//	centerImage.setLayoutParams(imageParam);
//	centerImage.setScaleType(ScaleType.FIT_CENTER);
//	centerImage.setOnClickListener(this);
//	
//	rightImage = new ImageView(this);
//	rightImage.setLayoutParams(imageParam);
//	rightImage.setScaleType(ScaleType.FIT_CENTER);
//
//        for (int i = 0; i < attachFiles.length; i++) {
//            RelativeLayout rl = new RelativeLayout(this);
//            rl.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//
//            if (attachFiles[i] != null) {
//                swipeView.addView(rl);
//            }
//        }
//    }
//
//    public void postRequest(int postId) {
//        if (!(postId == prevPostId)) {
//            preLoading();
//
//            if (request == null || !(request instanceof PostHttpRequest)) {
//                request = new PostHttpRequest(this);
//            }
//
//            ((PostHttpRequest) request).actionShow(postId);
//            manager.request(getApplicationContext(), (ViewGroup) contentWrapper, request, HttpRequestManager.POST_SHOW_REQUEST, this);
//            prevPostId = postId;
//        }
//    }
//
//    public void setPost(Post post) {
//        nick.setText(post.getUser().getNick());
//        nick.setOnClickListener(new GotoUserMainAction(this, post.getUser()));
//        if (post.getStore() != null) {
//            at.setVisibility(View.VISIBLE);
//            storeName.setText(post.getStore().getName());
//            storeName.setOnClickListener(new GotoStoreMainAction(this, post.getStore()));
//        } else {
//            at.setVisibility(View.GONE);
//            storeName.setText("");
//            storeName.setOnClickListener(null);
//        }
//        this.post.setText(post.getPost());
//        ago.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
//        commentCount.setText(post.getCommentCount()+"");
//        likeCount.setText(post.getLikeCount()+"");
//        contentWrapper.setOnClickListener(new GotoPostMainAction(this, post));
//    }
//
//    public void setPage(int currentPage) {
//        count.setText(
//                MatjiConstants.string(R.string.default_string_image) 
//                + " " + (currentPage+1) + "/" + attachFiles.length);
//        count.bringToFront();
//    }
//
//    private void removeView(View view) {
//	ViewGroup parent = (ViewGroup)view.getParent();
//	if (parent != null)
//	    parent.removeView(view);
//    }
//
//    private void replaceView(ViewGroup viewGroup, View view) {
//	if (viewGroup != null) {
//	    viewGroup.removeAllViews();
//	    viewGroup.addView(view);
//	}
//    }
//
//    private RelativeLayout imageLayoutAt(int index) {
//	return (RelativeLayout) swipeView.getChildAt(index);
//    }
//
//    public void setImage(int currentPage) {
//        int attach_file_id = attachFiles[currentPage].getId();
//
//        /* Set Previous Page Image */
//	if (currentPage > 0 && attachFiles[currentPage - 1] != null)
//	    imageLoader.DisplayImage(this, ImageLoader.UrlType.ATTACH_FILE,
//				     ImageLoader.ImageSize.XLARGE,
//				     leftImage,
//				     attachFiles[currentPage - 1].getId());
//        /* Set Center Page Image */
//	imageLoader.DisplayImage(this, ImageLoader.UrlType.ATTACH_FILE,
//				 ImageLoader.ImageSize.XLARGE,
//				 centerImage,
//				 attach_file_id);
//        /* Set Next Page Image */
//	if (attachFiles.length > currentPage + 1 && attachFiles[currentPage + 1] != null)
//	    imageLoader.DisplayImage(this, ImageLoader.UrlType.ATTACH_FILE,
//				     ImageLoader.ImageSize.XLARGE,
//				     rightImage,
//				     attachFiles[currentPage + 1].getId());
//
//	removeView(leftImage);
//	removeView(centerImage);
//	removeView(rightImage);
//	
//	replaceView(imageLayoutAt(currentPage - 1), leftImage);
//	replaceView(imageLayoutAt(currentPage), centerImage);
//	replaceView(imageLayoutAt(currentPage + 1), rightImage);
//	
//        // ImageView image = (ImageView) ((RelativeLayout) swipeView.getChildAt(currentPage)).getChildAt(0);
//        // imageLoader.DisplayImage(this,
//        //         ImageLoader.UrlType.ATTACH_FILE,
//        //         ImageLoader.ImageSize.XLARGE,
//        //         image,
//        //         attach_file_id);
//
//        /* Set Previous Page Image */
//        // if (currentPage > 0) {
//        //     attach_file_id = attachFiles[currentPage - 1].getId();
//        //     if (attachFiles[currentPage - 1] != null) {
//        //         image = (ImageView) ((RelativeLayout) swipeView.getChildAt(currentPage - 1)).getChildAt(0);
//        //         imageLoader.DisplayImage(this,
//        //                 ImageLoader.UrlType.ATTACH_FILE,
//        //                 ImageLoader.ImageSize.XLARGE,
//        //                 image,
//        //                 attach_file_id);
//        //     }
//        // }
//
//        /* Set Next Page Image */
//        // if (currentPage < attachFiles.length - 1) {
//        //     attach_file_id = attachFiles[currentPage + 1].getId();
//        //     if (attachFiles[currentPage + 1] != null) {
//        //         image = (ImageView) ((RelativeLayout) swipeView.getChildAt(currentPage + 1)).getChildAt(0);
//        //         imageLoader.DisplayImage(this,
//        //                 ImageLoader.UrlType.ATTACH_FILE,
//        //                 ImageLoader.ImageSize.XLARGE,
//        //                 image,
//        //                 attach_file_id);
//        //     }
//        // }
//    }
//
//    public void onScroll(int scrollX) { }
//
//    public void onViewScrollFinished(int currentPage) {
//        if (this.currentPage != currentPage) {
//            this.currentPage = currentPage;
//            manager.cancelTask();
//            setPage(currentPage);
//            setImage(currentPage);
//            postRequest(attachFiles[currentPage].getPostId());
//        }
//    }
//
//    public void preLoading() {
//        postContentWrapper.setVisibility(View.GONE);
//        moresign.setVisibility(View.GONE);
//    }
//
//    public void postLoading() {
//        postContentWrapper.setVisibility(View.VISIBLE);
//        moresign.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
//        switch (tag) {
//        case HttpRequestManager.POST_SHOW_REQUEST:
//            if (data != null && !data.isEmpty()) {
//                setPost((Post) data.get(0));
//                postLoading();
//            } else {
//                // TODO error message
//            }
//        }
//    }
//
//    @Override
//    public void requestExceptionCallBack(int tag, MatjiException e) {
//        e.performExceptionHandling(this);
//    }    
//
//    @Override
//    public void finish() {
//        setResult(RESULT_OK);
//        super.finish();
//    }
//
//
//    public void showPost() {
//        count.setVisibility(View.VISIBLE);
//        contentWrapper.setVisibility(View.VISIBLE);
//        isShowingPost = true;
//    }
//
//    public void dismissPost() {
//        count.setVisibility(View.GONE);
//        contentWrapper.setVisibility(View.GONE);
//        isShowingPost = false;
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (isShowingPost) {
//            dismissPost();
//        } else {
//            showPost();
//        }		
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//        case POST_MAIN_ACTIVITY:
//            if (resultCode == RESULT_OK) {
//                setPost((Post) data.getParcelableArrayListExtra(PostMainActivity.POSTS).get(0));
//            }
//            break;
//        }
//    }
//}



package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
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
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.HorizontalPager.OnScrollListener;
import com.matji.sandwich.widget.SwipeView;

public class ImageSliderActivity extends BaseActivity implements OnScrollListener, Requestable {
    private AttachFile[] attachFiles;
    private int currentPage;
    private SwipeView swipeView;

    private HttpRequest request;
    private HttpRequestManager manager;
    private ImageLoader imageLoader;

    private TextView count;

    private View postWrapper;
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

        currentPage = getIntent().getIntExtra(POSITION, 0);
        manager = HttpRequestManager.getInstance();
        imageLoader = new ImageLoader(this);
        imageLoader.setScalable(false);

        count = (TextView) findViewById(R.id.image_slider_count);
        swipeView = (SwipeView) findViewById(R.id.SwipeView);
        postWrapper= findViewById(R.id.image_slider_post_wrapper);
        moresign = findViewById(R.id.image_slider_moresign);
        nick = (TextView) findViewById(R.id.image_slider_nick);
        at = (TextView) findViewById(R.id.image_slider_at);
        storeName = (TextView) findViewById(R.id.image_slider_store_name);
        post = (TextView) findViewById(R.id.image_slider_post);
        ago = (TextView) findViewById(R.id.image_slider_created_at);
        commentCount = (TextView) findViewById(R.id.image_slider_comment_count);
        likeCount = (TextView) findViewById(R.id.image_slider_like_count);

        swipeView.addOnScrollListener(this);

        initImageView();
        swipeView.setCurrentPage(currentPage);
        setPage(currentPage);
        setImage(currentPage);
        dismissPost();
        postRequest(attachFiles[currentPage].getPostId());
        super.init();
    }

    private void initImageView() {
        for (int i = 0; i < attachFiles.length; i++) {
            RelativeLayout rl = new RelativeLayout(this);
            rl.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);

            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setScaleType(ScaleType.FIT_CENTER);

            rl.addView(image);

            if (attachFiles[i] != null) {
                swipeView.addView(rl);
            }
        }
    }

    public void postRequest(int postId) {
        if (!(postId == prevPostId)) {
            dismissPost();

            if (request == null || !(request instanceof PostHttpRequest)) {
                request = new PostHttpRequest(this);
            }

            ((PostHttpRequest) request).actionShow(postId);
            manager.request(this, getMainView(), SpinnerType.SMALL, request, HttpRequestManager.POST_SHOW_REQUEST, this);
            prevPostId = postId;
        }
    }

    public void setPost(Post post) {
        nick.setText(post.getUser().getNick());
        if (post.getStore() != null) {
            at.setVisibility(View.VISIBLE);
            storeName.setText(post.getStore().getName());
        } else {
            at.setVisibility(View.GONE);
            storeName.setText("");
        }
        this.post.setText(post.getPost());
        ago.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
        commentCount.setText(post.getCommentCount()+"");
        likeCount.setText(post.getLikeCount()+"");
    }

    public void setPage(int currentPage) {
        count.setText(
                MatjiConstants.string(R.string.default_string_image) 
                + " " + (currentPage+1) + "/" + attachFiles.length);
        count.bringToFront();
    }

    public void setImage(int currentPage) {
        int attach_file_id = attachFiles[currentPage].getId();

        /* Set Current Page Image */
        ImageView image = (ImageView) ((RelativeLayout) swipeView.getChildAt(currentPage)).getChildAt(0);
        imageLoader.DisplayImage(this,
                ImageLoader.UrlType.ATTACH_FILE,
                ImageLoader.ImageSize.XLARGE,
                image,
                attach_file_id);

        /* Set Previous Page Image */
        if (currentPage > 0) {
            attach_file_id = attachFiles[currentPage - 1].getId();
            if (attachFiles[currentPage - 1] != null) {
                image = (ImageView) ((RelativeLayout) swipeView.getChildAt(currentPage - 1)).getChildAt(0);
                imageLoader.DisplayImage(this,
                        ImageLoader.UrlType.ATTACH_FILE,
                        ImageLoader.ImageSize.XLARGE,
                        image,
                        attach_file_id);
            }
        }

        /* Set Next Page Image */
        if (currentPage < attachFiles.length - 1) {
            attach_file_id = attachFiles[currentPage + 1].getId();
            if (attachFiles[currentPage + 1] != null) {
                image = (ImageView) ((RelativeLayout) swipeView.getChildAt(currentPage + 1)).getChildAt(0);
                imageLoader.DisplayImage(this,
                        ImageLoader.UrlType.ATTACH_FILE,
                        ImageLoader.ImageSize.XLARGE,
                        image,
                        attach_file_id);
            }
        }
    }

    public void onScroll(int scrollX) {}

    public void onViewScrollFinished(int currentPage) {
        if (this.currentPage != currentPage) {
            this.currentPage = currentPage;
            manager.cancelTask();
            setPage(currentPage);
            setImage(currentPage);
            postRequest(attachFiles[currentPage].getPostId());
        }
    }

    public void showPost() {    
        postWrapper.setVisibility(View.VISIBLE);
        moresign.setVisibility(View.VISIBLE);
    }

    public void dismissPost() {
        postWrapper.setVisibility(View.GONE);
        moresign.setVisibility(View.GONE);
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) {
        case HttpRequestManager.POST_SHOW_REQUEST:
            if (data != null && !data.isEmpty()) {
                setPost((Post) data.get(0));
                showPost();
            } else {
                // TODO error message
            }
        }
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
}