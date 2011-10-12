package com.matji.sandwich;

import java.util.ArrayList;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.StoreFoodHttpRequest;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.title.CompletableTitle;
import com.matji.sandwich.widget.title.CompletableTitle.Completable;

public class StoreFoodAddActivity extends BaseActivity implements Completable, Requestable {

    private StoreFoodHttpRequest request;
    private HttpRequestManager manager;

    private CompletableTitle title;
    private EditText field;
    private TextView guide;
    private Store store;

    public static final String STORE = "StoreFoodAddActivity.store";

    public int setMainViewId() {
        return R.id.activity_store_food_add;
    }

    @Override
    protected void init() {
        setContentView(R.layout.activity_store_food_add);

        store = (Store) getIntent().getParcelableExtra(STORE);

        request = new StoreFoodHttpRequest(this);
        manager = HttpRequestManager.getInstance();

        title = (CompletableTitle) findViewById(R.id.Titlebar);
        field = (EditText) findViewById(R.id.store_food_name_field);
        guide = (TextView) findViewById(R.id.store_food_add_guide);

        title.setTitle(R.string.title_store_food_add);
        title.setCompletable(this);
        title.lockCompletableButton();
        TextWatcher tw = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if (field.getText().length() < MatjiConstants.MIN_FOOD_NAME_LENGTH) {
                    title.lockCompletableButton();
                } else {
                    title.unlockCompletableButton();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };
        field.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(MatjiConstants.MAX_FOOD_NAME_LENGTH)
        });
        field.addTextChangedListener(tw);
        guide.setText(
                String.format(
                        MatjiConstants.string(R.string.store_food_add_guide),
                        store.getName()));
    }

    @Override
    public void complete() {
        title.lockCompletableButton();
        request.actionNew(store.getId(), field.getText().toString().trim());
        manager.request(getApplicationContext(), getMainView(), request, 0, this);
    }

    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        finish();
    }

    @Override
    public void requestExceptionCallBack(int tag, MatjiException e) {
        title.unlockCompletableButton();
        e.performExceptionHandling(this);
    }
}