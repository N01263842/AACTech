/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

//Will be used to save changed status info to Database
public class UpdateInfo {
    private String date;
    private String activity;
    private String status;
    private DatabaseReference database;
    private int hospID;


    public UpdateInfo(){
        database = FirebaseDatabase.getInstance().getReference().child("paramedics");
    }

    public UpdateInfo(String date, String activity, String status) {
        this.date = date;
        this.activity = activity;
        this.status = status;
    }



    public void setDate(String date) {
        this.date = date;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getActivity() {
        return activity;
    }

    public String getStatus() {
        return status;
    }

    public void setHospID(int hospID){
        this.hospID = hospID;
    }

    public int getHospID(){
        return this.hospID;
    }

    public void updateHospital(String par_block){

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        //database = FirebaseDatabase.getInstance().getReference().child("paramedics");
        database.child(par_block).child("hospitalID").setValue(getHospID());
        database.child(par_block).child("date").setValue(formatter.format(date));
    }

    public void inactiveState(String par_block){

       // database = FirebaseDatabase.getInstance().getReference().child("paramedics");
        database.child(par_block).child("status").setValue("inactive");

    }

    public void sendRequest(String par_block){
       // database = FirebaseDatabase.getInstance().getReference().child("paramedics");
        database.child(par_block).child("video").setValue("yes");
    }


}
