package com.matji.sandwich.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matji.sandwich.ActivityStartable;
import com.matji.sandwich.PostActivity;
import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.base.BaseTabActivity;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.Like;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Post;
import com.matji.sandwich.data.SimpleTag;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.User;
import com.matji.sandwich.data.provider.DBProvider;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.LikeHttpRequest;
import com.matji.sandwich.http.request.PostHttpRequest;
import com.matji.sandwich.http.util.MatjiImageDownloader;
import com.matji.sandwich.listener.GotoImageSliderAction;
import com.matji.sandwich.listener.GotoStoreMainAction;
import com.matji.sandwich.listener.GotoUserMainAction;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.TimeUtil;
import com.matji.sandwich.widget.ProfileImageView;
import com.matji.sandwich.widget.dialog.ActionItem;
import com.matji.sandwich.widget.dialog.QuickActionDialog;

/**
 * {@link Post} Adapter.
 * 
 * @author mozziluv
 *
 */
public class PostSectionedAdapter extends SectionedAdapter {
    private int[] previewWrapperIds = new int[]{
            R.id.row_post_preview1,
            R.id.row_post_preview2,
            R.id.row_post_preview3,
    };
    
    private String subtitle;
    
    private MatjiImageDownloader downloader;

    private GotoUserMainAction action1;
    private GotoStoreMainAction action2;
    private GotoImageSliderAction action3;
    private Activity activity;
    private RelativeLayout spinnerContainer;

    private ListView parent;

    /**
     * 기본 생성자.
     * 
     * @param context
     */
    public PostSectionedAdapter(Context context) {
        super(context);
        init();
    }

    /**
     * 초기화 메소드.
     */
    protected void init() {
        downloader = new MatjiImageDownloader(context);
//        profileSize = (int) MatjiConstants.dimen(R.dimen.profile_size);
    }

    public void setSpinnerContainer(RelativeLayout spinnerContainer) {
	this.spinnerContainer = spinnerContainer;
    }

    public void setSubtitle(String subtitle) {    
        this.subtitle= subtitle;
    }
    
