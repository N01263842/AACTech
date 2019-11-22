/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

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

        Resources res = getResources();
        //Containers to store hospital location for display on maps
        String [] hosp1 = res.getStringArray(R.array.hosp1);
        String [] hosp2 = res.getStringArray(R.array.hosp2);
        String [] hosp3 = res.getStringArray(R.array.hosp3);
        String [] hosp4 = res.getStringArray(R.array.hosp4);
        String [] hosp5 = res.getStringArray(R.array.hosp5);

        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng hospital1 = (new LatLng(Float.parseFloat(hosp1[1]), Float.parseFloat(hosp1[2])));
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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hospital1,10.0f));
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
    //getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();


}
