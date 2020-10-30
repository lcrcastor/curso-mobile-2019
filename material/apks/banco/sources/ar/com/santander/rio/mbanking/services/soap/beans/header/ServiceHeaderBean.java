package ar.com.santander.rio.mbanking.services.soap.beans.header;

import com.google.gson.annotations.SerializedName;

public class ServiceHeaderBean {
    @SerializedName("nombre")
    public String name;
    public String version;

    public ServiceHeaderBean() {
    }

    public ServiceHeaderBean(String str, String str2) {
        this.name = str;
        this.version = str2;
    }
}
