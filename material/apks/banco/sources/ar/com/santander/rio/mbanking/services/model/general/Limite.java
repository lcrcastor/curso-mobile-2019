package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class Limite {
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("monto")
    private String monto;

    public Limite(String str, String str2) {
        this.moneda = str;
        this.monto = str2;
    }

    public Limite(String str, String str2, String str3) {
        this.descripcion = str;
        this.monto = str2;
        this.moneda = str3;
    }

    public Limite() {
    }

    public String getMonto() {
        return this.monto;
    }

    public void setMonto(String str) {
        this.monto = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }
}
