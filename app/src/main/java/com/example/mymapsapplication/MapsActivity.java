package com.example.mymapsapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean mapMode;
    private  LocationManager locationManager;
    private boolean isGPSEnabled = false;
    private  boolean isNetworkEnabled = false;
    private static final long MIN_TIME_BW_UPDATES = 1000*5;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void onClick(View v)
    {
        if(mapMode)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            mapMode = !mapMode;
        }
        else
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mapMode = !mapMode;
        }
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

        // Add a marker in Sydney and move the camera
        LatLng Delhi = new LatLng(29, 77);
        mMap.addMarker(new MarkerOptions().position(Delhi).title("Birth Place"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Delhi));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    public void getLocation()
    {
        try{
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            //get GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(isGPSEnabled) Log.d("MyMaps", "getLocation: GPS is enabled");

            //get network status (cell tower + wifi) - look for network provider in LocationManager
            //add code here to update var isNetworkEnabled and output Log.d

            if(!isGPSEnabled && !isNetworkEnabled)   //no provider is enabled
                Log.d("MyMaps", "getLocattion: no provider is enabled");
            else{
                //add Log.d here
                if (isNetworkEnabled)
                {

                    Log.d("MyMaps", "getLocattion: network provider is enabled");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListenerNetwork);
                }
                if(isGPSEnabled)
                {
                    Log.d("MyMaps", "getLocattion: GPS provider is enabled");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListenerGPS);
                }

            }
        }
        catch (Exception e)
        {
            //log
            e.printStackTrace();
        }
    }


}
