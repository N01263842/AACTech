/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui.ui.home;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import aac_tech.automotiveui.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList info;
    private Resources res;
    private TextView name;
    private TextView status;
    private TextView hospital;
    private TextView address;
    private Resources resource;
    ArrayList myinfo;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // homeViewModel =
       //         ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.paramed_home, container, false);

        name = root.findViewById(R.id.paramedic);
        status = root.findViewById(R.id.status);
        hospital = root.findViewById(R.id.hospital);
        address = root.findViewById(R.id.address);

        resource = getResources();

        Intent intent = getActivity().getIntent();
        myinfo = intent.getStringArrayListExtra("info");



        String [] hosp1 = resource.getStringArray(R.array.hosp1);
        String [] hosp2 = resource.getStringArray(R.array.hosp2);
        String [] hosp3 = resource.getStringArray(R.array.hosp3);
        String [] hosp4 = resource.getStringArray(R.array.hosp4);
        String [] hosp5 = resource.getStringArray(R.array.hosp5);

        name.setText(myinfo.get(0).toString());
        status.setText(myinfo.get(3).toString());

        if(Integer.parseInt(String.valueOf(myinfo.get(2))) == Integer.parseInt(hosp1[0])){
            hospital.setText(hosp1[3]);
            String addr = hosp1[4]+", "+hosp1[5]+", "+hosp1[6]+", "+hosp1[7];
            address.setText(addr);
        }
        else if(Integer.parseInt(String.valueOf(myinfo.get(2))) == Integer.parseInt(hosp2[0])){
            hospital.setText(hosp2[3]);
            String addr = hosp2[4]+", "+hosp2[5]+", "+hosp2[6]+", "+hosp2[7];
            address.setText(addr);
        }
        else if(Integer.parseInt(String.valueOf(myinfo.get(2))) == Integer.parseInt(hosp3[0])){
            hospital.setText(hosp3[3]);
            String addr = hosp3[4]+", "+hosp3[5]+", "+hosp3[6]+", "+hosp3[7];
            address.setText(addr);
        }
        else if(Integer.parseInt(String.valueOf(myinfo.get(2))) == Integer.parseInt(hosp4[0])){
            hospital.setText(hosp4[3]);
            String addr = hosp4[4]+", "+hosp4[5]+", "+hosp4[6]+", "+hosp4[7];
            address.setText(addr);
        }
        else if(Integer.parseInt(String.valueOf(myinfo.get(2))) == Integer.parseInt(hosp2[0])){
            hospital.setText(hosp5[3]);
            String addr = hosp5[4]+", "+hosp5[5]+", "+hosp5[6]+", "+hosp5[7];
            address.setText(addr);
        }




        //final TextView textView = root.findViewById(R.id.text_home);
      /*  homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}