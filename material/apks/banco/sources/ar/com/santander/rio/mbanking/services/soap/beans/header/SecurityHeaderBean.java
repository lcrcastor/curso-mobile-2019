package ar.com.santander.rio.mbanking.services.soap.beans.header;

import com.google.gson.annotations.SerializedName;

public class SecurityHeaderBean {
    @SerializedName("rsa")
    public String rsa;
    @SerializedName("token")
    public String token;

    public SecurityHeaderBean() {
    }

    public SecurityHeaderBean(String str, String str2) {
        this.rsa = str;
        this.token = str2;
    }
}
