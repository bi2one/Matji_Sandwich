package com.matji.sandwich;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.User;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.ReceivedUserListView;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class ReceivedUserActivity extends BaseActivity implements Completable {
    
    public static final String RECEIVED_USERS = "users";
    public static final int MAX_RECEIVED_USER_SIZE = 5;
    
    private ArrayList<User> receivedUsers;
    private TextView receivedUserListText;
    
    private Toast overflowToast;
    
    public int setMainViewId() {
        return R.id.activity_received_user;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_user);

        String message = String.format(
                MatjiConstants.string(R.string.overflow_received_user),
                MAX_RECEIVED_USER_SIZE);
        overflowToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        
        ((CompletableTitle) findViewById(R.id.Titlebar)).setCompletable(this);
        ((CompletableTitle) findViewById(R.id.Titlebar)).setTitle(R.string.write_message_received_user2);
        receivedUserListText = (TextView) findViewById(R.id.write_message_received_user_list);

        ReceivedUserListView listView = (ReceivedUserListView) findViewById(R.id.received_user_list);
        listView.setActivity(this);
        listView.requestReload();
        listView.setAdapterOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                onListItemClick(v);
            }
        });
                
        receivedUsers = getIntent().getParcelableArrayListExtra(RECEIVED_USERS);
        refresh();
    }

    public void onListItemClick(View v) {
        addReceivedUser((User) v.getTag());
    }
    
    public void refresh() {
        refreshReceivedUserListText();
    }
    
    public void refreshReceivedUserListText() {
        String receivedUserList = "";
        if (receivedUsers.size() > 0) {
            receivedUserList += receivedUsers.get(0).getNick();
            for (int i = 1; i < receivedUsers.size(); i++) 
                receivedUserList += ", " + receivedUsers.get(i).getNick();
        }
        receivedUserListText.setText(receivedUserList);
    }
    
    public void addReceivedUser(User user) {
        Log.d("Matji", receivedUsers.size()+"");
        if (receivedUsers.size() >= MAX_RECEIVED_USER_SIZE) {
            overflowToast.show();
        } else {
            if (!isExistUser(user)) {
                receivedUsers.add(user);
                refresh();
            }
        }
    }

    public boolean isExistUser(User user) {
        for (User addedUser : receivedUsers) {
            if (addedUser.getId() == user.getId()) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void complete() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(RECEIVED_USERS, receivedUsers);
        setResult(RESULT_OK, intent);
        finish();
    }
}