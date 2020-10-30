package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class RescatarFondoBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cantCuotaparte")
    public String cantCuotaParte;
    @SerializedName("comision")
    public String comision;
    @SerializedName("fecha")
    public String fecha;
    @SerializedName("hora")
    public String hora;
    @SerializedName("idFondo")
    public String idFondo;
    @SerializedName("importeNeto")
    public String importeNeto;
    @SerializedName("importeRescate")
    public String importeRescate;
    @SerializedName("legales")
    public LegalesFondosBean legales;
    @SerializedName("nroCertificado")
    public String nroCertificado;
    @SerializedName("plazoPago")
    public String plazoPago;
    @SerializedName("termCondiciones")
    public String termCondiciones;

    public RescatarFondoBodyResponseBean() {
    }

    public RescatarFondoBodyResponseBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, LegalesFondosBean legalesFondosBean) {
        this.idFondo = str;
        this.fecha = str2;
        this.hora = str3;
        this.importeRescate = str4;
        this.comision = str5;
        this.importeNeto = str6;
        this.cantCuotaParte = str7;
        this.nroCertificado = str8;
        this.legales = legalesFondosBean;
    }

    public RescatarFondoBodyResponseBean(String str, String str2, String str3, String str4, String str5, String str6, LegalesFondosBean legalesFondosBean) {
        this.idFondo = str;
        this.importeRescate = str2;
        this.comision = str3;
        this.importeNeto = str4;
        this.cantCuotaParte = str5;
        this.plazoPago = str6;
        this.legales = legalesFondosBean;
    }

    public String getIdFondo() {
        return this.idFondo;
    }

    public void setIdFondo(String str) {
        this.idFondo = str;
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

    public String getImporteRescate() {
        return this.importeRescate;
    }

    public void setImporteRescate(String str) {
        this.importeRescate = str;
    }

    public String getComision() {
        return this.comision;
    }

    public void setComision(String str) {
        this.comision = str;
    }

    public String getImporteNeto() {
        return this.importeNeto;
    }

    public void setImporteNeto(String str) {
        this.importeNeto = str;
    }

    public String getCantCuotaParte() {
        return this.cantCuotaParte;
    }

    public void setCantCuotaParte(String str) {
        this.cantCuotaParte = str;
    }

    public String getNroCertificado() {
        return this.nroCertificado;
    }

    public void setNroCertificado(String str) {
        this.nroCertificado = str;
    }

    public String getPlazoPago() {
        return this.plazoPago;
    }

    public void setPlazoPago(String str) {
        this.plazoPago = str;
    }

    public LegalesFondosBean getLegales() {
        return this.legales;
    }

    public void setLegales(LegalesFondosBean legalesFondosBean) {
        this.legales = legalesFondosBean;
    }

    public String getTermCondiciones() {
        return this.termCondiciones;
    }

    public void setTermCondiciones(String str) {
        this.termCondiciones = str;
    }
}
