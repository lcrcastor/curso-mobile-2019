package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsDeudaPTBodyRequestBean {
    @SerializedName("tipoDeuda")
    private String tipoDeuda;

    public CnsDeudaPTBodyRequestBean() {
    }

    public CnsDeudaPTBodyRequestBean(String str) {
        this.tipoDeuda = str;
    }

    public String getTipoDeuda() {
        return this.tipoDeuda;
    }

    public void setTipoDeuda(String str) {
        this.tipoDeuda = str;
    }
}
