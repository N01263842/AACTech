// Team Name: AAC-Tech

package aac_tech.automotiveui;

import com.google.firebase.database.DatabaseReference;

public class Client_Address_DataBase {

    private String cl_city;
    private String cl_zip;
    private String cl_street;
    private String contact;
    private String cl_province;
    private String para_id;

    public Client_Address_DataBase(){

    }

    public Client_Address_DataBase(String cityName, String zipName, String streetName, String phoneName, String provinceName,String para_id) {
        this.cl_city = cityName;
        this.cl_zip = zipName;
        this.cl_street = streetName;
        this.contact = phoneName;
        this.cl_province = provinceName;
        this.para_id = para_id;
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
