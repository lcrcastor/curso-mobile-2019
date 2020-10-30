package ar.com.santander.rio.mbanking.services.model.creditos;

import com.google.gson.annotations.SerializedName;

public class ListaDestino {
    @SerializedName("destFondo")
    String destFondo;
    @SerializedName("destPrestamo")
    String destPrestamo;

    public String getDestFondo() {
        return this.destFondo;
    }

    public void setDestFondo(String str) {
        this.destFondo = str;
    }

    public String getDestPrestamo() {
        return this.destPrestamo;
    }

    public void setDestPrestamo(String str) {
        this.destPrestamo = str;
    }
}
