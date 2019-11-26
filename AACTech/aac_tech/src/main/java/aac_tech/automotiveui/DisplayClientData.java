/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DisplayClientData extends AppCompatActivity {

    private DatabaseReference database;
    private TextView emerg_addr;
    private TextView home_addr;
    private Intent intent;
    private GoogleMap googleMap;
    private MapView minMapView;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_client_data);

        database = FirebaseDatabase.getInstance().getReference().child("clients");
        emerg_addr = (TextView)findViewById(R.id.emerg_address);
        home_addr = (TextView)findViewById(R.id.client_home_address);
        minMapView = (MapView)findViewById(R.id.min_map2);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                intent = getIntent();
                String client_name = intent.getStringExtra("client");
                System.out.println("Client name: "+client_name);

                String street = new String();
                String city = new String();
                String zip = new String();
                String province = new String();
                String contact = new String();
                String em_status = new String();
                String display_home_address = new String();

                for(DataSnapshot clientData: dataSnapshot.getChildren()){
                    System.out.println("database name: "+clientData.child("cl_name").getValue().toString() );
                    System.out.println("Intent name: "+client_name);
                    System.out.println("Size of database name"+clientData.child("cl_name").getValue().toString().length());
                    System.out.println("Size of Intent name: "+client_name.length());

                    if(client_name.equals(clientData.child("cl_name").getValue().toString())){

                        float loc_lat = Float.parseFloat(clientData.child("loc_lat").getValue().toString());
                        float loc_long = Float.parseFloat(clientData.child("loc_long").getValue().toString());
                        System.out.println("loc_lat: "+loc_lat+"loc_long"+loc_long);
                        street = clientData.child("cl_street").getValue().toString();
                        city = clientData.child("cl_city").getValue().toString();
                        zip = clientData.child("cl_zip").getValue().toString();
                        province = clientData.child("cl_province").getValue().toString();
                        contact = clientData.child("contact").getValue().toString();
                        em_status = clientData.child("em_status").getValue().toString();

                        startMap(savedInstanceState,loc_lat,loc_long);
                        getAddressFromLocation(loc_lat,loc_long);

                        display_home_address = "Client name :  "+client_name+
                                "\nContact     :  "+contact+
                                "\nEMG Status  :  "+em_status+
                                "\n\nStreet      :  "+street+
                                "\nCity        :  "+city+
                                "\nZIP         :  "+zip+
                                "\nprovince    :  "+province;

                        home_addr.setText(display_home_address);

                        break;
                    }



                    System.out.println("Home address: "+display_home_address);
                }

                System.out.println("Does this even execute");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.CANADA);


        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();

                strAddress.append(fetchedAddress.getFeatureName());
                strAddress.append("\n"+fetchedAddress.getAdminArea());
                strAddress.append("\n"+fetchedAddress.getCountryName());
                strAddress.append("\n"+fetchedAddress.getCountryCode());
                strAddress.append("\n"+fetchedAddress.getPostalCode());
                strAddress.append("\n"+fetchedAddress.getPremises());
                strAddress.append("\n"+fetchedAddress.getSubAdminArea());
                strAddress.append("\n"+fetchedAddress.getSubLocality());

                emerg_addr.setText(strAddress);




            } else {
               emerg_addr.setText("Currently searching for address info");
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Could not get address..!",Toast.LENGTH_LONG);
        }
    }

    public void startMap(Bundle bundle,final float lat,final float lng){
        minMapView.onCreate(bundle);




        minMapView.onResume();

        try {
            MapsInitializer.initialize(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        minMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                String addressInfo = new String();
                String hosp_label = new String();
                String street_label = new String();
                String zip_label = new String();
                String city_label = new String();
                String province_label = new String();

                String title = new String();



                if (checkLocationPermission()) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        googleMap.setMyLocationEnabled(true);
                    }
                }

                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);
                // For dropping a marker at a point on the Map
                LatLng hospital = (new LatLng(lat,lng));
                googleMap.addMarker(new MarkerOptions().position(hospital).
                        title("Emergency Marker").snippet(title));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(hospital).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition
                        (cameraPosition ));



            }
        });


    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getParent(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("")
                        .setMessage("")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getParent(),new String[]
                                        {Manifest.permission.ACCESS_FINE_LOCATION},1);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
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


                        googleMap.setMyLocationEnabled(true);
                    }

                } else {



                }
                return;
            }

        }
    }
}
