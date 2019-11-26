/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Client_Address_Info extends AppCompatActivity {

    private EditText city, zip, street, phone, province;
    private Button submit_b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__address__info);

        //Setting the title and enabling the up arrow
        getSupportActionBar().setTitle(R.string.clientInfo_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
