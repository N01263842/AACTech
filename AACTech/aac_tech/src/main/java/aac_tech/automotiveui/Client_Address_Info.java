/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Client_Address_Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__address__info);

        //Setting the title and enabling the up arrow
        getSupportActionBar().setTitle(R.string.clientInfo_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
