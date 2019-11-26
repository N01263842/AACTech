/*
 * Team-Name: AAC-Tech

 */
 package aac_tech.automotiveui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class client_Activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity);

        Button call = (Button)findViewById(R.id.homescreenB3);

       //System.out.println("ok");

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(client_Activity.this,videoChat_Activity.class);
                startActivity(intent);
            }
        });



    }
}
