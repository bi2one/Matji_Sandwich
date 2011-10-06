package com.matji.sandwich.location;

import android.location.Location;
import com.matji.sandwich.exception.MatjiException;

public interface MatjiLocationListener {
    public void onLocationChanged(int startTag, Location location);
    public void onLocationExceptionDelivered(int startTag, MatjiException e);
}