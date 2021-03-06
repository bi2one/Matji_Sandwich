package com.matji.sandwich.widget.cell;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.matji.sandwich.R;
import com.matji.sandwich.StoreDetailInfoTabActivity;
import com.matji.sandwich.base.BaseActivity;
import com.matji.sandwich.data.AttachFile;
import com.matji.sandwich.data.Food;
import com.matji.sandwich.data.SimpleTag;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.http.util.ImageLoader;
import com.matji.sandwich.http.util.ImageLoader.ImageSize;
import com.matji.sandwich.http.util.ImageLoader.UrlType;
import com.matji.sandwich.util.MatjiConstants;

/**
 * UI 상의 유저 인트로 셀.
 * 기본적으로 유저 정보 페이지 안에 들어 있는 뷰이므로,
 * SharedMatjiData의 top에 해당 유저의 데이터 모델이 저장되어 있어야 한다.
 * 
 * @author mozziluv
 *
 */
public class StoreInfoCell extends Cell {

    private Store store;
    private ImageLoader imgLoader;
    private ImageView thumbnail;

    /**
     * 기본 생성자 (Java Code)
     * 
     * @param context
     */
    public StoreInfoCell(Context context) {
        super(context, R.layout.cell_store_info);
    }

    /**
     * 기본 생성자 (XML)
     * 
     * @param context
     * @param attr
     */
    public StoreInfoCell(Context context, AttributeSet attr) {
        super(context, attr, R.layout.cell_store_info);
        setId(R.id.StoreInfoCell);
    }

    /**
     * Store 정보를 같이 전달 받을 때 사용하는 생성자.
     * 
     * @param context
     * @param store 이 Cell의 맛집 데이터
     */
    public StoreInfoCell(Context context, Store store) {
        super(context, R.layout.cell_store_info);
        setStore(store);
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        super.init();
        imgLoader = new ImageLoader(getContext(), R.drawable.img_restaurant);
        thumbnail = (ImageView) findViewById(R.id.cell_store_thumbnail);
    }

    public void setStore(Store store) {
        this.store = store;

        String tel = store.getTel();
        if (tel == null || tel.equals(""))
            tel = MatjiConstants.string(R.string.cell_store_not_exist_tel);
        ((TextView) findViewById(R.id.cell_store_tel)).setText(tel);

        String addr = store.getAddress();
        if (addr == null || addr.equals(""))
            addr = MatjiConstants.string(R.string.cell_store_not_exist_addr);
        ((TextView) findViewById(R.id.cell_store_address)).setText(addr);
        
        ArrayList<SimpleTag> simpleTags = store.getTags();
        if (simpleTags.size() == 0) {
            ((TextView) findViewById(R.id.cell_store_tag)).setText(MatjiConstants.string(R.string.cell_store_not_exist_tag));
        } else {
            String tags = simpleTags.get(0).getTag();
            for (int i = 1 ; i < simpleTags.size() ; i++) 
                tags += ", " + simpleTags.get(i).getTag();
            
            ((TextView) findViewById(R.id.cell_store_tag)).setText(tags);
        }

        ArrayList<Food> foodList = store.getFoods();
        if (foodList.isEmpty()) {
            ((TextView) findViewById(R.id.cell_store_food)).setText(MatjiConstants.string(R.string.cell_store_not_exist_menu));
        } else {
            String foods = foodList.get(0).getName();
            for (int i = 1; i < foodList.size(); i++) {
                foods += ", " + foodList.get(i).getName();
            }
            ((TextView) findViewById(R.id.cell_store_food)).setText(foods);
            
        }
            
        refresh();
    }

    /**
     * 
     */
    @Override
    public void gotoDetailPage() {
        Intent intent = new Intent(getContext(), StoreDetailInfoTabActivity.class);
        intent.putExtra(StoreDetailInfoTabActivity.STORE, (Parcelable) store);
        ((Activity) getContext()).startActivityForResult(intent, BaseActivity.STORE_DETAIL_INFO_TAB_ACTIVITY);
    }
    
    /**
     * Refresh 가능한 정보들을 refresh
     */
    public void refresh() {
        AttachFile file = store.getFile();
        if (file != null) {
            imgLoader.DisplayImage((Activity) getContext(), UrlType.ATTACH_FILE, ImageSize.SMALL, thumbnail, file.getId());
        } else {
            Drawable defaultImage = getResources().getDrawable(R.drawable.img_restaurant);
            thumbnail.setImageDrawable(defaultImage);
        }
    }
}