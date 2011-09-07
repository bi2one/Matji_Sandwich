package com.matji.sandwich.widget.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.matji.sandwich.Refreshable;
import com.matji.sandwich.StoreMainActivity;
import com.matji.sandwich.base.Identifiable;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.listener.LikeListener;
import com.matji.sandwich.widget.title.button.HomeButton;
import com.matji.sandwich.widget.title.button.LikeButton;
import com.matji.sandwich.widget.title.button.LikeButton.Likeable;
import com.matji.sandwich.widget.title.button.StorePostWriteButton;
import com.matji.sandwich.widget.title.button.TitleImageButton;

/**
 * {@link StoreMainActivity}에서 사용하는 Titlebar.
 * Titlebar에서 {@link Store} 객체를 사용해야 하므로 생성자에서 Store 정보를 무조건 받도록 한다. 
 * 
 * @author mozziluv
 *
 */
public class StoreTitle extends TitleContainerTypeLRR implements Likeable {    
	
    private LikeListener likeListener;
    private Refreshable refreshable;
    private Store store;
    private ViewGroup spinnerContainer;
    
    public StoreTitle(Context context, Store store) {
		super(context);
		init();
	}

	public StoreTitle(Context context, Store store, Identifiable identifiable, ViewGroup pinnerContainer) {
	    super(context);
	    init();
        setIdentifiable(identifiable);
        setSpinnerContainer(spinnerContainer);
        setRefreshable(refreshable);
        setStore(store);
	}
	
	public StoreTitle(Context context, AttributeSet attr) {
	    super(context, attr);
	    init();
	}

	protected void init() {
	    ((LikeButton) rightButton1).setLikeable(this);
	}
	
	public void setStore(Store store) {
	    this.store = store;
        likeListener = new LikeListener((Identifiable) getContext(), getContext(), store, spinnerContainer) {
            
            @Override
            public void postUnlikeRequest() {
                StoreTitle.this.store.setLikeCount(StoreTitle.this.store.getLikeCount() - 1);
                refresh();
            }
            
            @Override
            public void postLikeRequest() {
                StoreTitle.this.store.setLikeCount(StoreTitle.this.store.getLikeCount() + 1);
                refresh();
            }
        };
	    setTitle(store.getName());
        refresh();
	}
	
	public void syncSwitch() {
	       if (likeListener.isExistLike()) {
	            ((Switchable) rightButton1).on();
	        } else {
	            ((Switchable) rightButton1).off();
	        }
	}
	
	public void refresh() {
	    syncSwitch();
	    if (refreshable != null)
	        refreshable.refresh(StoreTitle.this.store);
	}
	
	public void setIdentifiable(Identifiable identifiable) {
	    ((LikeButton) rightButton1).setIdentifiable(identifiable);
	}

    public void setSpinnerContainer(ViewGroup spinnierContainer) {
        this.spinnerContainer = spinnierContainer;
    }
    
    public void setRefreshable(Refreshable refreshable) {
        this.refreshable = refreshable;
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
        return new StorePostWriteButton(getContext());
    }

    @Override
    public void like() {
        likeListener.like();
    }
}