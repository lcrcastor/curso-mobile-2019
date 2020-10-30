package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class BajaSeguroBean {
    @SerializedName("numCertificado")
    private String numCertificado;
    @SerializedName("numPoliza")
    private String numPoliza;

    public BajaSeguroBean() {
    }

    public BajaSeguroBean(String str, String str2) {
        this.numPoliza = str;
        this.numCertificado = str2;
    }

    public void setNumPoliza(String str) {
        this.numPoliza = str;
    }

    public void setNumCertificado(String str) {
        this.numCertificado = str;
    }
}
