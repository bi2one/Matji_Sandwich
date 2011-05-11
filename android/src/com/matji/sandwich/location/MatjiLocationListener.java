package com.matji.sandwich.location;

import android.location.Location;
import com.matji.sandwich.exception.MatjiException;

public interface MatjiLocationListener {
    public void onLocationChanged(Location location);
    public void onLocationExceptionDelivered(MatjiException e);
}