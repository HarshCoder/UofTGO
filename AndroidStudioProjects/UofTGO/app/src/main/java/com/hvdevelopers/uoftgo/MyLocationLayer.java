package com.hvdevelopers.uoftgo;

/**
 * Created by Harsh on 2017-01-21.
 */

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyLocationLayer implements LocationListener {
    private GoogleMap map;

    private BitmapDescriptor markerDescriptor;
    private boolean drawAccuracy = true;
    private int accuracyStrokeColor = Color.argb(255, 130, 182, 228);
    private int accuracyFillColor = Color.argb(100, 130, 182, 228);

    private Marker positionMarker;
    private Circle accuracyCircle;

    private Location location;

    public MyLocationLayer(GoogleMap map, Location location) {
        this.map = map;
        this.location = location;
        markerDescriptor = BitmapDescriptorFactory.fromResource(R.color.colorPrimary);
    }

    public void setDrawAccuracy(boolean drawAccuracy) {
        this.drawAccuracy = drawAccuracy;
    }

    public void setAccuracyStrokeColor(int color) {
        this.accuracyStrokeColor = color;
    }

    public void setAccuracyFillColor(int color) {
        this.accuracyFillColor = color;
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        float accuracy = location.getAccuracy();

        if (positionMarker != null) {
            positionMarker.remove();
        }
        final MarkerOptions positionMarkerOptions = new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .icon(markerDescriptor)
                .anchor(0.5f, 0.5f);
        positionMarker = map.addMarker(positionMarkerOptions);

        if (accuracyCircle != null) {
            accuracyCircle.remove();
        }
        if (drawAccuracy) {
            final CircleOptions accuracyCircleOptions = new CircleOptions()
                    .center(new LatLng(latitude, longitude))
                    .radius(accuracy)
                    .fillColor(accuracyFillColor)
                    .strokeColor(accuracyStrokeColor)
                    .strokeWidth(2.0f);
            accuracyCircle = map.addCircle(accuracyCircleOptions);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}