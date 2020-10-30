package ar.com.santander.rio.mbanking.services.soap.beans.body;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;

public class LocalizacionBean {
    private String latitud;
    private String longitud;

    public LocalizacionBean(String str, String str2) {
        this.latitud = str;
        this.longitud = str2;
    }

    public void updateLocation(LatLng latLng) {
        if (latLng != null) {
            this.latitud = String.valueOf(latLng.latitude);
            this.longitud = String.valueOf(latLng.longitude);
        }
    }

    public void changeLocation(Location location) {
        this.latitud = String.valueOf(location.getLatitude());
        this.longitud = String.valueOf(location.getLongitude());
    }

    public String getLatitud() {
        return this.latitud;
    }

    public void setLatitud(String str) {
        this.latitud = str;
    }

    public String getLongitud() {
        return this.longitud;
    }

    public void setLongitud(String str) {
        this.longitud = str;
    }
}
