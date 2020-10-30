package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ContratarCompraProtegidaBodyResponseBean extends ErrorBodyBean {
    @SerializedName("codRamo")
    private String codRamo;
    @SerializedName("fechaInicio")
    private String fechaInicio;
    @SerializedName("numCertificado")
    private String numCertificado;
    @SerializedName("numPoliza")
    private String numPoliza;

    public ContratarCompraProtegidaBodyResponseBean() {
    }

    public ContratarCompraProtegidaBodyResponseBean(String str, String str2, String str3, String str4) {
        this.fechaInicio = str;
        this.codRamo = str2;
        this.numPoliza = str3;
        this.numCertificado = str4;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String str) {
        this.fechaInicio = str;
    }

    public String getCodRamo() {
        return this.codRamo;
    }

    public void setCodRamo(String str) {
        this.codRamo = str;
    }

    public String getNumPoliza() {
        return this.numPoliza;
    }

    public void setNumPoliza(String str) {
        this.numPoliza = str;
    }

    public String getNumCertificado() {
        return this.numCertificado;
    }

    public void setNumCertificado(String str) {
        this.numCertificado = str;
    }
}
