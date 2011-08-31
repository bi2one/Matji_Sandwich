package com.matji.sandwich;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class WriteMessageActivity extends BaseActivity implements Completable {

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
    protected void init() {
        // TODO Auto-generated method stub
        super.init();
        setContentView(R.layout.activity_write_message);
        
        ((CompletableTitle) findViewById(R.id.Titlebar)).setCompletable(this);
        messageField = (EditText) findViewById(R.id.write_message_message_field);
        receivedUserListText = (TextView) findViewById(R.id.write_message_received_user_list);
        receivedUserListButton = (ImageButton) findViewById(R.id.write_message_received_user_list_btn);
        receivedMessageListButton = (ImageButton) findViewById(R.id.write_message_received_message_list_btn);
        
        receivedUserListText.setText(MatjiConstants.string(R.string.write_message_received_users));
        receivedUserListButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                selectUser();
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
    
    public void send(User user, String message) {
        
    }
    
    public void send(ArrayList<User> users, String message) {
        
    }

    @Override
    public void complete() {

    }
}