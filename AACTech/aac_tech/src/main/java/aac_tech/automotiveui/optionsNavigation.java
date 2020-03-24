/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Long.parseLong;

public class optionsNavigation extends AppCompatActivity {
//    private DrawerLayout mDrawerLayout;
    Resources res;
    private String [] hosp1, hosp2, hosp3, hosp4, hosp5;
    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);

      //  database = FirebaseDatabase.getInstance().getReference().child("paramedics");


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

         AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
              .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        /*database.addValueEventListener(new ValueEventListener() {
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
        });*/



    //    mDrawerLayout = findViewById(R.id.drawer_layout);

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        Intent intent = getIntent();

        ArrayList<String> paraInfo = intent.getStringArrayListExtra("info");


        switch(item.getItemId()){
            case R.id.hosp_inf:
                displayHospDialog(paraInfo);
                break;
            case R.id.signout:
                displaySignOutdialog(paraInfo);
                break;
            case R.id.par_stat:
                displayStateDialog(paraInfo);
                break;
        }

        return true;
    }

    private void displaySignOutdialog(final ArrayList<String> info){
        String instruct4 = new String();
        instruct4 = getResources().getString(R.string.signout_dialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(optionsNavigation.this);
        builder.setMessage(instruct4);
        builder.setCancelable(true);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
                Intent intent = new Intent(getApplicationContext(),paramedLogin.class);

               // intent.putExtra("sign_out",info.get(5).toString());
               // database.child(myinfo.get(5).toString()).child("status").setValue("inactive");
                startActivity(intent);
                UpdateInfo update = new UpdateInfo();

                update.inactiveState(info.get(5).toString());
                finish();
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

    private void displayStateDialog(ArrayList<String> info){
        long state_start = Long.parseLong(info.get(6));
        Date date = new Date();
        long state_duration = date.getTime();

        long difference = (state_duration - state_start)/1000;
        int days = 0, hours = 0, mins = 0, secs = 0;

        if(difference >= 86400){ // If state duration has been on for a day or more, then do this
            days = (int)(difference/86400);
            difference -= (days*86400);
        }
        if(difference >= 3600){ // If state duration has been on for an hour or more, do this
            hours = (int)(difference/3600);
            difference -= (hours*3600);
        }
        if(difference >= 60){
            mins = (int)(difference/60);
            difference -= (mins*60);
        }

        secs = (int)difference;

        String instruct3 = new String();

        instruct3 = getResources().getString(R.string.stat_dialog1)+" "+info.get(3).toString()+"."+
                "\n\n"+getResources().getString(R.string.stat_dialog2)+" "+info.get(3).toString()+" "+getResources().getString(R.string.stat_dialog7)+" "+days+" "+getResources().getString(R.string.stat_dialog8)+", "+hours+
                " "+getResources().getString(R.string.stat_dialog3)+" "+mins+" "+getResources().getString(R.string.stat_dialog4)+" "+secs+" "+getResources().getString(R.string.stat_dialog5)+"\n\n"+
                getResources().getString(R.string.stat_dialog6);

        AlertDialog.Builder builder = new AlertDialog.Builder(optionsNavigation.this);
        builder.setMessage(instruct3);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void displayHospDialog(ArrayList<String> info){
        int hosp_offset = 0;
        String instruct2 = new String();

        String [] hosp_list = new String[5];

        hosp_list = getHospitals();



        for(int cnt=0; cnt < hosp_list.length;cnt++){
            if(Integer.parseInt(info.get(2).toString()) == (cnt + 1)){
                hosp_offset = cnt;
                break;
            }
        }

        instruct2 = getResources().getString(R.string.hosp_dialog1)+"\n\n"+hosp_list[hosp_offset]+"\n"+
                "\n"+getResources().getString(R.string.hosp_dialog2)+"\n\n"+info.get(7);

        AlertDialog.Builder builder = new AlertDialog.Builder(optionsNavigation.this);
        builder.setMessage(instruct2);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }



    private String [] getHospitals(){
        String [] hosp_list;

        //Get Hospital Information
        res = getResources();

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

        return hosp_list;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.paramed_menu, menu);

        return true;
    }
}
