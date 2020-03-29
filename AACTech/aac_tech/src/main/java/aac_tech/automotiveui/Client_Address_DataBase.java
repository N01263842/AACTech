// Team Name: AAC-Tech

package aac_tech.automotiveui;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Client_Address_DataBase {

    private String cl_city;
    private String cl_zip;
    private String cl_street;
    private String contact;
    private String cl_province;
    private String para_id;
    private String time;
    private String cl_name;
    private String em_status;
    private String temp;
    private String hrate;
    private String spo2;
    private long loc_lat;
    private long loc_long;

    public Client_Address_DataBase(){

    }

    public void setLoc_lat(long loc_lat) {
        this.loc_lat = loc_lat;
    }

    public void setLoc_long(long loc_long) {
        this.loc_long = loc_long;
    }

    public long getLoc_lat() {
        return loc_lat;
    }

    public long getLoc_long() {
        return loc_long;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setHrate(String hrate) {
        this.hrate = hrate;
    }

    public void setSpo2(String spo2) {
        this.spo2 = spo2;
    }

    public String getTemp() {
        return temp;
    }

    public String getHrate() {
        return hrate;
    }

    public String getSpo2() {
        return spo2;
    }

    public void setTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        long now = cal.getTimeInMillis();

        this.time = String.valueOf(now);
    }

    public void setEm_status(String em_status) {
        this.em_status = em_status;
    }

    public String getEm_status() {
        return em_status;
    }

    public String getTime() {
        return time;
    }

    public String getCl_name() {
        return cl_name;
    }

    public void setCl_name(String name) {
        this.cl_name = name;
    }

    public void setPara_id(String para_id) {
        this.para_id = para_id;
    }

    public void setCl_city(String cl_city) {
        this.cl_city = cl_city;
    }

    public void setCl_zip(String cl_zip) {
        this.cl_zip = cl_zip;
    }

    public void setCl_street(String cl_street) {
        this.cl_street = cl_street;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCl_province(String cl_province) {
        this.cl_province = cl_province;
    }

    public String getPara_id() {
        return para_id;
    }

    public String getCl_city() {
        return cl_city;
    }

    public String getCl_zip() {
        return cl_zip;
    }

    public String getCl_street() {
        return cl_street;
    }

    public String getContact() {
        return contact;
    }

    public String getCl_province() {
        return cl_province;
    }
}
