package com.example.cettorre.animalsshelter.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;



public class LocationUtility implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected GoogleApiClient mGoogleApiClient;

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
        Location locData = getLocation();
        Log.e("location",String.valueOf(locData.getLatitude()));

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mGoogleApiClient.connect();
    }

    public void connectToGooglePlay(Context context){
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void connectGoogleApiClient(){
        mGoogleApiClient.connect();
    }

    public void disconnectApiClient(){
        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    public Location getLocation() {
        try {
            Location loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            return loc;
        }
        catch (SecurityException e) {
            return null;
        }
    }
}
