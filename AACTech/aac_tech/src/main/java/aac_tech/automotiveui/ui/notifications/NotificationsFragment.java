/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui.ui.notifications;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;

import aac_tech.automotiveui.DisplayClientData;
import aac_tech.automotiveui.R;
import aac_tech.automotiveui.UpdateInfo;
import aac_tech.automotiveui.optionsNavigation;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class NotificationsFragment extends Fragment{
    private TextView status, temp, oxygen, heart_r, home_addr;
    private DatabaseReference database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_paramed, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        status = (TextView)view.findViewById(R.id.patient_stat);
        temp = (TextView)view.findViewById(R.id.temperat);
        oxygen =(TextView)view.findViewById(R.id.oxygen);
        heart_r = (TextView)view.findViewById(R.id.heart);
        home_addr = (TextView)view.findViewById(R.id.client_home_address);


        database = FirebaseDatabase.getInstance().getReference().child("clients");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                long time = 0;
                String street = null;
                String city = null;
                String zip = null;
                String province = null;
                String contact = null;
                String em_status = null;
                String display_home_address = null;
                String mytemp = null;
                String spo2 = null;
                String hrate = null;
                String client_name = null;


                for(DataSnapshot clientData: dataSnapshot.getChildren()){

                    if(time < Long.parseLong(clientData.child("time").getValue().toString())){

                        time = Long.parseLong(clientData.child("time").getValue().toString());

                        street = new String();
                        city = new String();
                        zip = new String();
                        province = new String();
                        contact = new String();
                        em_status = new String();
                        display_home_address = new String();
                        mytemp = new String();
                        spo2 = new String();
                        hrate = new String();
                        client_name = new String();


                        street = clientData.child("cl_street").getValue().toString();
                        city = clientData.child("cl_city").getValue().toString();
                        zip = clientData.child("cl_zip").getValue().toString();
                        province = clientData.child("cl_province").getValue().toString();
                        contact = clientData.child("contact").getValue().toString();
                        em_status = clientData.child("em_status").getValue().toString();
                        mytemp = clientData.child("temp").getValue().toString();
                        hrate = clientData.child("hrate").getValue().toString();
                        spo2 = clientData.child("spo2").getValue().toString();
                        client_name = clientData.child("cl_name").getValue().toString();






                    }
                }

                display_home_address = "Client name :  "+client_name+
                        "\nContact     :  "+contact+
                        "\nEMG Status  :  "+em_status+
                        "\n\nStreet      :  "+street+
                        "\nCity        :  "+city+
                        "\nZIP         :  "+zip+
                        "\nprovince    :  "+province;

                home_addr.setText(display_home_address);
                oxygen.setText(spo2);
                temp.setText(mytemp);
                heart_r.setText(hrate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}