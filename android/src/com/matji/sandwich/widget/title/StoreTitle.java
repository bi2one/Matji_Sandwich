package com.matji.sandwich.widget.title;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;

import com.matji.sandwich.Refreshable;
import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.LikeButton;
import com.matji.sandwich.widget.title.button.LikeButton.Likeable;
import com.matji.sandwich.widget.title.button.StorePostWriteButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;

/**
 * {@link StoreMainActivity}에서 사용하는 Titlebar.
 * 
 * @author mozziluv
 *
 */
public class StoreTitle extends TitleContainerTypeLRR implements Refreshable {
    private StorePostWriteButton storePostWriteButton;
    private Store store;
    
    public StoreTitle(Context context) {
	super(context);
    }

    public StoreTitle(Context context, Store store, Identifiable identifiable) {
	super(context);
        setIdentifiable(identifiable);
        setStore(store);
    }
	
    public StoreTitle(Context context, AttributeSet attr) {
	super(context, attr);
    }
	
    public void setStore(Store store) {
	this.store = store;
	setTitle(store.getName());
    }
	
    public void setIdentifiable(Identifiable identifiable) {
	((LikeButton) rightButton1).setIdentifiable(identifiable);
    }

    public void setLikeable(Likeable likeable) {
        ((LikeButton) rightButton1).setLikeable(likeable);
    }
    
    @Override
	protected TitleImageButton getLeftButton1() {
        // TODO Auto-generated method stub
        return new HomeButton(getContext());
    }

    @Override
	protected TitleImageButton getRightButton1() {
        // TODO Auto-generated method stub
        return new LikeButton(getContext());
    }

    @Override
	protected TitleImageButton getRightButton2() {
        // TODO Auto-generated method stub
	StorePostWriteButton writeButton = new StorePostWriteButton(getContext());
	writeButton.setData(store, null);
        return writeButton;
    }

    @Override
	public void refresh() {
        ((LikeButton) rightButton1).refresh();
    } 

    @Override
	public void refresh(MatjiData data) {
        if (data instanceof Store) {
            setStore((Store) data);
            refresh();
        }        
    }

    @Override
	public void refresh(ArrayList<MatjiData> data) {}
}