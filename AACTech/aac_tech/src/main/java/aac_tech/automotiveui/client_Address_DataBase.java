package aac_tech.automotiveui;

public class client_Address_DataBase {

    private String cityName;
    private String zipName;
    private String streetName;
    private String phoneName;
    private String provinceName;

    public client_Address_DataBase (){

    }

    public client_Address_DataBase(String cityName, String zipName, String streetName, String phoneName, String provinceName) {
        this.cityName = cityName;
        this.zipName = zipName;
        this.streetName = streetName;
        this.phoneName = phoneName;
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getZipName() {
        return zipName;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public String getProvinceName() {
        return provinceName;
    }
}
