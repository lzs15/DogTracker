package com.example.c65317.dogtracker;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onSearch(View v) {
        mMap.clear();
        EditText location_TF = (EditText) findViewById(R.id.TFaddress);
        String location = location_TF.getText().toString();
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                //integer in the method identifies the number of relevant addresses
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //returns first result and places marker
            android.location.Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Oklahoma State University"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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
        LatLng osu = new LatLng(36.123875, -97.071912);
        mMap.addMarker(new MarkerOptions().position(osu).title("Marker in Oklahoma State University"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(osu));

        //enable reading user's location
        mMap.setMyLocationEnabled(true);
        //copy and paste mechanically; do not change anything
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location myLoc = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        double latitude = myLoc.getLatitude();
        double longitude = myLoc.getLongitude();
        //capture the address associated with the user location
        Geocoder geocoder = new Geocoder(this);
        List<android.location.Address> addressList = null;
        try {
            //integer in the method identifies the number of relevant addresses
            addressList = geocoder.getFromLocation(latitude, longitude, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        android.location.Address address = addressList.get(0);
        String a = address.getAddressLine(0);
        String b = address.getAddressLine(1);
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title(a + ", " + b));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }
}