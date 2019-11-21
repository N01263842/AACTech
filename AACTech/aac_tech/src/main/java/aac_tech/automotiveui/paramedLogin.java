/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class paramedLogin extends AppCompatActivity {

   private DatabaseReference database;
   ArrayList paraInfo;
   private paramedInfo par;
   private EditText uname;
   private EditText passwd;
   private int data_retrieved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramed_login);


        uname = (EditText)findViewById(R.id.uname);
        passwd = (EditText)findViewById(R.id.passwd);
        Button signin = (Button)findViewById(R.id.signin);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = uname.getText().toString();
                String password = passwd.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast toast = Toast.makeText(paramedLogin.this, "All fields are required", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    getParamedicInfo();
                }


            }
        });

    }

    public void getParamedicInfo(){

        paraInfo = new ArrayList();
        par = new paramedInfo();
        data_retrieved = 0;

        database = FirebaseDatabase.getInstance().getReference().child("paramedics");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot paramedData : dataSnapshot.getChildren()){
                    String username = paramedData.child("username").getValue().toString();

                    if(username.equals(uname.getText().toString())){

                        String name = paramedData.child("name").getValue().toString();
                        String pass = paramedData.child("pass").getValue().toString();

                        par.setFullName(name);
                        par.setPasswd(pass);
                        par.setUsername(username);

                        paraInfo.add(name);
                        paraInfo.add(username);
                        paraInfo.add(paramedData.child("hospitalID").getValue().toString());
                        paraInfo.add(paramedData.child("status").getValue().toString());
                        paraInfo.add(paramedData.child("login").getValue().toString());


                        data_retrieved = 1;

                        if(pass.equals(passwd.getText().toString())){
                            Intent intent = new Intent(paramedLogin.this, optionsNavigation.class);
                            intent.putStringArrayListExtra("info",paraInfo);
                            startActivity(intent);
                        } else {
                            Toast toast = Toast.makeText(paramedLogin.this, "Incorrect password!", Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                }

                if(data_retrieved == 0){
                    Toast toast = Toast.makeText(paramedLogin.this, "Username has not been registered", Toast.LENGTH_LONG);
                    toast.show();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
