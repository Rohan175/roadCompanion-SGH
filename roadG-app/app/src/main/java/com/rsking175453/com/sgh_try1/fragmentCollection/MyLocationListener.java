package com.rsking175453.com.sgh_try1.fragmentCollection;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.rsking175453.com.sgh_try1.R;

public class MyLocationListener implements LocationListener {
    private static final String TAG = "MyLocationListener";
    private Context c;
    public String lat,lon;
    public int acc;

    public MyLocationListener(Context c,String lat, String lon,int acc){
        this.c = c;
        Log.v(TAG,"data : " + lat + " " + lon + " " + acc);
        this.acc = acc;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public void onLocationChanged(Location loc) {
//        Toast.makeText(c, "Location changed: Lat: " + loc.getLatitude() + " Lng: "
//                + loc.getLongitude() + " Accuracy : " + loc.getAccuracy(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + loc.getLongitude();

        String latitude = "Latitude: " + loc.getLatitude();
        Log.v(TAG, longitude + " " + latitude + " " + loc.getAccuracy());
        if(loc.getAccuracy() < acc){
            lon = String.valueOf(loc.getLongitude());
            lat = String.valueOf(loc.getLatitude());
            EditText txtView = (EditText) ((Activity)c).findViewById(R.id.locationEdit);
            txtView.setText(lat +",\n" + lon);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.v(TAG,"onProviderDisabled");

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.v(TAG, "onProviderEnabled: ");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}