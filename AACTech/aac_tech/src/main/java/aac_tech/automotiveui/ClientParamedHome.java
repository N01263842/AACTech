package aac_tech.automotiveui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/*
 * Team-Name: AAC-Tech

 */
public class ClientParamedHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_paramed_home);

        final Button paramedic = (Button)findViewById(R.id.homescreenB1);
        final Button client = (Button)findViewById(R.id.homescreenB2);


        paramedic.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(ClientParamedHome.this,videoChat_Activity.class);
                startActivity(intent);
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
        });
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
                .setPositiveButton("Yes", mClickListener)
                .setNegativeButton("No", mClickListener).show();
    }//End of closeApp
}
