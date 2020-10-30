package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class SuscripcionFondosBean {
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("hora")
    private String hora;
    @SerializedName("idFondo")
    private String idFondo;
    @SerializedName("importeSusc")
    private String importe;
    @SerializedName("legales")
    private LegalesFondosBean legales;
    @SerializedName("nroCertificado")
    private String nroCertificado;

    public SuscripcionFondosBean() {
    }

    public SuscripcionFondosBean(String str, String str2, String str3, String str4, String str5, LegalesFondosBean legalesFondosBean) {
        this.fecha = str;
        this.hora = str2;
        this.idFondo = str3;
        this.importe = str4;
        this.nroCertificado = str5;
        this.legales = legalesFondosBean;
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

    public String getIdFondo() {
        return this.idFondo;
    }

    public void setIdFondo(String str) {
        this.idFondo = str;
    }

    public String getImporte() {
        return this.importe;
    }

    public void setImporte(String str) {
        this.importe = str;
    }

    public String getNroCertificado() {
        return this.nroCertificado;
    }

    public void setNroCertificado(String str) {
        this.nroCertificado = str;
    }

    public LegalesFondosBean getLegales() {
        return this.legales;
    }

    public void setLegales(LegalesFondosBean legalesFondosBean) {
        this.legales = legalesFondosBean;
    }
}
