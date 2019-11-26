/*
 * Team-Name: AAC-Tech

 */
package aac_tech.automotiveui;

//Will be used to save changed status info to Database
public class UpdateInfo {
    private String date;
    private String activity;
    private String status;

    public UpdateInfo(){

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
}
