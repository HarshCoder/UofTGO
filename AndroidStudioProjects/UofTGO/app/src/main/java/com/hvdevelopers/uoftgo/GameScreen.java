package com.hvdevelopers.uoftgo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static android.icu.lang.UProperty.MATH;

public class GameScreen extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 124;
    private Location myLocation;

    private double markerLatitude;
    private double markerLongitude;
    private boolean intersection;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        thread = new Thread();
        thread.start();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION );
        List<String> permissions = new ArrayList<String>();
        if( hasLocationPermission != PackageManager.PERMISSION_GRANTED ) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION );
        }

        if( !permissions.isEmpty() ) {
            requestPermissions( permissions.toArray( new String[permissions.size()] ), REQUEST_CODE_SOME_FEATURES_PERMISSIONS );
        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }catch(Exception e) {
            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                String provider = lm.getBestProvider(criteria, true);
                myLocation = lm.getLastKnownLocation(provider);
            }
        }

        LatLng myPos = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        /*if(mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos, 16.0f));
        }
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(0, 0);
        mMap.addMarker(new MarkerOptions().position(myPos).title("Current Location"));*/
        mMap.setMyLocationEnabled(true);
        if(mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos, 16.0f));
        }

        ArrayList<String> buildings = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getAssets().open("buildings.txt")));

            String mLine = reader.readLine();
            while (mLine != null) {
                mLine = reader.readLine();
                buildings.add(mLine);
            }
            reader.close();
        }catch(Exception e){
            Log.e("file", "not found");
        }

        String building = buildings.toString();

        String [] location = building.split(", ");

        for (int i = 0; i <location.length-1; i++){

            String [] loca = location[i].split(",");

            for(int j = 0; j<3; j++){
                String name = loca[0];
                markerLatitude = Double.parseDouble(loca[1]);
                markerLongitude = Double.parseDouble(loca[2]);

                mMap.addMarker(new MarkerOptions().position(new LatLng(markerLatitude, markerLongitude)).title(name));
                mMap.addCircle(new CircleOptions().center(new LatLng(markerLatitude, markerLongitude))
                        .radius(45)
                        .fillColor(R.color.red).strokeColor(R.color.red).strokeWidth(0.1f));
            }
        }

        double intersectPt = Math.pow(myLocation.getLatitude()-markerLatitude, 2) + Math.pow(myLocation.getLongitude() - markerLongitude,2);
        double leftlimit = 0;
        double rightlimit = (25+45);

        if(leftlimit<intersectPt && intersectPt<rightlimit){
            intersection = true;
            Log.e("intersect", "is happening");
        }
    }
}
