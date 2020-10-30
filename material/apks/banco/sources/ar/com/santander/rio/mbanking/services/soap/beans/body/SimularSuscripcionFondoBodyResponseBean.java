package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class SimularSuscripcionFondoBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cotizaCambio")
    private String cotizaCambio;
    @SerializedName("idFondo")
    private String idFondo;
    @SerializedName("importeSusc")
    private String importeSusc;
    @SerializedName("leyendaEspecial")
    private String leyendaEspecial;
    @SerializedName("legales")
    private LegalesFondosBean listaLegales;
    @SerializedName("porcentComision")
    private String porcentComision;
    @SerializedName("reglamento")
    private String reglamento;
    @SerializedName("termCondiciones")
    private String termCondiciones;

    public SimularSuscripcionFondoBodyResponseBean() {
    }

    public SimularSuscripcionFondoBodyResponseBean(String str, String str2, String str3, String str4, LegalesFondosBean legalesFondosBean) {
        this.importeSusc = str;
        this.idFondo = str2;
        this.porcentComision = str3;
        this.cotizaCambio = str4;
        this.listaLegales = legalesFondosBean;
    }

    public String getImporteSusc() {
        return this.importeSusc;
    }

    public void setImporteSusc(String str) {
        this.importeSusc = str;
    }

    public String getIdFondo() {
        return this.idFondo;
    }

    public void setIdFondo(String str) {
        this.idFondo = str;
    }

    public String getPorcentComision() {
        return this.porcentComision;
    }

    public void setPorcentComision(String str) {
        this.porcentComision = str;
    }

    public String getCotizaCambio() {
        return this.cotizaCambio;
    }

    public void setCotizaCambio(String str) {
        this.cotizaCambio = str;
    }

    public LegalesFondosBean getListaLegales() {
        return this.listaLegales;
    }

    public void setListaLegales(LegalesFondosBean legalesFondosBean) {
        this.listaLegales = legalesFondosBean;
    }

    public String getTermCondiciones() {
        return this.termCondiciones;
    }

    public void setTermCondiciones(String str) {
        this.termCondiciones = str;
    }

    public String getReglamento() {
        return this.reglamento;
    }

    public void setReglamento(String str) {
        this.reglamento = str;
    }

    public String getLeyendaEspecial() {
        return this.leyendaEspecial;
    }

    public void setLeyendaEspecial(String str) {
        this.leyendaEspecial = str;
    }
}
