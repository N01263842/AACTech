/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

public class paramedInfo {

    private String fullName;
    private String username;
    private String passwd;
    private String login;
    private String status;
    private float loc_long;
    private float loc_lat;
    private int hospID;

    public paramedInfo (){
        this.fullName = "";
        this.username = "";
        this.passwd = "";
        this.login = "";
        this.status = "";
        this.loc_long = 0;
        this.loc_lat = 0;
        this.hospID = 0;
    }

    public paramedInfo(String fullName, String username, String passwd,
                       String login, String status, float loc_long, float loc_lat, int hospID){
        this.fullName = fullName;
        this.username = username;
        this.passwd = passwd;
        this.login = login;
        this.status = status;
        this.loc_long = loc_long;
        this.loc_lat = loc_lat;
        this.hospID = hospID;
    }

    public void setPasswd(String passwd){
        this.passwd = passwd;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setLoc_lat(float loc_lat) {
        this.loc_lat = loc_lat;
    }

    public void setLoc_long(float loc_long) {
        this.loc_long = loc_long;
    }

    public void setHospID(int hospID) {
        this.hospID = hospID;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getFullName(){
        return this.fullName;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPasswd(){
        return this.passwd;
    }

    public String getLogin(){
        return this.login;
    }

    public String getStatus(){
        return this.status;
    }

    public float getLoc_long(){
        return this.loc_long;
    }

    public float getLoc_lat(){
        return this.loc_lat;
    }

    public int getHospID(){
        return this.hospID;
    }

}
