package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class Movimiento {
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("montoDolares")
    private String montoDolares;
    @SerializedName("montoPesos")
    private String montoPesos;

    public Movimiento(String str, String str2, String str3, String str4) {
        this.fecha = str;
        this.descripcion = str2;
        this.montoPesos = str3;
        this.montoDolares = str4;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String str) {
        this.fecha = str;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getMontoPesos() {
        return this.montoPesos;
    }

    public void setMontoPesos(String str) {
        this.montoPesos = str;
    }

    public String getMontoDolares() {
        return this.montoDolares;
    }

    public void setMontoDolares(String str) {
        this.montoDolares = str;
    }
}
