package com.matji.sandwich.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.StoreFoodAddActivity;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.data.StoreFood;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.HttpRequest;
import com.matji.sandwich.http.request.StoreFoodHttpRequest;
import com.matji.sandwich.http.spinner.SpinnerFactory.SpinnerType;
import com.matji.sandwich.listener.LikeListener;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.widget.dialog.SimpleDialog;
import com.matji.sandwich.widget.dialog.SimpleListDialog;

public class StoreFoodAdapter extends MBaseAdapter {

    private HttpRequestManager manager;
    private HttpRequest request;
    
    private Store store;
    private Identifiable identifiable;

    public static final String ADD_STORE_FOOD = "StoreFoodAdapter.add_store_food";
    private SimpleListDialog dialog;

    public StoreFoodAdapter(Context context) {
        super(context);
        this.context = context;
        manager = HttpRequestManager.getInstance(context);
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setIdentifiable(Identifiable identifiable) {
        this.identifiable = identifiable;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final FoodElement foodElement;

        final StoreFood storeFood = (StoreFood) data.get(position);

        if (convertView == null) {
            foodElement = new FoodElement();
            convertView = getLayoutInflater().inflate(R.layout.row_store_food, null);
            foodElement.wrapper = convertView.findViewById(R.id.row_store_food_wrapper);
            foodElement.name = (TextView) convertView.findViewById(R.id.row_store_food_name);
            foodElement.count = (TextView) convertView.findViewById(R.id.row_store_food_like_count);
            foodElement.line = convertView.findViewById(R.id.row_store_food_horizontal_line);
            convertView.setTag(foodElement);
        } else {
            foodElement = (FoodElement) convertView.getTag();
        }

        if (data.size() == 1) {
            foodElement.wrapper.setBackgroundResource(R.drawable.txtbox_selector);
        } else if (position == 0) {
            foodElement.wrapper.setBackgroundResource(R.drawable.txtbox_t_selector);
        } else if (position == data.size() - 1) {
            foodElement.wrapper.setBackgroundResource(R.drawable.txtbox_u_selector);
        } else {
            foodElement.wrapper.setBackgroundResource(R.drawable.txtbox_c_selector);
        }

        String name = storeFood.getFood().getName();
        if (storeFood.isAccuracy()) {
            foodElement.name.setTextColor(MatjiConstants.color(R.color.matji_gray));
        } else {
            foodElement.name.setTextColor(MatjiConstants.color(R.color.matji_light_gray));
        }
        
        if (name.equals(ADD_STORE_FOOD)) {
            foodElement.name.setText(R.string.store_food_add);
            foodElement.count.setVisibility(View.GONE);
            foodElement.line.setVisibility(View.GONE);
            foodElement.wrapper.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (identifiable.loginRequired()) {
                        Intent intent = new Intent(context, StoreFoodAddActivity.class);
                        intent.putExtra(StoreFoodAddActivity.STORE, (Parcelable) store);
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            foodElement.name.setText(storeFood.getFood().getName());
            foodElement.count.setText(storeFood.getLikeCount()+"");
            foodElement.count.setVisibility(View.VISIBLE);
            foodElement.line.setVisibility(View.VISIBLE);
            foodElement.wrapper.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    CharSequence[] items = {
                        MatjiConstants.string(R.string.store_food_delicious), 
                        (storeFood.isAccuracy()) ?
                                MatjiConstants.string(R.string.store_food_not_accuracy)
                                : MatjiConstants.string(R.string.store_food_accuracy), 
                        MatjiConstants.string(R.string.default_string_cancel)
                        };

                    dialog = new SimpleListDialog(context, storeFood.getFood().getName(), items);
                    dialog.setOnClickListener(new StoreFoodLikeDialogListener(position, (ViewGroup) foodElement.wrapper));
                    dialog.show();
                }
            });
        }

        return convertView;
    }

    private class FoodElement {
        TextView name;
        TextView count;
        View wrapper;
        View line;
    }

    public HttpRequest accuracyUpRequest(int store_food_id) {
        if (request == null || !(request instanceof StoreFoodHttpRequest)) { 
            request = new StoreFoodHttpRequest(context);
        }
        
        ((StoreFoodHttpRequest) request).actionAccuracyUp(store_food_id);
        
        return request;
    }
    
    public HttpRequest accuracyDownRequest(int store_food_id) {
        if (request == null || !(request instanceof StoreFoodHttpRequest)) { 
            request = new StoreFoodHttpRequest(context);
        }
        
        ((StoreFoodHttpRequest) request).actionAccuracyDown(store_food_id);
        
        return request;
    }
    
    class StoreFoodLikeDialogListener implements com.matji.sandwich.widget.dialog.SimpleListDialog.OnClickListener, Requestable {

        private int itemPos;
        private ViewGroup spinnerContainer;
        
        public StoreFoodLikeDialogListener(int itemPos, ViewGroup spinnerContainer) {
            this.itemPos = itemPos;
            this.spinnerContainer = spinnerContainer;
        }        
        
        @Override
        public void onItemClick(SimpleDialog dialog, int position) {
            switch (position) {
            case 0: // delicious
                LikeListener listener = new LikeListener((Identifiable) context, context, (StoreFood) data.get(itemPos), spinnerContainer) {
                    
                    @Override
                    public void postUnlikeRequest() {
                        ((StoreFood) data.get(itemPos)).setLikeCount(((StoreFood) data.get(itemPos)).getLikeCount() - 1);
                        notifyDataSetChanged();
                    }
                    
                    @Override
                    public void postLikeRequest() {
                        ((StoreFood) data.get(itemPos)).setLikeCount(((StoreFood) data.get(itemPos)).getLikeCount() + 1);
                        notifyDataSetChanged();
                    }
                };
                
                listener.onClick(null);
                break;
            case 1: // report
                StoreFood storeFood = (StoreFood) data.get(itemPos);
                if (storeFood.isAccuracy()) {
                    accuracyDownRequest(storeFood.getId());
                } else {
                    accuracyUpRequest(storeFood.getId());
                }
                
                manager.request(spinnerContainer, SpinnerType.SMALL, request, HttpRequestManager.STORE_FOOD_ACCURACY_REQUEST, this);
                break;
            default: // cancel
                break;
            }
        }


        @Override
        public void requestCallBack(int tag, ArrayList<MatjiData> data) {
            switch (tag) {
            case HttpRequestManager.STORE_FOOD_ACCURACY_REQUEST:
                ((StoreFood) StoreFoodAdapter.this.data.get(itemPos)).setAccuracy(((StoreFood) data.get(0)).isAccuracy());
                notifyDataSetChanged();
                break;
            }
        }

        @Override
        public void requestExceptionCallBack(int tag, MatjiException e) {
            // TODO Auto-generated method stub
            
        }
    }
}