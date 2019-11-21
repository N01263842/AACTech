/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import androidx.fragment.app.FragmentActivity;

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

        Resources res = getResources();
        //Containers to store hospital location for display on maps
        String [] hosp1 = res.getStringArray(R.array.hosp1);
        String [] hosp2 = res.getStringArray(R.array.hosp2);
        String [] hosp3 = res.getStringArray(R.array.hosp3);
        String [] hosp4 = res.getStringArray(R.array.hosp4);
        String [] hosp5 = res.getStringArray(R.array.hosp5);

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
    //getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();


}
