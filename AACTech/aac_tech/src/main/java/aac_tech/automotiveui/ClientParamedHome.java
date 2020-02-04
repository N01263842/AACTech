package aac_tech.automotiveui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * Team-Name: AAC-Tech

 */
public class ClientParamedHome extends AppCompatActivity {

    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_paramed_home);

        final Button paramedic = (Button)findViewById(R.id.homescreenB1);
        final Button client = (Button)findViewById(R.id.homescreenB2);


        paramedic.setVisibility(View.GONE);
        client.setVisibility(View.GONE);


    /*   paramedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientParamedHome.this,paramedLogin.class);
                startActivity(intent);


            }
        });

        paramedic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == KeyEvent.ACTION_DOWN){
                    paramedic.setBackgroundColor(Color.BLUE);
                }
                else if(motionEvent.getAction() == KeyEvent.ACTION_UP){
                    paramedic.setBackgroundColor(Color.RED);
                }

                return false;
            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientGetParamedic();
            }
        });

        client.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == KeyEvent.ACTION_DOWN){
                    client.setBackgroundColor(Color.BLUE);
                }
                else if(motionEvent.getAction() == KeyEvent.ACTION_UP){
                    client.setBackgroundColor(Color.RED);
                }

                return false;
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        DialogInterface.OnClickListener mClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int chose) {
                Intent intent;
                switch (chose){
                    case DialogInterface.BUTTON_POSITIVE:
                        intent = new Intent(ClientParamedHome.this, paramedLogin.class);
                        startActivity(intent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        intent = new Intent(ClientParamedHome.this, videoChat_Activity.class);
                        startActivity(intent);
                        break;
                }//End of switch
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_title2);
        builder.setMessage(getResources().getString(R.string.ident_msg))
                .setPositiveButton(R.string.positiveChoice, mClickListener)
                .setNegativeButton(R.string.negetiveChoice, mClickListener).show();
    }

    //Android softkey handling
    @Override
    public void onBackPressed(){
        closeApp(R.string.alert_Message);
    }

    //Creating the dialog box and handling the option chosen
    public void closeApp (int alertText){
        DialogInterface.OnClickListener mClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int chose) {
                switch (chose){
                    case DialogInterface.BUTTON_POSITIVE:
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }//End of switch
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_title);
        builder.setMessage(alertText)
                .setPositiveButton(R.string.positiveChoice, mClickListener)
                .setNegativeButton(R.string.negetiveChoice, mClickListener).show();
    }//End of closeApp

    private void ClientGetParamedic(){
        database = FirebaseDatabase.getInstance().getReference().child("paramedics");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot paramedInfo:dataSnapshot.getChildren()){
                    if(paramedInfo.child("status").getValue().toString().equals("active")){
                        String activeParamed = new String();
                        activeParamed = paramedInfo.child("username").getValue().toString();
                        String parent = new String();
                        parent = paramedInfo.getKey();
                        Intent intent = new Intent(getApplicationContext(),videoChat_Activity.class);
                        intent.putExtra("para_id",activeParamed);
                        intent.putExtra("parent",parent);
                        startActivity(intent);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
