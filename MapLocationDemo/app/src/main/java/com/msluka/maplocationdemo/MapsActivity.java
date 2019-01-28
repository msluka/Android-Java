package com.msluka.maplocationdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Location lastKnownLocation;

    Marker personalMarker;


    public void getAndSetLastKnownLocation(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(lastKnownLocation != null){
                LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                personalMarker = mMap.addMarker(new MarkerOptions().position(userLocation).title("This is last known location, waiting for update..."));
                personalMarker.showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));

            }else{

                LatLng manhattan = new LatLng(40.7673888,-73.9778467);
                mMap.addMarker(new MarkerOptions().position(manhattan).title("Manhattan").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(manhattan, 15));
            }


        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,0, locationListener);


        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);



        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

               // Toast.makeText(MapsActivity.this, location.toString(), Toast.LENGTH_SHORT).show();


               LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

               if(personalMarker != null){

                   personalMarker.remove();

               }

               mMap.clear();

               // mMap.addMarker(new MarkerOptions().position(userLocation).title("This is your current location"));
               personalMarker = mMap.addMarker(new MarkerOptions().position(userLocation).title("This is your current location"));
               personalMarker.showInfoWindow();

               //mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));

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



        };

        getAndSetLastKnownLocation();


        if(Build.VERSION.SDK_INT < 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,0, locationListener);

        }else{

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                // Ask for permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else{

                // We have permission
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,0, locationListener);
                getAndSetLastKnownLocation();
            }

        }



    }
}
