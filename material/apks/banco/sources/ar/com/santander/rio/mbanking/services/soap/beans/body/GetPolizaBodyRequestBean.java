package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetPolizaBodyRequestBean {
    @SerializedName("codRamo")
    private String codRamo;
    @SerializedName("numCertificado")
    private String numCertificado;
    @SerializedName("numPoliza")
    private String numPoliza;

    public GetPolizaBodyRequestBean(String str, String str2, String str3) {
        this.codRamo = str;
        this.numPoliza = str2;
        this.numCertificado = str3;
    }
}
