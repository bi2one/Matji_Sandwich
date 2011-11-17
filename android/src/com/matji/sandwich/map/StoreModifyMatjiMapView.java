package com.matji.sandwich.map;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
import com.matji.sandwich.R;
import com.matji.sandwich.Requestable;
import com.matji.sandwich.data.GeocodeAddress;
import com.matji.sandwich.data.MatjiData;
import com.matji.sandwich.exception.MatjiException;
import com.matji.sandwich.http.HttpRequestManager;
import com.matji.sandwich.http.request.GeocodeHttpRequest;
import com.matji.sandwich.http.spinner.SpinnerFactory;
import com.matji.sandwich.location.GpsManager;
import com.matji.sandwich.location.MatjiLocationListener;
import com.matji.sandwich.session.SessionMapUtil;
import com.matji.sandwich.util.GeocodeUtil;
import com.matji.sandwich.util.adapter.LocationToGeoPointAdapter;
import com.matji.sandwich.widget.dialog.SimpleAlertDialog;

public class StoreModifyMatjiMapView extends RelativeLayout implements MatjiMapViewInterface, Requestable, MatjiMapCenterListener, ClickListenerOverlay.OnClickListener, View.OnClickListener, MatjiLocationListener {

    private static final int GPS_START_TAG = 0;
    private static final int REQUEST_REVERSE_GEOCODING = 0;

    private boolean firstChange;

    private WeakReference<Activity> activityRef;
    private MatjiMapView mapView;
    private MapController mapController;
    private WeakReference<OnClickListener> clickListenerRef;
    private GeocodeRunnable geocodeRunnable;
    private HttpRequestManager requestManager;
    private SessionMapUtil sessionMapUtil;

    private EditText storeNameText;
    private TextView addressText;
    private RelativeLayout spinnerWrapper;
    private EditText addAddressText;
    private EditText phoneNumberText;
    private RelativeLayout downButton;
    private GpsManager gpsManager;
    private Location prevLocation;

    private final AddressClickListener addrClickListener = new AddressClickListener(); 

