package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.creditos.Leyendas;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmarPagoBodyResponseBean extends ErrorBodyBean {
    @SerializedName("capitalCuota")
    @Expose
    private String capitalCuota;
    @SerializedName("capitalUVA")
    @Expose
    private String capitalUVA;
    @SerializedName("condicionIVA")
    @Expose
    private String condicionIVA;
    @SerializedName("cuil")
    @Expose
    private String cuil;
    @SerializedName("fechaHora")
    @Expose
    private String fechaHora;
    @SerializedName("importeCuota")
    @Expose
    private String importeCuota;
    @SerializedName("importeCuotaUVA")
    @Expose
    private String importeCuotaUVA;
    @SerializedName("interesesCompPeriodo")
    @Expose
    private String interesesCompPeriodo;
    @SerializedName("interesesCompVto")
    @Expose
    private String interesesCompVto;
    @SerializedName("interesesUVA")
    @Expose
    private String interesesUVA;
    @SerializedName("iva")
    @Expose
    private String iva;
    @SerializedName("leyendas")
    @Expose
    private Leyendas leyendas;
    @SerializedName("nroComprobante")
    @Expose
    private String nroComprobante;
    @SerializedName("otrosImpuestos")
    @Expose
    private String otrosImpuestos;
    @SerializedName("saldoCapitalSinAjustar")
    @Expose
    private String saldoCapitalSinAjustar;
    @SerializedName("titularCredito")
    @Expose
    private String titularCredito;

    public String getTitularCredito() {
        return this.titularCredito;
    }

    public void setTitularCredito(String str) {
        this.titularCredito = str;
    }

    public String getCuil() {
        return this.cuil;
    }

    public void setCuil(String str) {
        this.cuil = str;
    }

    public String getCondicionIVA() {
        return this.condicionIVA;
    }

    public void setCondicionIVA(String str) {
        this.condicionIVA = str;
    }

    public String getSaldoCapitalSinAjustar() {
        return this.saldoCapitalSinAjustar;
    }

    public void setSaldoCapitalSinAjustar(String str) {
        this.saldoCapitalSinAjustar = str;
    }

    public String getInteresesCompPeriodo() {
        return this.interesesCompPeriodo;
    }

    public void setInteresesCompPeriodo(String str) {
        this.interesesCompPeriodo = str;
    }

    public String getInteresesCompVto() {
        return this.interesesCompVto;
    }

    public void setInteresesCompVto(String str) {
        this.interesesCompVto = str;
    }

    public String getOtrosImpuestos() {
        return this.otrosImpuestos;
    }

    public void setOtrosImpuestos(String str) {
        this.otrosImpuestos = str;
    }

    public Leyendas getLeyendas() {
        return this.leyendas;
    }

    public void setLeyendas(Leyendas leyendas2) {
        this.leyendas = leyendas2;
    }

    public String getNroComprobante() {
        return this.nroComprobante;
    }

    public String getFechaHora() {
        return this.fechaHora;
    }

    public void setRes(String str) {
        this.res = str;
    }

    public String getRes() {
        return this.res;
    }

    public String getImporteCuota() {
        return this.importeCuota;
    }

    public void setImporteCuota(String str) {
        this.importeCuota = str;
    }

    public String getCapitalCuota() {
        return this.capitalCuota;
    }

    public void setCapitalCuota(String str) {
        this.capitalCuota = str;
    }

    public String getImporteCuotaUVA() {
        return this.importeCuotaUVA;
    }

    public void setImporteCuotaUVA(String str) {
        this.importeCuotaUVA = str;
    }

    public String getCapitalUVA() {
        return this.capitalUVA;
    }

    public void setCapitalUVA(String str) {
        this.capitalUVA = str;
    }

    public String getInteresesUVA() {
        return this.interesesUVA;
    }

    public void setInteresesUVA(String str) {
        this.interesesUVA = str;
    }

    public String getIva() {
        return this.iva;
    }

    public void setIva(String str) {
        this.iva = str;
    }
}
