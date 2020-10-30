package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class PlazoFijo {
    @SerializedName("capital")
    private String capital = "";
    @SerializedName("fecha")
    private String fecha = "";
    @SerializedName("tasa")
    private String tasa = "";
    @SerializedName("tipo")
    private String tipo = "";
    @SerializedName("total")
    private String total = "";

    public PlazoFijo() {
    }

    public PlazoFijo(String str, String str2, String str3, String str4, String str5) {
        this.tipo = str;
        this.fecha = str2;
        this.tasa = str3;
        this.capital = str4;
        this.total = str5;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String str) {
        this.tipo = str;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String str) {
        this.fecha = str;
    }

    public String getTasa() {
        return this.tasa;
    }

    public void setTasa(String str) {
        this.tasa = str;
    }

    public String getCapital() {
        return this.capital;
    }

    public void setCapital(String str) {
        this.capital = str;
    }

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String str) {
        this.total = str;
    }
}
