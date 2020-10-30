package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class TransferenciaOrigenFondosBean {
    @SerializedName("cantCuotaparte")
    private String cantCuotaparte;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("fondoOrigen")
    private String fondoOrigen;
    @SerializedName("hora")
    private String hora;
    @SerializedName("idFondoOrigen")
    private String idFondoOrigen;
    @SerializedName("importeTransferencia")
    private String importe;
    @SerializedName("moneda")
    private String moneda;

    public TransferenciaOrigenFondosBean() {
    }

    public TransferenciaOrigenFondosBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.fecha = str;
        this.hora = str2;
        this.idFondoOrigen = str3;
        this.fondoOrigen = str4;
        this.importe = str5;
        this.moneda = str6;
        this.cantCuotaparte = str7;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String str) {
        this.fecha = str;
    }

    public String getHora() {
        return this.hora;
    }

    public void setHora(String str) {
        this.hora = str;
    }

    public String getIdFondoOrigen() {
        return this.idFondoOrigen;
    }

    public void setIdFondoOrigen(String str) {
        this.idFondoOrigen = str;
    }

    public String getFondoOrigen() {
        return this.fondoOrigen;
    }

    public void setFondoOrigen(String str) {
        this.fondoOrigen = str;
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String str) {
        this.importe = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getCantCuotaparte() {
        return this.cantCuotaparte;
    }

    public void setCantCuotaparte(String str) {
        this.cantCuotaparte = str;
    }
}
