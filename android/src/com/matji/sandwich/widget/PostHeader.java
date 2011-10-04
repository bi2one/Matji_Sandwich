package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.SimpleTag;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.listener.GotoImageSliderAction;
import com.matji.sandwich.listener.GotoStoreMainAction;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.dialog.ActionItem;
import com.matji.sandwich.widget.dialog.QuickActionDialog;
import com.matji.sandwich.widget.dialog.SimpleConfirmDialog;
import com.matji.sandwich.widget.dialog.SimpleDialog;

public class PostHeader extends ViewContainer {
    private Context context;

    private int[] previewWrapperIds = {
            R.id.header_post_preview1,
            R.id.header_post_preview2,
            R.id.header_post_preview3
    };

    private Post post;
    private Activity activity;
    private PostDeleteListener deleteListener;
    private PostEditListener editListener;
    private PostElement holder;
    private Session session;
    private ImageLoader imageLoader;

    public PostHeader(Context context) {
        super(context, R.layout.header_post);
        init(context);
    }

    public PostHeader(Context context, Post post) {
        this(context);
        init(context, post);
    }

    public PostHeader(Context context, Post post, Activity actrivity) {
        this(context);
        init(context, post, activity);
    }

    protected void init(Context context, Post post, Activity activity) {
        init(context);
        setActivity(activity);
        setPost(post);
    }
    
    protected void init(Context context, Post post) {
        init(context);
        setPost(post);
    }

    protected void init(Context context) {
        this.context = context;
        session = Session.getInstance(context);
        imageLoader = new ImageLoader(context);
        holder = new PostElement();

        holder.profile = (ProfileImageView) getRootView().findViewById(R.id.profile);
        holder.nickText = (TextView) getRootView().findViewById(R.id.header_post_nick);
        holder.atText = (TextView) getRootView().findViewById(R.id.header_post_at);
        holder.storeNameText = (TextView)getRootView().findViewById(R.id.header_post_store_name);
        holder.postText = (TextView) getRootView().findViewById(R.id.header_post_post);
        holder.previews = getRootView().findViewById(R.id.header_post_previews);
        holder.tagText = (TextView) getRootView().findViewById(R.id.header_post_tag);
        holder.dateAgoText = (TextView) getRootView().findViewById(R.id.header_post_created_at);
        holder.commentCountText = (TextView) getRootView().findViewById(R.id.header_post_comment_count);
        holder.likeCountText = (TextView) getRootView().findViewById(R.id.header_post_like_count);
        holder.menu = getRootView().findViewById(R.id.header_post_menu_btn);

        holder.profile.showInsetBackground();

        holder.previewWrapper = new RelativeLayout[previewWrapperIds.length];
        holder.preview = new ImageView[previewWrapperIds.length];

        for (int i = 0; i < previewWrapperIds.length; i++) {
            holder.preview[i] = new ImageView(getRootView().getContext());
            holder.previewWrapper[i] = (RelativeLayout) getRootView().findViewById(previewWrapperIds[i]);
            holder.previewWrapper[i].addView(holder.preview[i]);
        }
    }
    
    public void setPostDeleteListener(PostDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }
    
