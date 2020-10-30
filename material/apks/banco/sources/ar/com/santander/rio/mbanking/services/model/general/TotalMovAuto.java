package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class TotalMovAuto {
    @SerializedName("dolares")
    private String dolares;
    @SerializedName("pesos")
    private String pesos;

    public TotalMovAuto(String str, String str2) {
        this.dolares = str;
        this.pesos = str2;
    }

    public TotalMovAuto() {
    }

    public String getDolares() {
        return this.dolares;
    }

    public void setDolares(String str) {
        this.dolares = str;
    }

    public String getPesos() {
        return this.pesos;
    }

    public void setPesos(String str) {
        this.pesos = str;
    }
}
