package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class SimularTransferenciaFondoBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cantCuotaOrigen")
    private String cantCuotaOrigen;
    @SerializedName("comisionDestino")
    private String comisionDestino;
    @SerializedName("comisionOrigen")
    private String comisionOrigen;
    @SerializedName("idFondoDestino")
    private String idFondoDestino;
    @SerializedName("idFondoOrigen")
    private String idFondoOrigen;
    @SerializedName("importeTransferencia")
    private String importeTransferir;
    @SerializedName("leyendaEspecial")
    private String leyendaEspecial;
    @SerializedName("legales")
    private LegalesFondosBean listaLegales;
    @SerializedName("moneda")
    private String moneda;
    @SerializedName("monedaDestino")
    private String monedaDestino;
    @SerializedName("nombreDestino")
    private String nombreDestino;
    @SerializedName("nombreFondoOrigen")
    private String nombreFondoOrigen;
    @SerializedName("reglamento")
    private String reglamento;
    @SerializedName("termCondiciones")
    private String termCondiciones;

    public SimularTransferenciaFondoBodyResponseBean() {
    }

    public SimularTransferenciaFondoBodyResponseBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, LegalesFondosBean legalesFondosBean) {
        this.importeTransferir = str;
        this.moneda = str2;
        this.idFondoOrigen = str3;
        this.nombreFondoOrigen = str4;
        this.cantCuotaOrigen = str5;
        this.comisionOrigen = str6;
        this.idFondoDestino = str7;
        this.nombreDestino = str8;
        this.monedaDestino = str9;
        this.comisionDestino = str10;
        this.listaLegales = legalesFondosBean;
    }

    public String getImporteTransferir() {
        return this.importeTransferir;
    }

    public void setImporteTransferir(String str) {
        this.importeTransferir = str;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String str) {
        this.moneda = str;
    }

    public String getIdFondoOrigen() {
        return this.idFondoOrigen;
    }

    public void setIdFondoOrigen(String str) {
        this.idFondoOrigen = str;
    }

    public String getNombreFondoOrigen() {
        return this.nombreFondoOrigen;
    }

    public void setNombreFondoOrigen(String str) {
        this.nombreFondoOrigen = str;
    }

    public String getCantCuotaOrigen() {
        return this.cantCuotaOrigen;
    }

    public void setCantCuotaOrigen(String str) {
        this.cantCuotaOrigen = str;
    }

    public String getIdFondoDestino() {
        return this.idFondoDestino;
    }

    public void setIdFondoDestino(String str) {
        this.idFondoDestino = str;
    }

    public String getNombreDestino() {
        return this.nombreDestino;
    }

    public void setNombreDestino(String str) {
        this.nombreDestino = str;
    }

    public String getMonedaDestino() {
        return this.monedaDestino;
    }

    public void setMonedaDestino(String str) {
        this.monedaDestino = str;
    }

    public LegalesFondosBean getListaLegales() {
        return this.listaLegales;
    }

    public void setListaLegales(LegalesFondosBean legalesFondosBean) {
        this.listaLegales = legalesFondosBean;
    }

    public String getComisionOrigen() {
        return this.comisionOrigen;
    }

    public void setComisionOrigen(String str) {
        this.comisionOrigen = str;
    }

    public String getComisionDestino() {
        return this.comisionDestino;
    }

    public void setComisionDestino(String str) {
        this.comisionDestino = str;
    }

    public String getTermCondiciones() {
        return this.termCondiciones;
    }

    public void setTermCondiciones(String str) {
        this.termCondiciones = str;
    }

    public String getLeyendaEspecial() {
        return this.leyendaEspecial;
    }

    public void setLeyendaEspecial(String str) {
        this.leyendaEspecial = str;
    }

    public String getReglamento() {
        return this.reglamento;
    }

    public void setReglamento(String str) {
        this.reglamento = str;
    }
}