    public StoreModifyMatjiMapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.store_modify_matji_mapview, this, true);

        mapView = (MatjiMapView) findViewById(R.id.store_modify_mapview_map);
        storeNameText = (EditText) findViewById(R.id.store_modify_store_name);
        addressText = (TextView) findViewById(R.id.store_modify_address);
        spinnerWrapper = (RelativeLayout) findViewById(R.id.store_modify_mapview_spinner_wrapper);
        addAddressText = (EditText)findViewById(R.id.store_modify_add_address);
        phoneNumberText = (EditText)findViewById(R.id.store_modify_phone_number);
        downButton = (RelativeLayout)findViewById(R.id.store_modify_mapview_button);


        gpsManager = new GpsManager(context, this);
        downButton.setOnClickListener(this);
        geocodeRunnable = new GeocodeRunnable(spinnerWrapper, context, this);
        requestManager = HttpRequestManager.getInstance();
        sessionMapUtil = new SessionMapUtil(context);
        mapController = mapView.getController();
        setMapCenterListener(this);

        addressText.setOnClickListener(addrClickListener);
        ClickListenerOverlay listenerOverlay = new ClickListenerOverlay();
        listenerOverlay.setOnClickListener(this);
        List<Overlay> overlays = mapView.getOverlays();
        overlays.add(listenerOverlay);

        firstChange = true;
    }

    public void onLocationExceptionDelivered(int startedFromTag, MatjiException e) {
        e.performExceptionHandling(getContext());
    }

    public void setStoreName(String storeName) {
        storeNameText.setText(storeName);
    }

    public String getStoreName() {
        return storeNameText.getText().toString();
    }

    public void setAddress(final String storeAddress) {
        addressText.setText(storeAddress);
    }

    public String getAddress() {
        return addressText.getText().toString();
    }

    public void setStoreAddAddress(String storeAddAddress) {
        addAddressText.setText(storeAddAddress);
    }

    public String getAddAddress() {
        return addAddressText.getText().toString();
    }

    public void setStorePhoneNumber(String storePhoneNumber) {
        phoneNumberText.setText(storePhoneNumber);
    }
    public String getPhoneNumber() {
        return phoneNumberText.getText().toString();
    }

    public GeoPoint getMapCenter() {
        return mapView.getMapCenter();
    }

    public void moveToGpsCenter() {
        gpsManager.start(GPS_START_TAG, activityRef.get());
    }

    public void init(Activity activity) {
        this.activityRef = new WeakReference<Activity>(activity);
    }
    public void setStartCenter(int lat, int lng) {
        GeoPoint point = new GeoPoint(lat, lng);
        mapController.setCenter(point);
    }

    public void requestCallBack(int tag, ArrayList<MatjiData> data) {
        switch(tag) {
        case REQUEST_REVERSE_GEOCODING:
            GeocodeAddress geocodeAddress = GeocodeUtil.getDetailedAddress(data);
            String address = geocodeAddress.getCountrySplitedAddress();
            addressText.setText(address);
            addrClickListener.setAddress(address);
        }
    }

    public void requestExceptionCallBack(int tag, MatjiException e) {
        e.performExceptionHandling(getContext());
    }

    public void onMapCenterChanged(GeoPoint point) {
        if (firstChange) {
            firstChange = false;
            return;
        }
        Log.d("=====", "changed");
        geocodeRunnable.setCenter(point);
        activityRef.get().runOnUiThread(geocodeRunnable);
    }

    public void onMapTouchUp(View v) {
        if (clickListenerRef == null) {
            return ;
        } else {
            clickListenerRef.get().onMapTouchUp(v);
        }
    }

    public void onMapTouchDown(View v) {
        if (clickListenerRef == null) {
            return ;
        } else {
            clickListenerRef.get().onMapTouchDown(v);
        }
    }

    public void onClick(View v) {
        if (clickListenerRef == null) {
            return ;
        }

        int vId = v.getId();
        if (vId == downButton.getId()) {
            clickListenerRef.get().onShowMapClick(v);
        }
    }

    public void setCenter(int lat, int lng) {
        setCenter(new GeoPoint(lat, lng));
    }

    public void setCenter(GeoPoint point) {
        mapController.zoomToSpan(sessionMapUtil.getBasicLatSpan(), sessionMapUtil.getBasicLngSpan());
        mapController.animateTo(point);
    }

    public void setOnClickListener(OnClickListener clickListener) {
        this.clickListenerRef = new WeakReference<OnClickListener>(clickListener);
    }

    public void startMapCenterThread() {
        if (!gpsManager.isRunning()) {
            mapView.startMapCenterThread();
        }
    }

    public void stopMapCenterThread() {
        mapView.stopMapCenterThread();
    }

    public void setMapCenterListener(MatjiMapCenterListener listener) {
        mapView.setMapCenterListener(listener);
    }

    public GeoPoint getBound(BoundType type) {
        return mapView.getBound(type);
    }

    public MapController getController() {
        return mapController;
    }

    public int getLatitudeSpan() {
        return mapView.getLatitudeSpan();
    }

    public int getLongitudeSpan() {
        return mapView.getLongitudeSpan();
    }



    private class GeocodeRunnable implements Runnable {
        private WeakReference<Context> contextRef;
        private WeakReference<Requestable> requestableRef;
        private GeoPoint center;
        private RelativeLayout spinnerContainer;

        public GeocodeRunnable(RelativeLayout spinnerContainer, Context context, Requestable requestable) {
            this.contextRef = new WeakReference<Context>(context);
            this.requestableRef = new WeakReference<Requestable>(requestable);
            this.spinnerContainer = spinnerContainer;
        }

        public void setCenter(GeoPoint center) {
            this.center = center;
        }

        public void run() {
            GeocodeHttpRequest geocodeRequest = new GeocodeHttpRequest(contextRef.get());
            geocodeRequest.actionReverseGeocodingByGeoPoint(center, sessionMapUtil.getCurrentCountry());
            requestManager.request(contextRef.get(), spinnerContainer, SpinnerFactory.SpinnerType.SMALL, geocodeRequest, REQUEST_REVERSE_GEOCODING, requestableRef.get());
        }

    }

    public interface OnClickListener {
        public void onShowMapClick(View v);
        public void onMapTouchUp(View v);
        public void onMapTouchDown(View v);
    }

    public void onLocationChanged(int startedFromTag, Location location) {
        if (prevLocation != null) {
            if (prevLocation.getAccuracy() >= location.getAccuracy()) {
                mapView.startMapCenterThread(new LocationToGeoPointAdapter(location));
                gpsManager.stop();
            }
        }
        prevLocation = location;
        setCenter(new LocationToGeoPointAdapter(location));
    }

    class AddressClickListener implements View.OnClickListener {
        private String addr;

        public void setAddress(String addr) {
            this.addr = addr;
        }

        @Override
        public void onClick(View v) {
            if (addr != null || !addr.equals("")) {
                new SimpleAlertDialog(getContext(), addr).show();
            }
        }                
    }
}
