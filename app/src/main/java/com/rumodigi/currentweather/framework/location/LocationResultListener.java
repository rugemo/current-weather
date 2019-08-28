package com.rumodigi.currentweather.framework.location;

import android.location.Location;

public interface LocationResultListener {

    void locationPermissionPreviouslyDenied();

    void locationPermissionPreviouslyDeniedWithNeverAskAgain();

    void setLocation(Location location);
}
