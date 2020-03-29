

/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Client_Address_Info extends AppCompatActivity {

    private DatabaseReference database;
    Client_Address_DataBase cad;

    private EditText city, zip, street, phone, province, name;
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
        name = (EditText)findViewById(R.id.name_cl);

        intent = getIntent();

        //wrting the validation for the input

        String cit = city.getText().toString();
        String zip_c = zip.getText().toString();
        String str = street.getText().toString();
        String ph = phone.getText().toString();
        String pro = province.getText().toString();

        if (cit.equals("") || zip_c.equals("") || str.equals("") || ph.equals("") || pro.equals("")) {
            Toast toast = Toast.makeText(Client_Address_Info.this, R.string.field_warning, Toast.LENGTH_LONG);
            toast.show();
        }
        else if(!isValidCity(cit)||!isValidZip(zip_c)||!isValidStreet(str)||!isValidPhone(ph)||!isValidProvince(pro)){
            Toast toast = Toast.makeText(Client_Address_Info.this, R.string.pattern_warning, Toast.LENGTH_LONG);
            toast.show();
        }
        else {
           return ;
        }

        if (ph.length() < 10 || ph.length() > 10) {
            Toast.makeText(getApplicationContext(), R.string.phone_warning, Toast.LENGTH_SHORT).show();
        }


        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String para_id = intent.getStringExtra("paramedic");

                database = FirebaseDatabase.getInstance().getReference().child("clients");
                cad = new Client_Address_DataBase();

                cad.setCl_city(city.getText().toString());
                cad.setCl_province(province.getText().toString());
                cad.setCl_street(street.getText().toString());
                cad.setContact(phone.getText().toString());
                cad.setCl_zip(zip.getText().toString());
                cad.setPara_id(para_id);
                cad.setTime();
                cad.setEm_status("done");
                cad.setHrate("pending");
                cad.setSpo2("pending");
                cad.setTemp("pending");
                cad.setLoc_lat(0);
                cad.setLoc_long(0);
                cad.setCl_name(name.getText().toString());

                String database_id = database.push().getKey();

                database.child(database_id).setValue(cad);

                String showSuccess = new String();
                showSuccess = getResources().getString(R.string.success_dialog);

                AlertDialog.Builder builder = new AlertDialog.Builder(Client_Address_Info.this);
                builder.setMessage(showSuccess);
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
        });
    }

    //Validating the city pattern
    boolean isValidCity(String city){
        String cityRegex = "^[a-zA-Z]+(?:[\\s-][a-zA-Z]+)*$";

        Pattern pat = Pattern.compile(cityRegex);
        if (city== null)
            return false;
        return pat.matcher(city).matches();
    }

    //Validating the zip pattern
    boolean isValidZip(String zip){
        String zipRegex = "[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]";

        Pattern pat = Pattern.compile(zipRegex);
        if (zip == null)
            return false;
        return pat.matcher(zip).matches();
    }

    //Validating the street pattern
    boolean isValidStreet(String street){
        String streetRegex = "[A-Za-z'\\.\\s\\,]{3,}";

        Pattern pat = Pattern.compile(streetRegex);
        if (street == null)
            return false;
        return pat.matcher(street).matches();
    }

    //Validating the phone pattern
    boolean isValidPhone(String phone){
        String phoneRegex = "[0-9]{10}";

        Pattern pat = Pattern.compile(phoneRegex);
        if (phone == null)
            return false;
        return pat.matcher(phone).matches();
    }

    //Validating the province pattern
    boolean isValidProvince(String province){
        String provinceRegex = "[a-zA-Z\\\\._\\\\-]{2,}";

        Pattern pat = Pattern.compile(provinceRegex);
        if (province == null)
            return false;
        return pat.matcher(province).matches();
    }

}
