package ar.com.santander.rio.mbanking.services.soap.beans.header;

import com.google.gson.annotations.SerializedName;

public class DeviceHeaderBean {
    public String idBl;
    public String idRuntime = "5";
    @SerializedName("idTerminal")
    public String idTerminal;
    @SerializedName("marca")
    public String marca;
    @SerializedName("modelo")
    public String modelo;
    @SerializedName("versionSO")
    public String versionSO;

    public DeviceHeaderBean() {
    }

    public DeviceHeaderBean(String str, String str2, String str3, String str4, String str5, String str6) {
        this.idBl = str;
        this.idRuntime = str2;
        this.marca = str3;
        this.modelo = str4;
        this.idTerminal = str5;
        this.versionSO = str6;
    }
}
