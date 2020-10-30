package ar.com.santander.rio.mbanking.services.soap.beans.header;

import com.google.gson.annotations.SerializedName;

public class LocalitationHeaderBean {
    @SerializedName("latitud")
    public String latitude;
    @SerializedName("longitud")
    public String longitude;

    public LocalitationHeaderBean() {
    }

    public LocalitationHeaderBean(String str, String str2) {
        this.latitude = str;
        this.longitude = str2;
    }
}
