package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class StopDebitBodyResponseBean extends ErrorBodyBean {
    @SerializedName("horaBancaria")
    private String fechaComprobante;
    @SerializedName("numComprobante")
    private String numComprobante;

    public String getNumComprobante() {
        return this.numComprobante;
    }

    public void setNumComprobante(String str) {
        this.numComprobante = str;
    }

    public String getFechaComprobante() {
        return this.fechaComprobante;
    }

    public void setFechaComprobante(String str) {
        this.fechaComprobante = str;
    }
}
