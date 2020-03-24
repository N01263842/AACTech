/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui.ui.dashboard;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import aac_tech.automotiveui.R;
import aac_tech.automotiveui.UpdateInfo;
import aac_tech.automotiveui.optionsNavigation;
import aac_tech.automotiveui.paramedLogin;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private DrawerLayout mDrawerLayout;
    private GoogleMap googleMap;
    private ArrayList info;
    private Resources res;
    private MapView mMapView;
    private ArrayAdapter ad_spin;
    private String [] hosp_list;
    private String [] hosp1, hosp2, hosp3, hosp4, hosp5;
    private DatabaseReference database;
    private boolean skip;
    private UpdateInfo updateHosp;
    private Intent intent;

    private SupportMapFragment mapFragment;
    private Spinner hospital;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.paramed_maps, container, false);


       /* Intent intent = getActivity().getIntent();

        info = intent.getStringArrayListExtra("info");
        res = getResources();


        mMapView= (MapView) root.findViewById(R.id.mapView2);
        hospital = (Spinner)root.findViewById(R.id.hosp_spin);*/

     //   mMapView.onCreate(savedInstanceState);

      //  mMapView.onResume();

     //   setupSpinner();







        return root;
    }

    public void setUpMaps(final int offset){

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                float lat = 0;
                float lng = 0;
                String title = new String();

                if(offset == Integer.parseInt(hosp1[0])){
                    lat = Float.parseFloat(hosp1[1]);
                    lng = Float.parseFloat(hosp1[2]);
                    title = hosp1[3];

                }
                else if(offset == Integer.parseInt(hosp2[0])){
                    lat = Float.parseFloat(hosp2[1]);
                    lng = Float.parseFloat(hosp2[2]);
                    title = hosp2[3];
                }
                else if(offset== Integer.parseInt(hosp3[0])){
                    lat = Float.parseFloat(hosp3[1]);
                    lng = Float.parseFloat(hosp3[2]);
                    title = hosp3[3];
                }
                else if(offset == Integer.parseInt(hosp4[0])){
                    lat = Float.parseFloat(hosp4[1]);
                    lng = Float.parseFloat(hosp4[2]);
                    title = hosp4[3];
                }
                else if(offset == Integer.parseInt(hosp5[0])){
                    lat = Float.parseFloat(hosp5[1]);
                    lng = Float.parseFloat(hosp5[2]);
                    title = hosp5[3];
                }


                if (checkLocationPermission()) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
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
                LatLng hospital2 = (new LatLng(lat,lng));
                googleMap.addMarker(new MarkerOptions().position(hospital2).
                        title("hospital").snippet(title));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(hospital2).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition
                        (cameraPosition ));



            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(intent == null) intent = getActivity().getIntent();

        if(info == null) info = intent.getStringArrayListExtra("info");
        res = getResources();
        skip = true;



        mMapView= (MapView) view.findViewById(R.id.mapView2);
        hospital = (Spinner)view.findViewById(R.id.hosp_spin);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        setupSpinner();

        database = FirebaseDatabase.getInstance().getReference().child("paramedics");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(intent == null) intent = getActivity().getIntent();
                if(info == null) info = intent.getStringArrayListExtra("info");

                for(DataSnapshot paramed: dataSnapshot.getChildren()){
                    if(paramed.child("username").getValue().toString().equals(info.get(1))){
                        int hospID = Integer.parseInt(paramed.child("hospitalID").getValue().toString()) - 1;


                        setSpinner(hospID);
                        setUpMaps(hospID+1);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        hospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String validate = new String();
                updateHosp = new UpdateInfo();
                int hospID = i + 1;
                updateHosp.setHospID(hospID);

                validate =getResources().getString(R.string.validate);
                if(skip == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(validate);
                    builder.setCancelable(true);
                    builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            updateHosp.updateHospital(info.get(5).toString());

                            dialogInterface.cancel();
                        }
                    });

                   builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.cancel();
                       }
                   });




                AlertDialog alert = builder.create();
                alert.show();

                }
                else
                    skip = false;
            }




            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSpinner(int offset){

        hospital.setSelection(offset);



    }

    private void setupSpinner(){
        //Get Hospital Information
        hosp1 = res.getStringArray(R.array.hosp1);
        hosp2 = res.getStringArray(R.array.hosp2);
        hosp3 = res.getStringArray(R.array.hosp3);
        hosp4 = res.getStringArray(R.array.hosp4);
        hosp5 = res.getStringArray(R.array.hosp5);

        hosp_list = new String[5];

        //Add hospital names to array
        hosp_list[0] = hosp1[3];
        hosp_list[1] = hosp2[3];
        hosp_list[2] = hosp3[3];
        hosp_list[3] = hosp4[3];
        hosp_list[4] = hosp5[3];

        //Set up spinner with hospital names
        ad_spin = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,hosp_list);
        hospital.setAdapter(ad_spin);
    }




    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("")
                        .setMessage("")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),new String[]
                                        {Manifest.permission.ACCESS_FINE_LOCATION},1);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
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
                    if (ContextCompat.checkSelfPermission(getActivity(),
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