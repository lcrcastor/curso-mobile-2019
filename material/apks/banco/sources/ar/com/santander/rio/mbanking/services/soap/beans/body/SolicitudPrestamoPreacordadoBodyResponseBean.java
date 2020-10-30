package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamo;
import com.google.gson.annotations.SerializedName;

public class SolicitudPrestamoPreacordadoBodyResponseBean extends ErrorBodyBean {
    @SerializedName("datosSolicitudPrestamo")
    DatosSolicitudPrestamo datosSolicitudPrestamo;
    @SerializedName("linkSeguro")
    LinkSeguroBean linkSeguro;
    @SerializedName("nroCta")
    private String nroCta;
    @SerializedName("sucursalCta")
    private String sucursalCta;

    public DatosSolicitudPrestamo getDatosSolicitudPrestamo() {
        return this.datosSolicitudPrestamo;
    }

    public void setDatosSolicitudPrestamo(DatosSolicitudPrestamo datosSolicitudPrestamo2) {
        this.datosSolicitudPrestamo = datosSolicitudPrestamo2;
    }

    public String getNroCta() {
        return this.nroCta;
    }

    public void setNroCta(String str) {
        this.nroCta = str;
    }

    public String getSucursalCta() {
        return this.sucursalCta;
    }

    public void setSucursalCta(String str) {
        this.sucursalCta = str;
    }

    public LinkSeguroBean getLinkSeguro() {
        return this.linkSeguro;
    }

    public void setLinkSeguro(LinkSeguroBean linkSeguroBean) {
        this.linkSeguro = linkSeguroBean;
    }
}
