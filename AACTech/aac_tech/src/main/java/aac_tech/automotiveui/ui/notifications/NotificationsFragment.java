/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui.ui.notifications;

import android.Manifest;
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
import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.ConnectorPkg;

import java.lang.reflect.Array;
import java.util.ArrayList;

import aac_tech.automotiveui.DisplayClientData;
import aac_tech.automotiveui.R;
import aac_tech.automotiveui.optionsNavigation;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private DatabaseReference database;
    private ArrayList client_names, client_status;
    private ListView client_list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.activity_paramed, container, false);



        return root;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        client_list = (ListView)view.findViewById(R.id.list_client);


        client_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Intent intent = new Intent(getActivity(), DisplayClientData.class);
                 intent.putExtra("client",client_names.get(i).toString());
                 startActivity(intent);
            }
        });

                database = FirebaseDatabase.getInstance().getReference().child("clients");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                client_names = new ArrayList();
                client_status = new ArrayList();

                for(DataSnapshot clientData: dataSnapshot.getChildren()){
                    client_names.add(clientData.child("cl_name").getValue().toString());
                    client_status.add(clientData.child("em_status").getValue().toString());
                }

                if(client_names.size() > 0 && client_status.size() > 0){
                    CustomAdapter customAdapter = new CustomAdapter();
                    client_list.setAdapter(customAdapter);

                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return client_names.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_list,null);

            TextView cl_name = (TextView)view.findViewById(R.id.client_name);
            TextView cl_status = (TextView)view.findViewById(R.id.em_status);

            cl_name.setText(client_names.get(i).toString());
            cl_status.setText(client_status.get(i).toString());

            if(client_status.get(i).toString().equals("done")) {
                cl_status.setTextColor(Color.GREEN);
            }
            else cl_status.setTextColor(Color.RED);


            return view;
        }
    }



}