package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class Autorizaciones {
    @SerializedName("resAutorizacion")
    private String resAutorizacion;
    @SerializedName("totalDolares")
    private String totalDolares;
    @SerializedName("totalPesos")
    private String totalPesos;

    public Autorizaciones(String str, String str2, String str3) {
        this.resAutorizacion = str;
        this.totalPesos = str2;
        this.totalDolares = str3;
    }

    public Autorizaciones() {
    }

    public String getResAutorizacion() {
        return this.resAutorizacion;
    }

    public void setResAutorizacion(String str) {
        this.resAutorizacion = str;
    }

    public String getTotalPesos() {
        return this.totalPesos;
    }

    public void setTotalPesos(String str) {
        this.totalPesos = str;
    }

    public String getTotalDolares() {
        return this.totalDolares;
    }

    public void setTotalDolares(String str) {
        this.totalDolares = str;
    }
}
