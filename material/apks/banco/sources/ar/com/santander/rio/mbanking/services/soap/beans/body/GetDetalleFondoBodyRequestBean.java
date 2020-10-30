package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetDetalleFondoBodyRequestBean {
    @SerializedName("idFondo")
    private String idFondo;

    public GetDetalleFondoBodyRequestBean(String str) {
        this.idFondo = str;
    }
}
