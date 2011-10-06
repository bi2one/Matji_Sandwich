package com.matji.sandwich.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.matji.sandwich.adapter.StoreFoodAdapter;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.RequestCommand;
import com.matji.sandwich.http.request.StoreFoodHttpRequest;


public class StoreFoodListView extends RequestableMListView {
    protected HttpRequest request;

    private Store store; 

    public StoreFoodListView(Context context, AttributeSet attrs) {
        super(context, attrs, new StoreFoodAdapter(context), 50);
        init();
    }

    protected void init() {
        setDivider(null);
        setSelector(android.R.color.transparent);
        setCacheColorHint(Color.TRANSPARENT);
        setVerticalScrollBarEnabled(false);
    }

    public void setStore(Store store) {
        this.store = store;
        ((StoreFoodAdapter) getMBaseAdapter()).setStore(store);
    }

    public void setIdentifiable(Identifiable identifiable) {
        ((StoreFoodAdapter) getMBaseAdapter()).setIdentifiable(identifiable);
    }
    
    @Override
    public RequestCommand request() {
        if (request == null || !(request instanceof StoreFoodHttpRequest)) {
            request = new StoreFoodHttpRequest(getContext());
        }
        ((StoreFoodHttpRequest) request).actionList(store.getId(), getPage(), getLimit());
        return request;
    }
    
    @Override
    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        StoreFood dummy = new StoreFood();// 더미 StoreFood
        Food dummyFood = new Food();
        dummyFood.setName(StoreFoodAdapter.ADD_STORE_FOOD);
        dummy.setFood(dummyFood);
        dummy.setAccuracy(true);
        data.add(dummy);
        super.requestCallBack(tag, data);
    }

    @Override
    public void onListItemClick(int position) {}
}