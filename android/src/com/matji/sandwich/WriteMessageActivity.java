//package com.matji.sandwich;
//
//import java.util.ArrayList;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.matji.sandwich.base.BaseActivity;
//import com.matji.sandwich.data.MatjiData;
//import com.matji.sandwich.data.User;
//import com.matji.sandwich.exception.MatjiException;
//import com.matji.sandwich.http.HttpRequestManager;
//import com.matji.sandwich.http.request.HttpRequest;
//import com.matji.sandwich.http.request.MessageHttpRequest;
//
//public class WriteMessageActivity extends BaseActivity implements Requestable {
//    private static final int MESSAGE_WRITE_REQUEST = 11;
//    //	private static final int  = 12;
//
//    private HttpRequestManager manager;
//    private HttpRequest request;
//
//    private EditText messageField;
//    private EditText receivedUserField;
//
//    private int user_id;
//
//    private static final int USER_ID_IS_NULL = -1;
//
//    public int setMainViewId() {
//        return R.id.contentWrapper;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_write_message);
//
//        manager = HttpRequestManager.getInstance(getApplicationContext());
//
//        user_id = getIntent().getIntExtra("user_id", USER_ID_IS_NULL);
//
//        messageField = (EditText) findViewById(R.id.write_message_message_field);
//        receivedUserField = (EditText) findViewById(R.id.write_message_received_user_field);
//        receivedUserField.setFocusable(false);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        messageField.requestFocus();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//        case RECEIVED_USER_ACTIVITY:
//            if (resultCode == RESULT_OK) {
//                if (data != null) {
//                    User user = (User) data.getParcelableExtra("user");
//                    if (user != null) {
//                        receivedUserField.setText(user.getNick());
//                        user_id = user.getId();
//                    }
//                }
//            }
//            break;
//        }
//    }
//
//    public HttpRequest sendRequest() {
//        if (request == null || !(request instanceof MessageHttpRequest)) {
//            request = new MessageHttpRequest(getApplicationContext());
//        }
//        ((MessageHttpRequest) request).actionNew(user_id, messageField.getText().toString().trim());
//
//        return request;
//    }
//
//    public void onAddButtonClicked(View v) {
//        Intent intent = new Intent(this, ReceivedUserActivity.class);
//        startActivityForResult(intent, RECEIVED_USER_ACTIVITY);
//    }
//
//    public void onSendButtonClicked(View v) {
//        if (messageField.getText().toString().trim().equals("")) {
//            Toast.makeText(getApplicationContext(), R.string.writing_content_message, Toast.LENGTH_SHORT).show();
//        } else if (user_id == USER_ID_IS_NULL) {
//            Toast.makeText(getApplicationContext(), R.string.writing_content_received_user, Toast.LENGTH_SHORT).show();
//        } else {
//            manager.request(getMainView(), sendRequest(), MESSAGE_WRITE_REQUEST, this);
//        }
//    }
//
//    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
//        switch(tag) {
//        case MESSAGE_WRITE_REQUEST:
//            setResult(RESULT_OK);
//            finish();
//            break;
//
//        }
//    }
//
//    public void requestExceptionCallBack(int tag, MatjiException e) {
//        e.performExceptionHandling(getApplicationContext());
//    }
//} 