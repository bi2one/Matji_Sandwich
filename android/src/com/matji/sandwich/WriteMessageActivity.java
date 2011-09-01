package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
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
import com.matji.sandwich.util.KeyboardUtil;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class WriteMessageActivity extends BaseActivity implements Completable, Requestable {

    public static final String USER = "user";
    
    private Toast toast;
    
    private HttpRequestManager manager;
    private HttpRequest request;

    private final int MAX_RECEIVED_USER_SIZE = 5;
    private int receivedUserCount = 0;
    private User[] receivedUsers;
    
    private EditText messageField;
    private TextView receivedUserListText;    
    private ImageButton receivedUserListButton;
    private ImageButton receivedMessageListButton;
    @Override
    public int setMainViewId() {
        // TODO Auto-generated method stub
        return R.id.write_message_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
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

        toast = Toast.makeText(this, R.string.writing_content_message, Toast.LENGTH_SHORT);
        manager = HttpRequestManager.getInstance(this);
        
        receivedUsers = new User[MAX_RECEIVED_USER_SIZE];
        ((CompletableTitle) findViewById(R.id.Titlebar)).setCompletable(this);
        ((CompletableTitle) findViewById(R.id.Titlebar)).setTitle(R.string.write_message);
        messageField = (EditText) findViewById(R.id.write_message_message_field);
        receivedUserListText = (TextView) findViewById(R.id.write_message_received_user_list);
        receivedUserListButton = (ImageButton) findViewById(R.id.write_message_received_user_list_btn);
        receivedMessageListButton = (ImageButton) findViewById(R.id.write_message_received_message_list_btn);
        
        receivedUserListText.setText(MatjiConstants.string(R.string.write_message_received_users));
        User user = getIntent().getParcelableExtra(USER);
        if (user != null) {
            // intent에 user가 추가되어 있으면 
            // 타이틀 바의 쪽지 보내기 버튼을 통해 온 것이므로
            // 받는 사람에 전달받은 user를 우선 추가해준다.
            receivedUsers[receivedUserCount++] = user;
            receivedUserListText.setText(
                    receivedUserListText.getText() + user.getNick());
        }
        
        receivedUserListButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WriteMessageActivity.this, ReceivedUserActivity.class));
//                selectUser();
            }
        });
        receivedMessageListButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                showReceivedMessage();
            }
        });
    }

    public void selectUser() {
    }
    
    public void showReceivedMessage() {
        
    }
    
    public void send(User[] users, String message) {
        if (request == null || !(request instanceof MessageHttpRequest)) {
            request = new MessageHttpRequest(this);
        }
        
        for (int i = 0; i < receivedUserCount; i++) {
            ((MessageHttpRequest) request).actionNew(users[i].getId(), message);
            manager.request(getMainView(), request, HttpRequestManager.MESSAGE_NEW_REQUEST, this);            
        }
    }

    @Override
    public void complete() {
        String message = messageField.getText().toString().trim();
        if (message.equals("")) {
            toast.show();
        } else {
            send(receivedUsers, message);
        }
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch(tag) {
        case HttpRequestManager.MESSAGE_NEW_REQUEST:
            finish();
            break;
        }
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(this);
    }
}