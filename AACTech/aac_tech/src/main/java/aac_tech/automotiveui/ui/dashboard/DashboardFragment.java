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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import aac_tech.automotiveui.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private DrawerLayout mDrawerLayout;
    private GoogleMap googleMap;
    private ArrayList info;
    private Resources res;
    private MapView mMapView;


    private SupportMapFragment mapFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.map_content, container, false);


        Intent intent = getActivity().getIntent();

        info = intent.getStringArrayListExtra("info");
        res = getResources();


      //  dashboardViewModel =
          //      ViewModelProviders.of(this).get(DashboardViewModel.class);

        mMapView= (MapView) root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

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

                String [] hosp1 = res.getStringArray(R.array.hosp1);
                String [] hosp2 = res.getStringArray(R.array.hosp2);
                String [] hosp3 = res.getStringArray(R.array.hosp3);
                String [] hosp4 = res.getStringArray(R.array.hosp4);
                String [] hosp5 = res.getStringArray(R.array.hosp5);

                if(Integer.parseInt(String.valueOf(info.get(2))) == Integer.parseInt(hosp1[0])){
                     lat = Float.parseFloat(hosp1[1]);
                     lng = Float.parseFloat(hosp1[2]);
                     title = hosp1[3];

                }
                else if(Integer.parseInt(String.valueOf(info.get(2))) == Integer.parseInt(hosp2[0])){
                    lat = Float.parseFloat(hosp2[1]);
                    lng = Float.parseFloat(hosp2[2]);
                    title = hosp2[3];
                }
                else if(Integer.parseInt(String.valueOf(info.get(2))) == Integer.parseInt(hosp3[0])){
                    lat = Float.parseFloat(hosp3[1]);
                    lng = Float.parseFloat(hosp3[2]);
                    title = hosp3[3];
                }
                else if(Integer.parseInt(String.valueOf(info.get(2))) == Integer.parseInt(hosp4[0])){
                    lat = Float.parseFloat(hosp4[1]);
                    lng = Float.parseFloat(hosp4[2]);
                    title = hosp4[3];
                }
                else if(Integer.parseInt(String.valueOf(info.get(2))) == Integer.parseInt(hosp5[0])){
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


        return root;
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