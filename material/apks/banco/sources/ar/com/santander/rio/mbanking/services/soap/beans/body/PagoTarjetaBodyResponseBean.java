package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class PagoTarjetaBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cuentaBDolares")
    private String cuentaBDolares;
    @SerializedName("fechaOperacion")
    private String fechaOperacion;
    @SerializedName("importeDolares")
    private String importeDolares;
    @SerializedName("importePagoTC")
    private String importePagoTC;
    @SerializedName("numComprobante")
    private String numComprobante;
    @SerializedName("tipoCuentaDebito")
    private String tipoCuentaDebito;
    @SerializedName("tipoPagoTC")
    private String tipoPagoTC;

    public String getFechaOperacion() {
        return this.fechaOperacion;
    }

    public void setFechaOperacion(String str) {
        this.fechaOperacion = str;
    }

    public String getNumComprobante() {
        return this.numComprobante;
    }

    public void setNumComprobante(String str) {
        this.numComprobante = str;
    }

    public String getTipoPagoTC() {
        return this.tipoPagoTC;
    }

    public void setTipoPagoTC(String str) {
        this.tipoPagoTC = str;
    }

    public String getImportePagoTC() {
        return this.importePagoTC;
    }

    public void setImportePagoTC(String str) {
        this.importePagoTC = str;
    }

    public String getImporteDolares() {
        return this.importeDolares;
    }

    public void setImporteDolares(String str) {
        this.importeDolares = str;
    }

    public String getTipoCuentaDebito() {
        return this.tipoCuentaDebito;
    }

    public void setTipoCuentaDebito(String str) {
        this.tipoCuentaDebito = str;
    }

    public String getCuentaBDolares() {
        return this.cuentaBDolares;
    }

    public void setCuentaBDolares(String str) {
        this.cuentaBDolares = str;
    }
}
