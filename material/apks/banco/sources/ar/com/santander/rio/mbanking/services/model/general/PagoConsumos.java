package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class PagoConsumos {
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("importe")
    private String importe;
    @SerializedName("tipoMoneda")
    private String tipoMoneda;

    public PagoConsumos(String str, String str2, String str3, String str4) {
        this.descripcion = str;
        this.fecha = str2;
        this.importe = str3;
        this.tipoMoneda = str4;
    }

    public PagoConsumos() {
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String str) {
        this.descripcion = str;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String str) {
        this.fecha = str;
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String str) {
        this.importe = str;
    }

    public String getTipoMoneda() {
        return this.tipoMoneda;
    }

    public void setTipoMoneda(String str) {
        this.tipoMoneda = str;
    }
}
