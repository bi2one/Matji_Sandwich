package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.matji.sandwich.listener.LikeListener;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.CommentInputBar;
import com.matji.sandwich.widget.CommentListView;
import com.matji.sandwich.widget.title.PageableTitle;
import com.matji.sandwich.widget.title.PageableTitle.Pageable;

public class PostActivity extends BaseActivity implements Requestable, Pageable {
    private ArrayList<MatjiData> posts;
    private int position;
    private Post currentPost;

    private boolean showKeyboard;

    private HttpRequest request;
    private HttpRequestManager manager;

    private PageableTitle pageableTitle;
    private CommentListView commentListView;
    private CommentInputBar commentInputBar;

    private Toast toast;

    private static final int COMMENT_WRITE_REQUEST = 10;

    public static final String SHOW_KEYBOARD = "show_keyboard"; 
    public static final String POSTS = "posts";
    public static final String POSITION = "position";
    private static final int NOT_POSITIONED = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(POSTS, posts);
        setResult(RESULT_OK, intent);
        super.finish();
    }

    protected void init() {
        super.init();
        setContentView(R.layout.activity_post);

        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        manager = HttpRequestManager.getInstance(this);

        posts = getIntent().getParcelableArrayListExtra(POSTS);
        position = getIntent().getIntExtra(POSITION, NOT_POSITIONED);
        if (position == NOT_POSITIONED) {
            // TODO finish activity
        }
        currentPost = (Post) posts.get(position);                           // 전달받은 position에 있는 Post 객체를 가져와 currentPost에 저장
        showKeyboard = getIntent().getBooleanExtra(SHOW_KEYBOARD, false);
        pageableTitle = (PageableTitle) findViewById(R.id.Titlebar);
        pageableTitle.setPageable(this);                                    // Pageable 객체를 등록 
        pageableTitle.setTitle(R.string.default_string_post);               // 타이틀 설정
        commentListView = (CommentListView) findViewById(R.id.comment_list);
        commentListView.setPost(currentPost);                               // currentPost를 commentListView의 post로 저장
        commentListView.setActivity(this);
        commentListView.requestReload();

        commentInputBar = (CommentInputBar) findViewById(R.id.comment_input_bar);
        if (showKeyboard) {
            commentInputBar.requestFocusToTextField();                      // showKeyboard 값에 따라 키보드를 보여주거나 보여주지 않음
        }
        commentInputBar.setLikeListener(new LikeListener(this, this, currentPost) { // LikeListener를 등록. 리스너의 Post 객체는 currentPost

            @Override
            public void postUnlikeRequest() {
                // like 요청 후 post의 likeCount를 줄여준다.
                currentPost.setLikeCount(currentPost.getLikeCount() - 1);
                commentListView.setPost(currentPost);
                commentListView.dataRefresh();

            }

            @Override
            public void postLikeRequest() {
                // unlike 요청 후 post의 likeCount를 늘려준다.
                currentPost.setLikeCount(currentPost.getLikeCount() + 1);
                commentListView.setPost(currentPost);
                commentListView.dataRefresh();
            }
        });
    }

    public void onConfirmButtonClicked(View v) {
        if (loginRequired() && !manager.isRunning(this)) {
            if (request == null || !(request instanceof CommentHttpRequest)) {
                request = new CommentHttpRequest(getApplicationContext());
            }

            if(commentInputBar.getText().trim().equals("")) {
                Toast.makeText(getApplicationContext(), R.string.writing_content_comment, Toast.LENGTH_SHORT).show();
            } else {
                ((CommentHttpRequest) request).actionNew(((Post) posts.get(position)).getId(), commentInputBar.getText().trim(), MatjiConstants.string(R.string.android));
                manager.request(this, request, COMMENT_WRITE_REQUEST, this);
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
            } else {
                Log.d("Matji", "not exist data ...");
            }
        }
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.showToastMsg(getApplicationContext());
    }

    @Override
    public void prev() {
        if (!manager.isRunning(this)) {
            if (position-1 < 0) {
                toast.setText(MatjiConstants.string(R.string.does_not_exist_prev_post));
                toast.show();
            }
            else {
                currentPost = (Post) posts.get(--position);
                commentListView.clearAdapter();
                commentListView.setPost(currentPost);
                commentListView.requestReload();
            }
        }
    }

    @Override
    public void next() {
        if (!manager.isRunning(this)) {
            if (position+1 >= posts.size()) {
                toast.setText(MatjiConstants.string(R.string.does_not_exist_next_post));
                toast.show();
            } else {
                currentPost = (Post) posts.get(++position);
                commentListView.setPost(currentPost);
                commentListView.clearAdapter();
                commentListView.requestReload();
            }
        }
    }
}