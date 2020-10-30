package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ContratarSeguroMovilBodyResponseBean extends ErrorBodyBean {
    @SerializedName("codRamo")
    private String codRamo;
    @SerializedName("fechaInicio")
    private String fechaInicio;
    @SerializedName("leyenda")
    private LeyendasBean leyendas;
    @SerializedName("numCertificado")
    private String numCertificado;
    @SerializedName("numPoliza")
    private String numPoliza;

    public ContratarSeguroMovilBodyResponseBean() {
    }

    public ContratarSeguroMovilBodyResponseBean(String str, String str2, String str3, String str4, LeyendasBean leyendasBean) {
        this.fechaInicio = str;
        this.codRamo = str2;
        this.numPoliza = str3;
        this.numCertificado = str4;
        this.leyendas = leyendasBean;
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

    public LeyendasBean getLeyendas() {
        return this.leyendas;
    }

    public void setLeyendas(LeyendasBean leyendasBean) {
        this.leyendas = leyendasBean;
    }
}
