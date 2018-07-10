package com.example.cettorre.animalsshelter.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.cettorre.animalsshelter.view.InsertAnimalActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;


public class LocationUtility implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final long LOC_UPDATE_INTERVAL = 10000; // 10s in milliseconds
    private final long LOC_FASTEST_UPDATE = 5000; // 5s in milliseconds
    protected static GoogleApiClient mGoogleApiClient;
    protected static LocationRequest mLocRequest;
    public static Location mCurLocation;

    public static void setmCurLocation(Location mCurLocation) {
        LocationUtility.mCurLocation = mCurLocation;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //TODO check permissions testing from MainActivity onRequestPermissionsResult
        // If we're running on API 23 or above, we need to ask permission at runtime
  /*      int permCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            Location locData = getLocation();
            setLocationFields(locData);
        }
*/
        //TODO check if null
        //    Location locData = getLocation();
        //    Log.e("location",String.valueOf(locData.getLatitude()));

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mGoogleApiClient.connect();
    }

    public void connectToGooglePlay(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void createLocationRequest() {
        mLocRequest = LocationRequest.create();
        mLocRequest.setInterval(LOC_UPDATE_INTERVAL);
        mLocRequest.setFastestInterval(LOC_FASTEST_UPDATE);
        mLocRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }

    public void connectGoogleApiClient() {
        mGoogleApiClient.connect();
    }

    public void disconnectApiClient() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public Location getLocation() {
        try {
            Location loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            return loc;
        } catch (SecurityException e) {
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
        mCurLocation = location;
        }else {
            Log.e("onLocationChanged","wait location update is not ready");
        }

        Log.e("location change", String.valueOf(location.getLatitude()));

    }

    public void startLocationUpdates(Context context) throws NullPointerException{
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates
                (mGoogleApiClient, mLocRequest, this);

        //todo check if null and if google play services is connected-> request activate GPS
        if(mCurLocation==null) throw new NullPointerException("Invalid Location, please add animal location");
        mCurLocation=getLocation();




        // give the location list a slight green tint so we know we're listening
    }

    public void stopLocationUpdates() {
        // TODO: stop the updates
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);


    }

    public void checkIfGPSisEnabled(final Activity activity) {

        //Check if GPS is enabled
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
//                final LocationSettingsStates state = result.getLocationSettingsStates();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:


                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(activity
                                    , 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });







    }

    public void requestAccessFineLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

    }
}
