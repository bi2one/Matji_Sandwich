package com.matji.sandwich;

import android.content.Intent;

public interface ActivityStartable {
    public void activityResultDelivered(int requestCode, int resultCode, Intent data);
}