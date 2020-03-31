/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParamedMaps extends FragmentActivity implements OnMapReadyCallback, LocationListener,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastLocation;
    private long lat, lng;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private LocationCallback mLocationCallback;
    private ArrayList<String> data;
    private DatabaseReference cl_database, database;
    private ValueEventListener myValueEvent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramed_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Intent intent = getIntent();
        data = intent.getStringArrayListExtra("info2");
        cl_database = FirebaseDatabase.getInstance().getReference().child("clients");
        database = FirebaseDatabase.getInstance().getReference().child("paramedics");

         try{
             mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
             mFusedLocationClient.getLastLocation().addOnSuccessListener(ParamedMaps.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        lastLocation = location;
                    }
                }
            });

            locationRequest = LocationRequest.create();
            locationRequest.setInterval(5000);
            locationRequest.setFastestInterval(1000);

            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                        setLastLocation(location);
                    }
                }
            };
         } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        cl_database.addValueEventListener(myValueEvent = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                //Request location updates:
                googleMap.setMyLocationEnabled(true);
            }
        }

        /*Resources res = getResources();
        //Containers to store hospital location for display on maps
        String [] hosp1 = res.getStringArray(R.array.hosp1);
        String [] hosp2 = res.getStringArray(R.array.hosp2);
        String [] hosp3 = res.getStringArray(R.array.hosp3);
        String [] hosp4 = res.getStringArray(R.array.hosp4);
        String [] hosp5 = res.getStringArray(R.array.hosp5);*/

        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);





        //buildGoogleApiClient();

        // Add a marker in Sydney and move the camera
       /* LatLng hospital1 = (new LatLng(Float.parseFloat(hosp1[1]), Float.parseFloat(hosp1[2])));
        mMap.addMarker(new MarkerOptions().position(hospital1).title(hosp1[3]));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hospital1));

        LatLng hospital2 = (new LatLng(Float.parseFloat(hosp2[1]), Float.parseFloat(hosp2[2])));
        mMap.addMarker(new MarkerOptions().position(hospital2).title(hosp2[3]));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hospital2));

        LatLng hospital3 = (new LatLng(Float.parseFloat(hosp3[1]), Float.parseFloat(hosp3[2])));
        mMap.addMarker(new MarkerOptions().position(hospital3).title(hosp3[3]));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(hospital3));

        LatLng hospital4 = (new LatLng(Float.parseFloat(hosp4[1]), Float.parseFloat(hosp4[2])));
        mMap.addMarker(new MarkerOptions().position(hospital4).title(hosp4[3]));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hospital4));

        LatLng hospital5 = (new LatLng(Float.parseFloat(hosp5[1]), Float.parseFloat(hosp5[2])));
        mMap.addMarker(new MarkerOptions().position(hospital5).title(hosp5[3]));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hospital5));

        */



      /* if(mLastLocation != null) {
           LatLng myLoc = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc,10.0f));
       }*/
      if(mLastLocation != null)  setLastLocation(mLastLocation);
    }

    private void setLastLocation( Location lastLocation){

        LatLng myLoc = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc,10.0f));

        database.child(data.get(5)).child("lat").setValue(lastLocation.getLatitude());
        database.child(data.get(5)).child("long").setValue(lastLocation.getLongitude());
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(ParamedMaps.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(ParamedMaps.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(ParamedMaps.this)
                        .setTitle("")
                        .setMessage("")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ParamedMaps.this,new String[]
                                        {Manifest.permission.ACCESS_FINE_LOCATION},1);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(ParamedMaps.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
            return false;
        } else {
            return true;
        }
    }

   /* private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                        mMap.setMyLocationEnabled(true);
                    }

                } else {



                }
                return;
            }

        }
    }
    //getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

    @Override
    public void onLocationChanged(Location location) {

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


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


       /* if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();

            LatLng loc = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(loc).title("New Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }*/
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
