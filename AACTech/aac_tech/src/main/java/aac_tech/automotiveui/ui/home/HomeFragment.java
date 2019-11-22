/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui.ui.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

import org.xmlpull.v1.XmlPullParser;

import aac_tech.automotiveui.R;
import aac_tech.automotiveui.optionsNavigation;
import aac_tech.automotiveui.paramedInfo;
import aac_tech.automotiveui.paramedLogin;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList info;
    ArrayAdapter ad;
    private Resources res;
    private GoogleMap googleMap;
    private MapView minMapView;
    private TextView AddressInfo;
    private ToggleButton tb_state;
    private ListView para_list;
    String [] hosp_list;
    private DatabaseReference database;
    paramedInfo parainfo;
    Spinner add_spin;
    private String [] hosp1, hosp2, hosp3, hosp4, hosp5;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.paramed_home, container, false);
        minMapView = (MapView) root.findViewById(R.id.min_map);
        AddressInfo = (TextView)root.findViewById(R.id.home_addres);
        tb_state = (ToggleButton)root.findViewById(R.id.tog_active);
        para_list = (ListView)root.findViewById(R.id.para_list);
        add_spin = (Spinner)root.findViewById(R.id.hosp_spin);
        res = getResources();

        hosp1 = res.getStringArray(R.array.hosp1);
        hosp2 = res.getStringArray(R.array.hosp2);
        hosp3 = res.getStringArray(R.array.hosp3);
        hosp4 = res.getStringArray(R.array.hosp4);
        hosp5 = res.getStringArray(R.array.hosp5);



        hosp_list = new String[5];

        hosp_list[0] = hosp1[3];
        hosp_list[1] = hosp2[3];
        hosp_list[2] = hosp3[3];
        hosp_list[3] = hosp4[3];
        hosp_list[4] = hosp5[3];

        ad = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,hosp_list);
        add_spin.setAdapter(ad);
        para_list.setAdapter(ad);







        tb_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(tb_state.isChecked()) {
                    tb_state.setBackgroundColor(Color.GREEN);
                    parainfo = new paramedInfo();
                    parainfo.setStatus("active");
                    database = FirebaseDatabase.getInstance().getReference().child("paramedics");
                    database.child(info.get(5).toString()).child("status").setValue(parainfo.getStatus());

                }
                else {
                    tb_state.setBackgroundColor(Color.RED);
                    parainfo = new paramedInfo();
                    parainfo.setStatus("active");
                    database = FirebaseDatabase.getInstance().getReference().child("paramedics");
                    database.child(info.get(5).toString()).child("status").setValue(parainfo.getStatus());
                }
            }
        });

        minMapView.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();

        info = intent.getStringArrayListExtra("info");

        minMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        minMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                float lat = 0;
                float lng = 0;
                String addressInfo = new String();
                String hosp_label = new String();
                String street_label = new String();
                String zip_label = new String();
                String city_label = new String();
                String province_label = new String();

                String title = new String();



                hosp_label = res.getString(R.string.hospital);
                street_label = res.getString(R.string.street);
                zip_label = res.getString(R.string.ZIP);
                city_label = res.getString(R.string.City);
                province_label = res.getString(R.string.province);

                if(Integer.parseInt(String.valueOf(info.get(2))) == Integer.parseInt(hosp1[0])){
                    lat = Float.parseFloat(hosp1[1]);
                    lng = Float.parseFloat(hosp1[2]);
                    title = hosp1[3];

                    addressInfo = hosp_label+" "+title+"\n"+
                                  street_label+" "+hosp1[4]+"\n"+
                                  city_label+" "+hosp1[5]+"\n"+
                                  province_label+" "+hosp1[6]+"\n"+
                                  zip_label+" "+hosp1[7];

                    AddressInfo.setText(addressInfo);
                }
                else if(Integer.parseInt(String.valueOf(info.get(2))) == Integer.parseInt(hosp2[0])){
                    lat = Float.parseFloat(hosp2[1]);
                    lng = Float.parseFloat(hosp2[2]);
                    title = hosp2[3];

                    addressInfo = hosp_label+" "+title+"\n"+
                            street_label+" "+hosp2[4]+"\n"+
                            city_label+" "+hosp2[5]+"\n"+
                            province_label+" "+hosp2[6]+"\n"+
                            zip_label+" "+hosp2[7];

                    AddressInfo.setText(addressInfo);
                }
                else if(Integer.parseInt(String.valueOf(info.get(2))) == Integer.parseInt(hosp3[0])){
                    lat = Float.parseFloat(hosp3[1]);
                    lng = Float.parseFloat(hosp3[2]);
                    title = hosp3[3];

                    addressInfo = hosp_label+" "+title+"\n"+
                            street_label+" "+hosp3[4]+"\n"+
                            city_label+" "+hosp3[5]+"\n"+
                            province_label+" "+hosp3[6]+"\n"+
                            zip_label+" "+hosp3[7];

                    AddressInfo.setText(addressInfo);
                }
                else if(Integer.parseInt(String.valueOf(info.get(2))) == Integer.parseInt(hosp4[0])){
                    lat = Float.parseFloat(hosp4[1]);
                    lng = Float.parseFloat(hosp4[2]);
                    title = hosp4[3];

                    addressInfo = hosp_label+" "+title+"\n"+
                            street_label+" "+hosp4[4]+"\n"+
                            city_label+" "+hosp4[5]+"\n"+
                            province_label+" "+hosp4[6]+"\n"+
                            zip_label+" "+hosp4[7];

                    AddressInfo.setText(addressInfo);
                }
                else if(Integer.parseInt(String.valueOf(info.get(2))) == Integer.parseInt(hosp2[0])){
                    lat = Float.parseFloat(hosp5[1]);
                    lng = Float.parseFloat(hosp5[2]);
                    title = hosp5[3];

                    addressInfo = hosp_label+" "+title+"\n"+
                            street_label+" "+hosp5[4]+"\n"+
                            city_label+" "+hosp5[5]+"\n"+
                            province_label+" "+hosp5[6]+"\n"+
                            zip_label+" "+hosp5[7];

                    AddressInfo.setText(addressInfo);
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

    private void getParamedicInfo(){

     //   paraInfo = new ArrayList();
    //    par = new paramedInfo();
     //   data_retrieved = 0;

        database = FirebaseDatabase.getInstance().getReference().child("paramedics");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //username = dataSnapshot.child()

                for(DataSnapshot paramedData : dataSnapshot.getChildren()){
                    String username = paramedData.child("username").getValue().toString();

                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}