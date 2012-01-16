package com.matji.sandwich;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.matji.sandwich.base.BaseMapActivity;
import com.matji.sandwich.data.Store;
import com.matji.sandwich.overlay.StoreLocationOverlay;
import com.matji.sandwich.util.MatjiConstants;
import com.matji.sandwich.util.PhoneCallUtil;
import com.matji.sandwich.widget.HighlightHeader;
import com.matji.sandwich.widget.title.StoreLocationTitle;

public class StoreLocationMapActivity extends BaseMapActivity {
    public static String INTENT_STORE = "StoreLocationMapActivity.store";
    private MyLocationOverlay myLocationOverlay;
    private PhoneCallUtil phoneCallUtil;
    private MapController mapController;
    private StoreLocationTitle titleBar;
    private HighlightHeader header;
    private TextView addressView;
    private TextView phoneView;
    private Intent arguments;
    private MapView mapView;
    private Store store;

    public int setMainViewId() {
        return R.id.activity_store_location_map;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_location_map);

        titleBar = (StoreLocationTitle)findViewById(R.id.activity_store_location_title);
        header = (HighlightHeader)findViewById(R.id.activity_store_location_header);
        mapView = (MapView)findViewById(R.id.map_view);
        mapController = mapView.getController();

        addressView = (TextView)findViewById(R.id.activity_store_location_address);
        phoneView = (TextView)findViewById(R.id.activity_store_location_phone);

        arguments = getIntent();
        store = (Store)arguments.getParcelableExtra(INTENT_STORE);
        titleBar.setView(getMainView());
        setStore(store);

        phoneCallUtil = new PhoneCallUtil(this);
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        mapView.getOverlays().add(myLocationOverlay);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        myLocationOverlay.enableMyLocation();
    }
    
    @Override
    protected void onStop() {
        myLocationOverlay.disableMyLocation();
        super.onStop();
    }
    
    private void setStore(Store store) {
        header.setTitle(String.format(MatjiConstants.string(R.string.store_location_header), store.getName()));
        addressView.setText(store.getAddress());
        if (store.getTel() != null && store.getTel().trim().equals(""))
            phoneView.setVisibility(View.GONE);
        else
            phoneView.setText(store.getTel());
        setOverlay(store);
    }

    private void setOverlay(Store store) {
        mapController.setCenter(store.getGeoPoint());
        List<Overlay> overlays = mapView.getOverlays();
        overlays.add(new StoreLocationOverlay(this, store.getGeoPoint()));
        mapView.invalidate();
    }

    public void onAddressClick(View v) {
        mapController.animateTo(store.getGeoPoint());
    }

    public void onPhoneClick(View v) {
        phoneCallUtil.call(store.getTelNotDashed());
    }
}