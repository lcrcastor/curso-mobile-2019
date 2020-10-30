package ar.com.santander.rio.mbanking.services.model.general;

import com.google.android.gms.maps.model.LatLng;

public class PlaceMap {
    public boolean isHeader = false;
    private String lat;
    private String lon;
    private String name;
    private String place_id;

    public PlaceMap() {
    }

    public PlaceMap(String str, String str2, String str3, String str4) {
        this.name = str;
        this.lat = str2;
        this.lon = str3;
        this.place_id = str4;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getLat() {
        return this.lat;
    }

    public void setLat(String str) {
        this.lat = str;
    }

    public String getLon() {
        return this.lon;
    }

    public void setLon(String str) {
        this.lon = str;
    }

    public String getPlace_id() {
        return this.place_id;
    }

    public void setPlace_id(String str) {
        this.place_id = str;
    }

    public LatLng getLatLng() {
        return new LatLng(Double.parseDouble(this.lat), Double.parseDouble(this.lon));
    }

    public String toString() {
        return getName();
    }
}
