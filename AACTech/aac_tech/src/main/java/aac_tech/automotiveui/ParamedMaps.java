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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParamedMaps extends FragmentActivity implements  LocationListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private long lat, lng;
    private ArrayList<String> data;
    private DatabaseReference cl_database, database;
    private ValueEventListener myValueEvent;
    private String clientkey;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private MapView mMapView;
    private FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramed_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Intent intent = getIntent();
        data = intent.getStringArrayListExtra("info2");
        cl_database = FirebaseDatabase.getInstance().getReference().child("clients");
        database = FirebaseDatabase.getInstance().getReference().child("paramedics");

        fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayEndDialog();
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        displayFunctionMSG();

        cl_database.addValueEventListener(myValueEvent = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot parData: dataSnapshot.getChildren()){
                    if(parData.child("para_id").getValue().toString().equals(data.get(1))
                            && parData.child("em_status").getValue().toString().equals("active")) {
                        if(parData.child("loc_lat").getValue() != null &&
                                parData.child("loc_lat").getValue() != null){
                            double lat = Double.parseDouble(parData.child("loc_lat").getValue().toString());
                            double lng = Double.parseDouble(parData.child("loc_long").getValue().toString());

                            LatLng parLocation = (new LatLng(lat,lng));
                            mMap.addMarker(new MarkerOptions().position(parLocation).title("Client's Current Location"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(parLocation));
                        }

                        if(clientkey == null) clientkey = parData.getKey();

                        break;
                    }
                }
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

        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);

    }

    private void setLastLocation( Location lastLocation){

        LatLng myLoc = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc,10.0f));

        database.child(data.get(5)).child("loc_lat").setValue(lastLocation.getLatitude());
        database.child(data.get(5)).child("loc_long").setValue(lastLocation.getLongitude());
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
        setLastLocation(location);
       // locationManager.removeUpdates(this);
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

    private void displayFunctionMSG(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ParamedMaps.this);
        builder.setMessage("To end GPS tracking and return to your home screen, click the button at bottom right of the screen");

        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void displayEndDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ParamedMaps.this);
        builder.setMessage("you are ending GPS tracking of patient. You will now be redirected back to your home screen.\n Click 'OK' to continue");
        builder.setTitle("Returning Home");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
                Intent intent = new Intent(getApplicationContext(), optionsNavigation.class);
                intent.putStringArrayListExtra("info",data);

                cl_database.child(clientkey).child("em_status").setValue("dispatched");

                database.removeEventListener(myValueEvent);

                startActivity(intent);

            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.map_end){
           displayEndDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);

        return true;
    }
}
