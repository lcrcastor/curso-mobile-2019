package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class Pago {
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("tipoMoneda")
    private String tipoMoneda;
    @SerializedName("total")
    private String total;
    @SerializedName("usdTotal")
    private String usdTotal;

    public Pago(String str, String str2, String str3, String str4) {
        this.descripcion = str;
        this.tipoMoneda = str2;
        this.total = str3;
        this.usdTotal = str4;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getTipoMoneda() {
        return this.tipoMoneda;
    }

    public void setTipoMoneda(String str) {
        this.tipoMoneda = str;
    }

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String str) {
        this.total = str;
    }

    public String getUsdTotal() {
        return this.usdTotal;
    }

    public void setUsdTotal(String str) {
        this.usdTotal = str;
    }
}
