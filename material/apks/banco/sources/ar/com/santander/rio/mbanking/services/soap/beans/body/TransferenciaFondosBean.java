package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class TransferenciaFondosBean {
    @SerializedName("destino")
    private TransferenciaDestinoFondosBean destino;
    @SerializedName("legales")
    private LegalesFondosBean legales;
    @SerializedName("nroCertificado")
    private String nroCertificado;
    @SerializedName("origen")
    private TransferenciaOrigenFondosBean origen;

    public TransferenciaFondosBean() {
    }

    public TransferenciaFondosBean(TransferenciaOrigenFondosBean transferenciaOrigenFondosBean, TransferenciaDestinoFondosBean transferenciaDestinoFondosBean, LegalesFondosBean legalesFondosBean, String str) {
        this.origen = transferenciaOrigenFondosBean;
        this.destino = transferenciaDestinoFondosBean;
        this.legales = legalesFondosBean;
        this.nroCertificado = str;
    }

    public TransferenciaOrigenFondosBean getOrigen() {
        return this.origen;
    }

    public void setOrigen(TransferenciaOrigenFondosBean transferenciaOrigenFondosBean) {
        this.origen = transferenciaOrigenFondosBean;
    }

    public TransferenciaDestinoFondosBean getDestino() {
        return this.destino;
    }

    public void setDestino(TransferenciaDestinoFondosBean transferenciaDestinoFondosBean) {
        this.destino = transferenciaDestinoFondosBean;
    }

    public LegalesFondosBean getLegales() {
        return this.legales;
    }

    public void setLegales(LegalesFondosBean legalesFondosBean) {
        this.legales = legalesFondosBean;
    }

    public String getNroCertificado() {
        return this.nroCertificado;
    }

    public void setNroCertificado(String str) {
        this.nroCertificado = str;
    }
}
