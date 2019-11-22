package aac_tech.automotiveui;

import androidx.appcompat.app.AppCompatActivity;

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
        Button client = (Button)findViewById(R.id.homescreenB2);


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
                    paramedic.setBackgroundColor(Color.BLUE);
                }
                else if(motionEvent.getAction() == KeyEvent.ACTION_UP){
                    paramedic.setBackgroundColor(Color.RED);
                }

                return false;
            }
        });
    }
}
