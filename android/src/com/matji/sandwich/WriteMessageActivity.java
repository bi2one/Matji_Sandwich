package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.User;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.MessageHttpRequest;
import com.matji.sandwich.session.Session;
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class WriteMessageActivity extends BaseActivity implements Completable, Requestable {

    public static final String USER = "user";

    private Toast writingMessageToast;
    private Toast selectReceivedUserToast;

    private HttpRequestManager manager;
    private HttpRequest request;

    private ArrayList<User> receivedUsers;

    private EditText messageField;
    private TextView receivedUserListText;    
    private ImageButton receivedUserListButton;

    private CompletableTitle title;
    
    @Override
    public int setMainViewId() {
        return R.id.write_message_activity;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                messageField.requestFocus();
                KeyboardUtil.showKeyboard(WriteMessageActivity.this, messageField);
            }
        }, 100);
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        super.init();
        setContentView(R.layout.activity_write_message);

        manager = HttpRequestManager.getInstance();

        receivedUsers = new ArrayList<User>();
        title = (CompletableTitle) findViewById(R.id.Titlebar);
        messageField = (EditText) findViewById(R.id.write_message_message_field);
        receivedUserListText = (TextView) findViewById(R.id.write_message_received_user_list);
        receivedUserListButton = (ImageButton) findViewById(R.id.write_message_received_user_list_btn);

        writingMessageToast = Toast.makeText(this, R.string.writing_content_message, Toast.LENGTH_SHORT);
        selectReceivedUserToast = Toast.makeText(this, R.string.write_message_select_user, Toast.LENGTH_SHORT);
        
        title.setCompletable(this);
        title.setTitle(R.string.write_message);        
        
        User user = getIntent().getParcelableExtra(USER);
        if (user != null && user.getId() != Session.getInstance(this).getCurrentUser().getId()) {
            // intent에 user가 추가되어 있으면 
            // 타이틀 바의 쪽지 보내기 버튼을 통해 온 것이므로
            // 받는 사람에 전달받은 user를 우선 추가해준다.
            receivedUsers.add(user);
            refresh();
        }

        receivedUserListButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteMessageActivity.this, ReceivedUserActivity.class);
                intent.putParcelableArrayListExtra(ReceivedUserActivity.RECEIVED_USERS, receivedUsers);
                startActivityForResult(intent, RECEIVED_USER_ACTIVITY);
            }
        });
    }

    public void refresh() {
        refreshReceivedUserListText();
    }
    
    public void refreshReceivedUserListText() {
        String receivedUserList = "";
        if (!receivedUsers.isEmpty()) {
            receivedUserList += receivedUsers.get(0).getNick();
            for (int i = 1; i < receivedUsers.size(); i++) 
                receivedUserList += ", " + receivedUsers.get(i).getNick();
        }
        receivedUserListText.setText(receivedUserList);
    }
    public void send(ArrayList<User> receivedUsers, String message) {
        if (request == null || !(request instanceof MessageHttpRequest)) {
            request = new MessageHttpRequest(this);
        }
        
        int[] receivedUserIds = new int[receivedUsers.size()];
        for (int i = 0; i < receivedUserIds.length; i++) {
            receivedUserIds[i] = receivedUsers.get(i).getId();
        }
        
        ((MessageHttpRequest) request).actionNew(receivedUserIds, message);
        manager.request(getApplicationContext(), getMainView(), request, HttpRequestManager.MESSAGE_NEW_REQUEST, this);
    }

    @Override
    public void complete() {
        String message = messageField.getText().toString().trim();
        if (message.equals("")) {
            writingMessageToast.show();
        } else if (receivedUsers.size() == 0) {
            selectReceivedUserToast.show();
        } else {
            title.lock();
            send(receivedUsers, message);
        }
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch(tag) {
        case HttpRequestManager.MESSAGE_NEW_REQUEST:
            title.unlock();
            finish();
            break;
        }
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        title.unlock();
        e.performExceptionHandling(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case RECEIVED_USER_ACTIVITY:
            if (resultCode == RESULT_OK) {
                receivedUsers = data.getParcelableArrayListExtra(ReceivedUserActivity.RECEIVED_USERS);
                refresh();
            }
            break;
        }
    }
}