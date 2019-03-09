package com.msluka.myplaces;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GpsStatus.Listener {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;
    GpsStatus gpsStatus;

    Location currentLocation = null;
    ImageView redBtn;
    ImageView greenBtn;
    Location lastKnownLocation;

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
                lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

                locationManager.addGpsStatusListener(this);

            }

        }

    }


    // Our method
    public void centerMapOnLocation(Location location, String title) {

        if (location != null) {

            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

            mMap.clear();

            if (title != "You are here" || title != "Your last known location") {

                mMap.addMarker(new MarkerOptions().position(userLocation).title(title));

            }

            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 6));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10));

        }


    }

    public void goToPlaceList(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void whereAmI(View view) {

        if (currentLocation != null) {

            centerMapOnLocation(currentLocation, "You are here");

        } else {

            centerMapOnLocation(lastKnownLocation, "You last known location");

        }

    }

    public void closeApp(View view){

       // finish();

        //moveTaskToBack(true);

        Intent mainView = new Intent(getApplicationContext(), MainActivity.class);
        mainView.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        mainView.putExtra("EXIT", true);
        startActivity(mainView);
        finish();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        redBtn = findViewById(R.id.imageViewRed);
        greenBtn = findViewById(R.id.imageViewGreen);




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
        mMap.setOnMapLongClickListener(this);


        Intent intent = getIntent();

        if (intent.getIntExtra("placeNumber", 0) == 0) {

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    //centerMapOnLocation(location, "You are here");
                    currentLocation = location;
                    redBtn.setVisibility(View.INVISIBLE);
                    greenBtn.setVisibility(View.VISIBLE);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                    Toast.makeText(getApplicationContext(), "GPS status changed", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onProviderEnabled(String provider) {

                    Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onProviderDisabled(String provider) {

                    Toast.makeText(getApplicationContext(), "GPS disabled", Toast.LENGTH_LONG).show();

                }
            };


            if (Build.VERSION.SDK_INT < 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
                lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnownLocation, "Your last known location");

                locationManager.addGpsStatusListener(this);

            } else {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    // We have permission
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
                    lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                    centerMapOnLocation(lastKnownLocation, "Your last known location");

                    locationManager.addGpsStatusListener(this);

                } else {

                    // Ask for permission
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                }

            }

        } else {


            //Repeated code for other reason ... not good, it has to be changed

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    //centerMapOnLocation(location, "You are here");
                    currentLocation = location;
                    redBtn.setVisibility(View.INVISIBLE);
                    greenBtn.setVisibility(View.VISIBLE);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                    Toast.makeText(getApplicationContext(), "GPS status changed", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onProviderEnabled(String provider) {

                    Toast.makeText(getApplicationContext(), "GPS enabled", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onProviderDisabled(String provider) {

                    Toast.makeText(getApplicationContext(), "GPS disabled", Toast.LENGTH_LONG).show();

                }
            };

            if (Build.VERSION.SDK_INT < 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
                lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

                locationManager.addGpsStatusListener(this);

            } else {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    // We have permission
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
                    lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

                    locationManager.addGpsStatusListener(this);

                } else {

                    // Ask for permission
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                }

            }


            Location placeLocation = new Location(LocationManager.GPS_PROVIDER);
            placeLocation.setLatitude(MainActivity.locations.get(intent.getIntExtra("placeNumber", 0)).latitude);
            placeLocation.setLongitude(MainActivity.locations.get(intent.getIntExtra("placeNumber", 0)).longitude);

            centerMapOnLocation(placeLocation, MainActivity.places.get(intent.getIntExtra("placeNumber", 0)));


        }


        if (currentLocation == null) {
            redBtn.setVisibility(View.VISIBLE);
            greenBtn.setVisibility(View.INVISIBLE);

        } else {
            redBtn.setVisibility(View.INVISIBLE);
            greenBtn.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void onMapLongClick(LatLng latLng) {

        String address = "";

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {

            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (addressList != null && addressList.size() > 0) {

                //address = addressList.get(0).toString();

                if (addressList.get(0).getSubThoroughfare() != null) {
                    address += addressList.get(0).getSubThoroughfare() + " ";
                }
                if (addressList.get(0).getThoroughfare() != null) {
                    address += addressList.get(0).getThoroughfare() + ", ";
                }

                if (addressList.get(0).getLocality() != null) {
                    address += addressList.get(0).getLocality() + ", ";
                }

                if (addressList.get(0).getPostalCode() != null) {
                    address += addressList.get(0).getPostalCode() + ", ";
                }

                if (addressList.get(0).getCountryName() != null) {
                    address += addressList.get(0).getCountryName();
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (address == "") {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
            address = sdf.format(new Date());
        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(address));
        MainActivity.places.add(address);
        MainActivity.locations.add(latLng);
        MainActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.msluka.myplaces", Context.MODE_PRIVATE);

        try {

            ArrayList<String> latitudes = new ArrayList<>();
            ArrayList<String> longitudes = new ArrayList<>();

            for(LatLng coordinates : MainActivity.locations){

                latitudes.add(Double.toString(coordinates.latitude));
                longitudes.add(Double.toString(coordinates.longitude));
            }

            sharedPreferences.edit().putString("places", ObjectSerializer.serialize(MainActivity.places)).apply();
            sharedPreferences.edit().putString("latitudes", ObjectSerializer.serialize(latitudes)).apply();
            sharedPreferences.edit().putString("longitudes", ObjectSerializer.serialize(longitudes)).apply();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Your new place added successfully", Toast.LENGTH_LONG).show();


    }


    // Source: https://stackoverflow.com/questions/14222152/androids-onstatuschanged-not-working
    @Override
    public void onGpsStatusChanged(int event) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        gpsStatus = locationManager.getGpsStatus(gpsStatus);

        switch (event) {
            case GpsStatus.GPS_EVENT_STARTED:
                // Do Something with mStatus info

                Toast.makeText(this, "GPS started", Toast.LENGTH_LONG).show();

                break;

            case GpsStatus.GPS_EVENT_STOPPED:
                // Do Something with mStatus info

                Toast.makeText(this, "GPS stopped", Toast.LENGTH_LONG).show();

                break;

            case GpsStatus.GPS_EVENT_FIRST_FIX:
                // Do Something with mStatus info

                Toast.makeText(this, "GPS first fix", Toast.LENGTH_LONG).show();

                break;

            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                // Do Something with mStatus info


                break;
        }


    }
}
