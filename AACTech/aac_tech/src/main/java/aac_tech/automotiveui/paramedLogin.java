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
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;


public class paramedLogin extends AppCompatActivity {

   private DatabaseReference database;
   ArrayList paraInfo;
   private paramedInfo par;
   private EditText uname;
   private EditText passwd;
   private int data_retrieved;
   private ValueEventListener myvalueEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paramed_login);

        database = FirebaseDatabase.getInstance().getReference().child("paramedics");

        uname = (EditText)findViewById(R.id.uname);
        passwd = (EditText)findViewById(R.id.passwd);

        Button signin = (Button)findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = uname.getText().toString();
                String password = passwd.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast toast = Toast.makeText(paramedLogin.this, R.string.field_warning, Toast.LENGTH_LONG);
                    toast.show();
                }
                else if(!isValidUName(username) || !isValidPassword(password)){
                    Toast toast = Toast.makeText(paramedLogin.this, R.string.pattern_warning, Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    getParamedicInfo();
                }
            }
        });

        //Setting the title and enabling the up arrow
        //getSupportActionBar().setTitle(R.string.viewInfo_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void getParamedicInfo(){


        database.addValueEventListener(myvalueEvent = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String pass = new String();
                String parent = new String();
                data_retrieved = 0;
                paraInfo = new ArrayList();
                par = new paramedInfo();
                Date date = new Date();

                for(DataSnapshot paramedData : dataSnapshot.getChildren()){
                    String username = paramedData.child("username").getValue().toString();

                        if (username.equals(uname.getText().toString())) {

                            String name = paramedData.child("name").getValue().toString();
                            pass = paramedData.child("pass").getValue().toString();
                            parent = paramedData.getKey();


                            par.setFullName(name);
                            par.setPasswd(pass);
                            par.setUsername(username);

                            //System.out.println("Just testing something");
                            date = Calendar.getInstance().getTime();

                            paraInfo.add(name);
                            paraInfo.add(username);
                            paraInfo.add(paramedData.child("hospitalID").getValue().toString());
                            paraInfo.add("active");
                            paraInfo.add(paramedData.child("login").getValue().toString());
                            paraInfo.add(parent);
                            paraInfo.add(Long.toString(date.getTime()));
                            paraInfo.add(paramedData.child("date").getValue().toString());

                            // database.child(parent).child("status").setValue("active");


                            data_retrieved = 1;

                            break;

                        }
                    else{
                        data_retrieved = -1;
                        }
                }

                if(data_retrieved == 1) {

                    if (pass.equals(passwd.getText().toString())) {
                        UpdateInfo update = new UpdateInfo();
                        Intent intent = new Intent(paramedLogin.this, optionsNavigation.class);
                        intent.putStringArrayListExtra("info", paraInfo);
                        database.child(parent).child("status").setValue("active");
                        database.child(parent).child("activity").setValue(date.getTime());
                        database.removeEventListener(myvalueEvent);
                        startActivity(intent);
                        //uname.setText("");
                      //  passwd.setText("");

                    } else {
                        Toast toast = Toast.makeText(paramedLogin.this, "Incorrect password!", Toast.LENGTH_LONG);
                        toast.show();
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

    //Validating the username pattern
    boolean isValidUName(String username){
        String nameRegex = "[a-zA-Z0-9\\\\._\\\\-]{3,}";

        Pattern pat = Pattern.compile(nameRegex);
        if (username == null)
            return false;
        return pat.matcher(username).matches();
    }

    //Validating the password pattern
    boolean isValidPassword(String password){
        String pwdRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=\\S+$).{8,}$";

        Pattern pat = Pattern.compile(pwdRegex);
        if (password == null)
            return false;
        return pat.matcher(password).matches();
    }
}
