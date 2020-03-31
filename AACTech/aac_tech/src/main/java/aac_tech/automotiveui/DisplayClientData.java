/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import android.view.MenuItem;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DisplayClientData extends AppCompatActivity {

    private TextView status, temp, oxygen, heart_r, home_addr;
    private DatabaseReference database;
    private Intent intent;
    private ArrayList<String> data;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramed);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        status = (TextView)findViewById(R.id.patient_stat);
        temp = (TextView)findViewById(R.id.temperat);
        oxygen =(TextView)findViewById(R.id.oxygen);
        heart_r = (TextView)findViewById(R.id.heart);
        home_addr = (TextView)findViewById(R.id.client_home_address);


        database = FirebaseDatabase.getInstance().getReference().child("clients");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                intent = getIntent();
                data  = intent.getStringArrayListExtra("client");
                String client_name = data.get(data.size() - 1);
                data.remove(data.size() - 1);

                String street = new String();
                String city = new String();
                String zip = new String();
                String province = new String();
                String contact = new String();
                String em_status = new String();
                String display_home_address = new String();
                String mytemp = new String();
                String spo2 = new String();
                String hrate = new String();


                for(DataSnapshot clientData: dataSnapshot.getChildren()){

                    if(client_name.equals(clientData.child("cl_name").getValue().toString())){

                        street = clientData.child("cl_street").getValue().toString();
                        city = clientData.child("cl_city").getValue().toString();
                        zip = clientData.child("cl_zip").getValue().toString();
                        province = clientData.child("cl_province").getValue().toString();
                        contact = clientData.child("contact").getValue().toString();
                        em_status = clientData.child("em_status").getValue().toString();
                        mytemp = clientData.child("temp").getValue().toString();
                        spo2 = clientData.child("spo2").getValue().toString();
                        hrate = clientData.child("hrate").getValue().toString();



                        display_home_address = "Client name :  "+client_name+
                                "\nContact       :  "+contact+
                                "\n\nStreet        :  "+street+
                                "\nCity           :  "+city+
                                "\nZIP            :  "+zip+
                                "\nprovince       :  "+province;

                        home_addr.setText(display_home_address);
                        oxygen.setText(spo2);
                        temp.setText(mytemp);
                        heart_r.setText(hrate);
                        status.setText(em_status);


                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(),optionsNavigation.class);
                intent.putStringArrayListExtra("info",data);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
