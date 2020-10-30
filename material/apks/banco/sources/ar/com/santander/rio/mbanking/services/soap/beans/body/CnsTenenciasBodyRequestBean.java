package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsTenenciasBodyRequestBean {
    @SerializedName("tipoCliente")
    public String tipoCliente;

    public CnsTenenciasBodyRequestBean(String str) {
        this.tipoCliente = str;
    }
}
