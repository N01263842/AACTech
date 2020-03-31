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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Location mlastLocation;
    private long lat, lng;
    private Location lastLocation;
    private ArrayList<String> data;
    private DatabaseReference cl_database, database;
    private ValueEventListener myValueEvent, myEvent;
    private String clientkey;
    private Client_Address_DataBase cad;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        Intent intent = getIntent();
        data = intent.getStringArrayListExtra("para");
        cl_database = FirebaseDatabase.getInstance().getReference().child("clients");
        database = FirebaseDatabase.getInstance().getReference().child("paramedics");

        startClientRecord();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        cl_database.addValueEventListener(myEvent = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot parData: dataSnapshot.getChildren()){
                    if(parData.child("para_id").getValue().toString().equals(data.get(0))
                            && parData.child("em_status").getValue().toString().equals("dispatched")) {

                       database.removeEventListener(myValueEvent);
                       closingClient();

                        break;
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        database.addValueEventListener(myValueEvent = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot parData: dataSnapshot.getChildren()){
                    Toast.makeText(getApplicationContext(),"This is working",Toast.LENGTH_LONG).show();
                    if(parData.child("username").getValue().toString().equals(data.get(0))
                            && parData.child("status").getValue().toString().equals("busy")
                            && parData.child("video").getValue().toString().equals("no")) {
                        if(parData.child("loc_lat").getValue() != null &&
                            parData.child("loc_long").getValue() != null){
                            double lat = Double.parseDouble(parData.child("loc_lat").getValue().toString());
                            double lng = Double.parseDouble(parData.child("loc_long").getValue().toString());

                            LatLng parLocation = (new LatLng(lat,lng));
                            mMap.addMarker(new MarkerOptions().position(parLocation).title("Paramedic's Current Location"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(parLocation));
                        }

                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void closingClient(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setMessage("Paramedic has arrived at your location.\nNow closing the session");
        builder.setTitle("NOTICE");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

                cl_database.removeEventListener(myEvent);

                android.os.Process.killProcess(android.os.Process.myPid());

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

        cl_database.child(clientkey).child("loc_lat").setValue(lastLocation.getLatitude());
        cl_database.child(clientkey).child("loc_long").setValue(lastLocation.getLongitude());
    }

    private void startClientRecord(){

        clientkey = cl_database.push().getKey();

        cad = new Client_Address_DataBase();

        cad.setCl_city("pending");
        cad.setCl_province("pending");
        cad.setCl_street("pending");
        cad.setContact("pending");
        cad.setCl_zip("pending");
        cad.setPara_id(data.get(0));
        cad.setTime();
        cad.setEm_status("active");
        cad.setHrate("pending");
        cad.setSpo2("pending");
        cad.setTemp("pending");
        cad.setLoc_lat(0);
        cad.setLoc_long(0);
        cad.setCl_name("pending");

        cl_database.child(clientkey).setValue(cad);

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(MapsActivity.this)
                        .setTitle("")
                        .setMessage("")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,new String[]
                                        {Manifest.permission.ACCESS_FINE_LOCATION},1);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MapsActivity.this,
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


    @Override
    public void onLocationChanged(Location location) {
        setLastLocation(location);
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
    //getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();


}