    public void setPostEditListener(PostEditListener editListener) {
        this.editListener = editListener;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    
    public void setPost(Post post) {
        this.post = post;
        setOnClickListener();
        setViewData();
    }

    private void setOnClickListener() {
        GotoUserMainAction action1 = new GotoUserMainAction(getRootView().getContext(), post.getUser());
        GotoStoreMainAction action2 = new GotoStoreMainAction(getRootView().getContext(), post.getStore());
        GotoImageSliderAction action3 = new GotoImageSliderAction(getRootView().getContext(), post.getAttachFiles());

        holder.profile.setOnClickListener(action1);
        holder.nickText.setOnClickListener(action1);
        holder.storeNameText.setOnClickListener(action2);
        holder.menu.setOnClickListener(new PostHeaderQuickActionDialog());
        holder.menu.setTag(holder.menu);

        for (int i = 0; i < previewWrapperIds.length; i++) {
            holder.preview[i].setOnClickListener(action3);
        }
    }


    private void setViewData() {
        setPreviews(holder, post);

        Store store = post.getStore();
        User user = post.getUser();


        if (store != null) {
            holder.nickText.setMaxWidth(MatjiConstants.dimenInt(R.dimen.default_nick_max_width_half));
            holder.atText.setVisibility(View.VISIBLE);
            holder.storeNameText.setText(" "+store.getName());
        }
        else {
            holder.nickText.setMaxWidth(9999);
            holder.atText.setVisibility(View.GONE);
            holder.storeNameText.setText("");
        }

        ArrayList<SimpleTag> tags = post.getTags();
        String tagResult = "";

        if (tags.size() > 0) {
            tagResult += tags.get(0).getTag();
            for (int i = 1; i < tags.size(); i++) {
                tagResult += ", " + tags.get(i).getTag();
            }
            holder.tagText.setText(tagResult);
            holder.tagText.setVisibility(View.VISIBLE);
        } else {
            holder.tagText.setVisibility(View.GONE);
        }

        holder.profile.setUserId(user.getId());
        holder.nickText.setText(user.getNick()+" ");
        holder.postText.setText(post.getPost().trim());
        holder.dateAgoText.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
        holder.commentCountText.setText(post.getCommentCount() + "");
        holder.likeCountText.setText(post.getLikeCount() + "");
        
        if (session.isLogin() && post.getUser().getId() == session.getCurrentUser().getId()) {
            holder.menu.setVisibility(View.VISIBLE);
        } else {
            holder.menu.setVisibility(View.GONE);
        }
    }

    public void setPreviews(PostElement holder, Post post) {
        holder.previews.setVisibility(View.GONE);

        for (int i = 0; i < holder.preview.length; i++) {
            holder.preview[i].setVisibility(View.GONE);
        }

        for (int i = 0; (i < post.getAttachFiles().size()) && (i < holder.preview.length); i++) {
            holder.previews.setVisibility(View.VISIBLE);
            holder.preview[i].setVisibility(View.VISIBLE);
            holder.preview[i].setTag(i+"");
            imageLoader.DisplayImage((Activity)context, ImageLoader.UrlType.ATTACH_FILE, ImageLoader.ImageSize.SMALL, holder.preview[i], post.getAttachFiles().get(i).getId());
        }
    }

    private class PostElement {
        private ProfileImageView profile;
        private TextView nickText; 
        private TextView atText;
        private TextView storeNameText;
        private TextView postText;
        private TextView tagText;
        private TextView dateAgoText;
        private TextView commentCountText;
        private TextView likeCountText;
        private View previews;
        private RelativeLayout[] previewWrapper;
        private ImageView[] preview;
        private View menu;
    }


    /**
     * QuickAction 버튼을 클릭했을 때, 다이얼로그를 보여주고
     * 다이얼로그 내의 버튼을 클릭했을 때 각 버튼에 대한 행동을 취한다.
     *  
     * @author mozziluv
     *
     */
    class PostHeaderQuickActionDialog implements Requestable, OnClickListener {
        private QuickActionDialog quickaction;
        private Session session;
        private HttpRequest request;
        private HttpRequestManager manager;
        private ViewGroup spinnerContainer;

        /**
         * 기본 생성자.
         * 
         * @param position 현재 QuickAction 버튼이 눌린 position
         */
        public PostHeaderQuickActionDialog() {
            this.quickaction = new QuickActionDialog(context);
            this.session = Session.getInstance(context);
            this.manager = HttpRequestManager.getInstance();
            this.spinnerContainer = (ViewGroup) getRootView().findViewById(R.id.header_post_wrapper);

            // 자신이 작성한 이야기일 경우 수정, 삭제 버튼도 추가.
            if (isMine(post)) {
                ActionItem editAction = new ActionItem();
                editAction.setIcon(MatjiConstants.drawable(R.drawable.icon_memo_write));
                quickaction.addActionItem(editAction);

                ActionItem deleteAction = new ActionItem();
                deleteAction.setIcon(MatjiConstants.drawable(R.drawable.icon_memo_del));
                quickaction.addActionItem(deleteAction);
            }

            // 클릭 리스너 등록.
            quickaction.setOnActionItemClickListener(new QuickActionDialog.OnActionItemClickListener() {

                @Override
                public void onItemClick(int pos) {
                    if (((Identifiable) context).loginRequired()) {
                        if (pos == 0) {
                            Log.d("Matji", "edit button click");
                        } else if (pos == 1) {
                            Log.d("Matji", "delete button click");
                            deletePost();
                        }
                    }
                }
            });
        }

        /**
         * 이야기 삭제 버튼을 클릭했을 때,
         * 현재 이 이야기를 삭제할 것인지를 묻는 Alert 윈도우를 띄우고,
         * 확인 했을 때 삭제요청을 한다.
         */
        public void deletePost() {
            if (activity.getParent() != null) {
                activity = activity.getParent();
            }

            SimpleConfirmDialog dialog = new SimpleConfirmDialog(activity, R.string.default_string_check_delete);
            dialog.setOnClickListener(new SimpleConfirmDialog.OnClickListener() {

                @Override
                public void onConfirmClick(SimpleDialog dialog) {
                    deleteRequest(post);
                }

                @Override
                public void onCancelClick(SimpleDialog dialog) {}
            });
            dialog.show();
        }

        /**
         * Delete 요청
         * @param post Delete할 이야기
         */
        public void deleteRequest(Post post) {
            // Alert 창 띄우기.
            if (!manager.isRunning()) {
                if (request == null || !(request instanceof PostHttpRequest)) {
                    request = new PostHttpRequest(context);
                }

                ((PostHttpRequest) request).actionDelete(post.getId());
                manager.request(context, spinnerContainer, request, HttpRequestManager.POST_DELETE_REQUEST, this);
            }
        }

        /**
         * 파라미터로 전달받은 {@link Post}가 현재 로그인 된 {@link User}의 {@link Post}인지 확인한다.
         * 
         * @param post 확인 할 {@link Post}
         * @return 전달받은 {@link Post}가 로그인 된 {@link User}의 {@link Post}일 때 true
         */
        public boolean isMine(Post post) {
            return session.isLogin() && session.getCurrentUser().getId() == post.getUserId();
        }

        /**
         * @see Requestable#requestCallBack(int, ArrayList)
         */
        @Override
        public void requestCallBack(int tag, ArrayList<MatjiData> data) {

            switch (tag) {
            // TODO Auto-generated method stub
            case HttpRequestManager.POST_DELETE_REQUEST:
                deleteListener.postDelete();
                break;
            }
        }

        /**
         * @see Requestable#requestExceptionCallBack(int, MatjiException)
         */
        @Override
        public void requestExceptionCallBack(int tag, MatjiException e) {
            // TODO Auto-generated method stub
            e.performExceptionHandling(context);
        }

        /**
         * Quick Action Dialog를 보여준다.
         */
        @Override
        public void onClick(View v) {
            quickaction.show((View) v.getTag());
        }
    }
    
    public interface PostDeleteListener {
        public void postDelete();
    }
    
    public interface PostEditListener {
        public void postEdit();
    }
}
