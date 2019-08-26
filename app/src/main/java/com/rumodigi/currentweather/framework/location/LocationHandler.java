package com.rumodigi.currentweather.framework.location;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.rumodigi.currentweather.di.component.ActivityComponent;
import com.rumodigi.currentweather.di.component.ApplicationComponent;

import javax.inject.Inject;

public class LocationHandler {

    public static final int LOCATION_REQUEST_CODE = 1000;
    private static final int GOOGLE_API_ERROR_CODE = 1001;
    private static final int LOCATION_REQUEST_INTERVAL = 0;
    private static final int LOCATION_REQUEST_FASTEST_INTERVAL = 0;

    private final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final int GRANTED = PackageManager.PERMISSION_GRANTED;


    private final Context context;
    private final ActivityComponent activityComponent;
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final LocationManager locationManager;
    private final LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private LocationResultListener locationResultListener;

    @Inject
    public LocationHandler(ApplicationComponent applicationComponent, ActivityComponent activityComponent) {
        this.activityComponent = activityComponent;
        this.context = applicationComponent.getContext();
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(LOCATION_REQUEST_INTERVAL)
                .setFastestInterval(LOCATION_REQUEST_FASTEST_INTERVAL);
    }

    public void setUpLocationCallback(LocationResultListener locationResultListener) {
        this.locationResultListener = locationResultListener;
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                locationResultListener.setLocation(locationResult.getLastLocation());
                fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            }
        };
    }

    public void getUserLocation() {
        if (!isGooglePlayServicesAvailable()){
            return;
        }
        if (!isPermissionGranted()) {
            requestPermission();
            return;
        }
        if (!isLocationEnabled()) {
            promptUserToEnableLocation();
            return;
        }
        getLastKnownLocation();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(context, FINE_LOCATION) == GRANTED &&
                ContextCompat.checkSelfPermission(context, COARSE_LOCATION) == GRANTED;
    }

    private void requestPermission() {
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};
        ActivityCompat.requestPermissions(activityComponent.getActivity(), permissions, LOCATION_REQUEST_CODE);
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(context);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activityComponent.getActivity(), status, GOOGLE_API_ERROR_CODE).show();
            }
            return false;
        }
        return true;
    }

    private void promptUserToEnableLocation() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        LocationServices
                .getSettingsClient(activityComponent.getActivity())
                .checkLocationSettings(builder.build())
                .addOnSuccessListener(locationSettingsResponse -> getLastKnownLocation())
                .addOnFailureListener(e -> {
                    int status = ((ApiException) e).getStatusCode();
                    if (status == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(activityComponent.getActivity(), LOCATION_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException exception) {
                            exception.printStackTrace();
                        }
                    }
                });
    }

    @SuppressWarnings("MissingPermission")
    private void getLastKnownLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(locationTask -> {
            Location location = locationTask.getResult();
            if (location == null) {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            } else {
                locationResultListener.setLocation(location);
            }
        });
    }
}
