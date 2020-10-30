package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetPolizaBodyResponseBean extends ErrorBodyBean {
    @SerializedName("archivoNombre")
    private String archivoNombre;
    @SerializedName("archivoPoliza")
    private String archivoPoliza;

    public String getArchivoPoliza() {
        return this.archivoPoliza;
    }

    public void setArchivoPoliza(String str) {
        this.archivoPoliza = str;
    }

    public String getArchivoNombre() {
        return this.archivoNombre;
    }

    public void setArchivoNombre(String str) {
        this.archivoNombre = str;
    }
}
