package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.Comment;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.CommentHttpRequest;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.listener.LikeListener;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.CommentInputBar;
import com.matji.sandwich.widget.CommentListView;
import com.matji.sandwich.widget.PostHeader.PostDeleteListener;
import com.matji.sandwich.widget.title.PageableTitle;
import com.matji.sandwich.widget.title.PageableTitle.Pageable;

public class PostMainActivity extends BaseActivity implements Requestable, Pageable, PostDeleteListener {
    private ArrayList<MatjiData> posts;
    private int position;
    private Post currentPost;

    private boolean showKeyboard;

    private HttpRequest request;
    private HttpRequestManager manager;

    private PageableTitle pageableTitle;
    private CommentListView commentListView;
    private CommentInputBar commentInputBar;
    private LikeListener likeLisetner;

    private Toast toast;

    private static final int COMMENT_WRITE_REQUEST = 10;
	private static final int COMMENT_DELETE_REQUEST = 11;
    
    public static final String SHOW_KEYBOARD = "PostMainActivity.show_keyboard"; 
    public static final String POSTS = "PostMainActivity.posts";
    public static final String POSITION = "PostMainActivity.position";
    public static final String POST_ID = "PostMainActivity.post_id";
    public static final String IS_DELETED = "PostMainActivity.is_deleted";    
    public static final String DELETED_POST_ID = "PostMainActivity.deleted_post_id";
    private static final int NOT_INITIALIZED = -1;
    private int post_id = -1;
    private boolean isDeleted = false; // for alarm list view
    private int lastDeletedPostId= -1;      // for alarm list view

    public int setMainViewId() {
        return R.id.activity_post;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(POSTS, posts);
        intent.putExtra(IS_DELETED, isDeleted);
        intent.putExtra(DELETED_POST_ID, lastDeletedPostId);
        setResult(RESULT_OK, intent);
        super.finish();
    }

    private void init() {
        setContentView(R.layout.activity_post);

        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        manager = HttpRequestManager.getInstance();

        position = getIntent().getIntExtra(POSITION, NOT_INITIALIZED);
        post_id = getIntent().getIntExtra(POST_ID, NOT_INITIALIZED);

        if (position == NOT_INITIALIZED) {
            // TODO finish activity
        }

        if (post_id == NOT_INITIALIZED) {
            posts = getIntent().getParcelableArrayListExtra(POSTS);
            currentPost = (Post) posts.get(position);                           // 전달받은 position에 있는 Post 객체를 가져와 currentPost에 저장
        } else {
            request = new PostHttpRequest(this);

            try {
                currentPost = (Post) request.request().get(0);
            } catch (MatjiException e) {
                e.printStackTrace();
            } 
            posts = new ArrayList<MatjiData>();
            posts.add(currentPost);
        }

        showKeyboard = getIntent().getBooleanExtra(SHOW_KEYBOARD, false);

        pageableTitle = (PageableTitle) findViewById(R.id.Titlebar);
        commentListView = (CommentListView) findViewById(R.id.comment_list);
        commentInputBar = (CommentInputBar) findViewById(R.id.comment_input_bar);

        pageableTitle.setPageable(this);                                    // Pageable 객체를 등록 
        pageableTitle.setTitle(R.string.default_string_post);               // 타이틀 설정
        commentListView.setPost(currentPost);                               // currentPost를 commentListView의 post로 저장
        commentListView.setPostDeleteListener(this);
        commentListView.setActivity(this);
        commentListView.requestReload();

        if (showKeyboard) {
            commentInputBar.requestFocusToTextField();                      // showKeyboard 값에 따라 키보드를 보여주거나 보여주지 않음
        }
        
        likeLisetner = new LikeListener(this, this, currentPost, getMainView()) {
            @Override
            public void postUnlikeRequest() {
                // like 요청 후 post의 likeCount를 줄여준다.
                currentPost.setLikeCount(currentPost.getLikeCount() - 1);
                commentListView.setPost(currentPost);
                commentListView.dataRefresh();
                commentInputBar.offLikehand();
            }

            @Override
            public void postLikeRequest() {
                // unlike 요청 후 post의 likeCount를 늘려준다.
                currentPost.setLikeCount(currentPost.getLikeCount() + 1);
                commentListView.setPost(currentPost);
                commentListView.dataRefresh();
                commentInputBar.onLikehand();
            }
        };
        
        refreshCommentInputBar();
    }

