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

    private EditText city, zip, street, phone, province;
    private Button submit_b;

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

        city = (EditText)findViewById(R.id.city);
        zip = (EditText)findViewById(R.id.zip);
        street = (EditText)findViewById(R.id.street);
        phone = (EditText)findViewById(R.id.phone);
        province = (EditText)findViewById(R.id.province);
        submit_b =(Button)findViewById(R.id.submit_b);

        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
