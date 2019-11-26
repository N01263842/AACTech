

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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Client_Address_Info extends AppCompatActivity {

    private DatabaseReference database;
    Client_Address_DataBase cad;

    private EditText city, zip, street, phone, province;
    private Button submit_b;
    private Intent intent;
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

        intent = getIntent();

        //wrting the validation for the input

        String cit = city.getText().toString();
        String zip_c = zip.getText().toString();
        String str = street.getText().toString();
        String ph = street.getText().toString();
        String pro = province.getText().toString();

        if (cit.equals("") || zip_c.equals("") || str.equals("") || ph.equals("") || pro.equals("")) {
            Toast toast = Toast.makeText(Client_Address_Info.this, "All fields are required", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
           return ;
        }

        if (ph.length() < 10 || ph.length() > 10) {
            Toast.makeText(getApplicationContext(),
                    "Please enter a valied phone number", Toast.LENGTH_SHORT).show();
        }











        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String para_id = intent.getStringExtra("para_id");

                database = FirebaseDatabase.getInstance().getReference().child("clients");
                cad = new Client_Address_DataBase();

                cad.setCl_city(city.getText().toString());
                cad.setCl_province(province.getText().toString());
                cad.setCl_street(street.getText().toString());
                cad.setContact(phone.getText().toString());
                cad.setCl_zip(zip.getText().toString());
                cad.setPara_id(para_id);

                String database_id = database.push().getKey();

                database.child(database_id).setValue(cad);



            }
        });
    }
}