    public void onConfirmButtonClicked(View v) {
        if (loginRequired() && !manager.isRunning()) {
            if (request == null || !(request instanceof CommentHttpRequest)) {
                request = new CommentHttpRequest(getApplicationContext());
            }

            if(commentInputBar.getText().trim().equals("")) {
                Toast.makeText(getApplicationContext(), R.string.writing_content_comment, Toast.LENGTH_SHORT).show();
            } else {
                ((CommentHttpRequest) request).actionNew(((Post) posts.get(position)).getId(), commentInputBar.getText().trim(), MatjiConstants.target());
                manager.request(getApplicationContext(), getMainView(), request, COMMENT_WRITE_REQUEST, this);
                commentInputBar.setText("");
                KeyboardUtil.hideKeyboard(this);
            }
        }
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch (tag) {
        case COMMENT_WRITE_REQUEST:
            if (data != null && data.size() > 0) {
                Comment newComment = (Comment) data.get(0);
                commentListView.addComment(newComment);
                currentPost.setCommentCount(currentPost.getCommentCount()+1);
                commentListView.setPost(currentPost);
                commentListView.dataRefresh();
                commentListView.setSelection(currentPost.getCommentCount()-1);
            } else {
            	Log.d("Matji", "not exist data ...");
            }
        case COMMENT_DELETE_REQUEST:
        	currentPost.setCommentCount(currentPost.getCommentCount()-1);
        }
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.showToastMsg(getApplicationContext());
    }

    @Override
    public void prev() {
        if (!manager.isRunning()) {
            if (position-1 < 0) {
                toast.setText(MatjiConstants.string(R.string.does_not_exist_prev_post));
                toast.show();
            }
            else {
                currentPost = (Post) posts.get(--position);
                commentListView.clearAdapter();
                commentListView.setPost(currentPost);
                commentListView.requestReload();
                refreshCommentInputBar();
            }
        }
    }

    @Override
    public void next() {
        if (!manager.isRunning()) {
            if (position+1 >= posts.size()) {
                toast.setText(MatjiConstants.string(R.string.does_not_exist_next_post));
                toast.show();
            } else {
                currentPost = (Post) posts.get(++position);
                commentListView.setPost(currentPost);
                commentListView.clearAdapter();
                commentListView.requestReload();
                refreshCommentInputBar();
            }
        }
    }
    
    public void refreshCommentListView() {
        commentListView.setPost(currentPost);
        commentListView.dataRefresh();
    }

    public void refreshCommentInputBar() {
        CommentInputBar.Type type = 
            (Session.getInstance(this).isCurrentUser(currentPost.getUser())) ?
                    CommentInputBar.Type.PRIVATE : CommentInputBar.Type.PUBLIC; 
        likeLisetner.setData(currentPost);
        commentInputBar.setType(type);
        if (type == CommentInputBar.Type.PUBLIC) {
            commentInputBar.setLikeListener(likeLisetner);
            commentInputBar.refreshLikehand(currentPost.getId());
        }
    }

    @Override
    public void postDelete() {
        isDeleted = true;
        lastDeletedPostId = ((Post) posts.get(position)).getId();        
        posts.remove(position);
        finish();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case POST_EDIT_ACTIVITY:
            if (resultCode == RESULT_OK) {
                Post post = (Post) data.getParcelableExtra(PostEditActivity.POST);
                currentPost = post;
                posts.remove(position);
                posts.add(position, post);
            }
            break;
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        refreshCommentInputBar();
        refreshCommentListView();
    }   
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_reload:
            commentListView.refresh();
            return true;
        }
        return false;
    }
}