    /**
     * Section의 View를 리턴한다.
     * Section의 날짜가 오늘일 때, Text는 Today, background drawable은 today용 이미지로 설정해 리턴한다.
     * 
     * @see SectionedAdapter#getSectionView(View, String)
     */
    @Override
    public View getSectionView(View convertView, String section) {
        PostSectionElement postSectionElement;

        if (convertView == null) {
            postSectionElement = new PostSectionElement();
            convertView = inflater.inflate(R.layout.row_date_section, null);

            postSectionElement.section = (TextView)convertView.findViewById(R.id.row_date_section);
            convertView.setTag(postSectionElement);
        } else {
            postSectionElement = (PostSectionElement)convertView.getTag();
        }

        if (TimeUtil.isToday(section)) {
            postSectionElement.section.setText(context.getResources().getString(R.string.default_string_today));
            postSectionElement.section.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.indexbar_today));
        } else {
            postSectionElement.section.setText(section);
            postSectionElement.section.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.indexbar_day));
        }
        
        int padding = (int) MatjiConstants.dimen(R.dimen.default_distance);
        if (getSectionName(0).equals(section)) {
            if (postSectionElement.subtitle == null) {
                postSectionElement.subtitle = (TextView) convertView.findViewById(R.id.row_date_section_subtitle);
            }
            postSectionElement.subtitle.setText(subtitle);
            postSectionElement.subtitle.setVisibility(View.VISIBLE);
            convertView.setPadding(padding, padding, 0, 0);
        } else {
            if (postSectionElement.subtitle != null) {
                postSectionElement.subtitle.setVisibility(View.GONE);
                convertView.setPadding(0, 0, 0, 0);
            }
        }
        
        return convertView;
    }

    /**
     * {@link Post}의 뷰를 리턴한다.
     * 
     * @see SectionedAdapter#getItemView(int, View, ViewGroup)
     */
    @Override
    public View getItemView(final int position, View convertView, final ViewGroup parent) {
        PostElement postElement;
        if (this.parent == null) {
            this.parent = (ListView) parent;
        }

        if (convertView == null) {
            postElement = new PostElement();
            convertView = getLayoutInflater().inflate(R.layout.row_post, null);
            postElement.postWrapper = convertView.findViewById(R.id.row_post_post_wrapper);
            postElement.profile = (ProfileImageView) convertView.findViewById(R.id.profile);
            postElement.nick = (TextView) convertView.findViewById(R.id.row_post_nick);
            postElement.at = (TextView) convertView.findViewById(R.id.row_post_at);
            postElement.storeName = (TextView)convertView.findViewById(R.id.row_post_store_name);
            postElement.post = (TextView) convertView.findViewById(R.id.row_post_post);
            postElement.tag = (TextView) convertView.findViewById(R.id.row_post_tag);
            postElement.previews = convertView.findViewById(R.id.row_post_previews);
            postElement.dateAgo = (TextView) convertView.findViewById(R.id.row_post_created_at);
            postElement.commentCount = (TextView) convertView.findViewById(R.id.row_post_comment_count);
            postElement.likeCount = (TextView) convertView.findViewById(R.id.row_post_like_count);
            postElement.menu= (ImageButton) convertView.findViewById(R.id.row_post_menu_btn);
            postElement.menuWrapper = (RelativeLayout) convertView.findViewById(R.id.row_post_menu_btn_wrapper);
            
            postElement.preview = new ImageView[previewWrapperIds.length];
            postElement.previewWrapper = new RelativeLayout[previewWrapperIds.length];
            for (int i = 0; i < previewWrapperIds.length; i++) {
                postElement.preview[i] = new ImageView(context);
                postElement.previewWrapper[i] = (RelativeLayout) convertView.findViewById(previewWrapperIds[i]);
                postElement.previewWrapper[i].addView(postElement.preview[i]);
            }
            
            postElement.profile.showReliefBackground();
            
            convertView.setTag(postElement);

        } else {
            postElement = (PostElement) convertView.getTag();
        }

        postElement.postWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListItemClick(position);
            }
        });

        setActions(postElement, position);
        setViewData(postElement, position);

        return convertView;
    }

    /**
     * 전달받은 position의 {@link Post}의 작성 날짜를 Section의 Name으로 한다.
     * {@link Post}의 작성 날짜를 변환해 리턴한다.
     * 
     * @see SectionedAdapter#putSectionName(int)
     */
    @Override
    public String putSectionName(int position) {
        Post post = (Post) data.get(position);
        Date date = TimeUtil.getDateFromCreatedAt(post.getCreatedAt());
        String section = TimeUtil.parseString(date);
        return section;
    }

    /**
     * holder의 각 View에 대해 리스너를 등록한다.
     * 
     * @param holder 뷰가 위치한 holder
     * @param position 해당 holder의 position
     */
    private void setActions(PostElement holder, final int position) {
        action1 = new GotoUserMainAction(context, ((Post) data.get(position)).getUser());
        action2 = new GotoStoreMainAction(context, ((Post) data.get(position)).getStore());
        action3 = new GotoImageSliderAction(context, ((Post) data.get(position)).getAttachFiles());

        holder.profile.setOnClickListener(action1);
        holder.nick.setOnClickListener(action1);
        holder.storeName.setOnClickListener(action2);
        holder.post.setLinksClickable(false);
        PostQuickActionDialog dialog = new PostQuickActionDialog(position);
        holder.menu.setOnClickListener(dialog);
        holder.menu.setTag(holder.menu);
        holder.menuWrapper.setOnClickListener(dialog);
        holder.menuWrapper.setTag(holder.menu);

        for (int i = 0; i < holder.preview.length; i++) {
            holder.preview[i].setOnClickListener(action3);
        }
    }

    /**
     * {@link Post}내의 정보를 View에 뿌려준다.
     * 
     * @param holder 정보를 뿌려줄 view holder
     * @param position 해당 holder의 position
     */
    private void setViewData(PostElement holder, int position) {
        Post post = (Post) data.get(position);

        setPreviews(holder, post);
        
        Store store = post.getStore();
        User user = post.getUser();

        if (store != null) {
            holder.at.setVisibility(View.VISIBLE);
            holder.storeName.setText(" "+store.getName());
        }
        else {
            holder.at.setVisibility(View.GONE);
            holder.storeName.setText("");
        }

        ArrayList<SimpleTag> tags = post.getTags();
        String tagResult = "";

        if (tags.size() > 0) {
            tagResult += tags.get(0).getTag();
            for (int i = 1; i < tags.size(); i++) {
                tagResult += ", " + tags.get(i).getTag();
            }
            holder.tag.setText(tagResult);
            holder.tag.setVisibility(View.VISIBLE);
        } else {
            holder.tag.setVisibility(View.GONE);
        }

        holder.profile.setUserId(user.getId());
        holder.nick.setText(user.getNick()+" ");
        holder.post.setText(post.getPost().trim());
        holder.dateAgo.setText(TimeUtil.getAgoFromSecond(post.getAgo()));
        holder.commentCount.setText(post.getCommentCount() + "");
        holder.likeCount.setText(post.getLikeCount() + "");
    }

    /**
     * 파라미터로 전달받은 {@link Post}에 {@link AttachFile}이 존재할 경우,
     * preview에 이미지를 다운로드 받는다.
     * 
     * @param post 
     * @param preview
     */
    public void setPreviews(PostElement holder, Post post) {
        holder.previews.setVisibility(View.GONE);
        
        for (int i = 0; i < holder.preview.length; i++) {
            holder.preview[i].setVisibility(View.GONE);
        }
        
        for (int i = 0; (i < post.getAttachFiles().size()) && (i < holder.preview.length); i++) {
            holder.previews.setVisibility(View.VISIBLE);
            holder.preview[i].setVisibility(View.VISIBLE);
            holder.preview[i].setTag(i+"");
            downloader.downloadAttachFileImage(post.getAttachFiles().get(i).getId(), MatjiImageDownloader.IMAGE_SMALL, holder.preview[i]);
        }
    }

    /**
     * 리스트 아이템을 클릭했을 때,
     * {@link PostActivity}로 이동한다.
     * 
     * @param position 클릭된 아이템의 positoin
     */
    private void onListItemClick(int position) {
        Post post = (Post) data.get(position);
        if (post.getActivityId() == 0) {
            Intent intent = new Intent(context, PostActivity.class);
            intent.putExtra(PostActivity.POSTS, data);
            intent.putExtra(PostActivity.POSITION, position);
            if (((Activity) context).getParent().getParent() instanceof BaseTabActivity) {
                BaseTabActivity parent = (BaseTabActivity) ((Activity) context).getParent();
                parent.tabStartActivityForResult(intent, BaseActivity.POST_ACTIVITY, (ActivityStartable) context);
            } else {
                ((Activity) context).startActivityForResult(intent, BaseActivity.POST_ACTIVITY);
            }
        }
    }

    /**
     * Dialog를 show 하기 위해 activity를 저장한다.
     * 
     * @param activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * {@link Post} 뷰 홀더
     * 
     * @author mozziluv
     *
     */
    private class PostElement {
        View postWrapper;
        ProfileImageView profile;
        TextView nick;
        TextView at;
        TextView storeName;
        TextView post;
        TextView tag;
        TextView dateAgo;
        TextView commentCount;
        TextView likeCount;
        View previews;
        RelativeLayout[] previewWrapper;
        ImageView[] preview;
        ImageButton menu;
        RelativeLayout menuWrapper;
    }

    private class PostSectionElement {
        TextView subtitle;
        TextView section;
    }

    /**
     * QuickAction 버튼을 클릭했을 때, 다이얼로그를 보여주고
     * 다이얼로그 내의 버튼을 클릭했을 때 각 버튼에 대한 행동을 취한다.
     *  
     * @author mozziluv
     *
     */
    class PostQuickActionDialog implements Requestable, OnClickListener {
        private QuickActionDialog quickaction;
        private Session session;
        private HttpRequest request;
        private HttpRequestManager manager;
        private DBProvider dbProvider;

        private int position;

        private static final int LIKE_REQUEST = 10;
        private static final int UN_LIKE_REQUEST = 11;
        private static final int DELETE_REQUEST = 12;

        /**
         * 기본 생성자.
         * 
         * @param position 현재 QuickAction 버튼이 눌린 position
         */
        public PostQuickActionDialog(int position) {
            this.quickaction = new QuickActionDialog(context);
            this.session = Session.getInstance(context);
            this.manager = HttpRequestManager.getInstance(context);
            this.dbProvider = DBProvider.getInstance(context);

            this.position = position;

            // 아이템(버튼) 추가.
            ActionItem commentAction = new ActionItem();
            commentAction.setIcon(MatjiConstants.drawable(R.drawable.icon_memo_talk));
            quickaction.addActionItem(commentAction);

            ActionItem likeAction = new ActionItem();
            likeAction.setIcon(MatjiConstants.drawable(R.drawable.icon_memo_likehand));
            quickaction.addActionItem(likeAction);

            // 자신이 작성한 이야기일 경우 수정, 삭제 버튼도 추가.
            if (isMine((Post) data.get(position))) {
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
                            Log.d("Matji", "comment button click");
                            gotoCommentActivity();
                        } else if (pos == 1) {
                            Log.d("Matji", "like button click");
                            likePost();
                        } else if (pos == 2) {
                            Log.d("Matji", "edit button click");

                        } else if (pos == 3) {
                            Log.d("Matji", "delete button click");
                            deletePost();
                        }
                    }
                }
            });
        }

        /**
         * 댓글 작성 버튼을 클릭했을 때,
         * 댓글 페이지로 이동하고 키보드가 바로 올라오도록 한다.
         */
        public void gotoCommentActivity() {
            Intent intent = new Intent(context, PostActivity.class);
            intent.putExtra(PostActivity.POSTS, data);
            intent.putExtra(PostActivity.POSITION, position);
            intent.putExtra(PostActivity.SHOW_KEYBOARD, true);
            context.startActivity(intent);
        }

        /**
         * 좋아요 버튼을 클릭했을 때,
         * 현재 이 이야기를 좋아요 했는지를 판단한 후 그에 따라 처리한다.
         */
        public void likePost() {
            if (!manager.isRunning()) {
                Post post = (Post) data.get(position);
                if (dbProvider.isExistLike(post.getId(), DBProvider.POST)){
                    unlikeRequest(post);
                } else {
                    likeRequest(post);
                }
            }
        }

        /**
         * Like 요청
         * @param post Like할 이야기
         */
        private void likeRequest(Post post) {
            if (request == null || !(request instanceof LikeHttpRequest)) {
                request = new LikeHttpRequest(context);
            }

            ((LikeHttpRequest) request).actionPostLike(post.getId());
            manager.request(spinnerContainer, request, LIKE_REQUEST, this);
            ((Post) data.get(position)).setLikeCount(post.getLikeCount() + 1);
        }

        /**
         * Unlike 요청
         * @param post UnLike할 이야기
         */
        private void unlikeRequest(Post post) {
            if (request == null || !(request instanceof LikeHttpRequest)) {
                request = new LikeHttpRequest(context);
            }

            ((LikeHttpRequest) request).actionPostUnLike(post.getId());
            manager.request(spinnerContainer, request, UN_LIKE_REQUEST, this);
            ((Post) data.get(position)).setLikeCount(post.getLikeCount() - 1);
        }

        /**
         * 이야기 삭제 버튼을 클릭했을 때,
         * 현재 이 이야기를 삭제할 것인지를 묻는 Alert 윈도우를 띄우고,
         * 확인 했을 때 삭제요청을 한다.
         */
        public void deletePost() {
            final Post post = (Post) data.get(position);
            if (activity.getParent() != null) {
                activity = activity.getParent();
            }
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setTitle(R.string.default_string_delete);
            alert.setMessage(R.string.default_string_check_delete);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    deleteRequest(post);
                }
            }); 
            alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {}
            });
            alert.show();
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
                manager.request(spinnerContainer, request, DELETE_REQUEST, this);
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
            case LIKE_REQUEST:
                Like like = new Like();
                like.setForeignKey(((Post) PostSectionedAdapter.this.data.get(position)).getId());
                like.setObject(DBProvider.POST);
                dbProvider.insertLike(like);
                break;
            case UN_LIKE_REQUEST:
                dbProvider.deleteLike(((Post) PostSectionedAdapter.this.data.get(position)).getId(), DBProvider.POST);
                break;
            case DELETE_REQUEST:
                PostSectionedAdapter.this.data.remove(position);
                break;
            }
            notifyDataSetChanged();
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
}