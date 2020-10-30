package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ConsultaDatosInicialesTransfBodyRequestBean {
    @SerializedName("datosIniciales")
    public DatosIniciales datosIniciales;

    public ConsultaDatosInicialesTransfBodyRequestBean() {
    }

    public ConsultaDatosInicialesTransfBodyRequestBean(DatosIniciales datosIniciales2) {
        this.datosIniciales = datosIniciales2;
    }
}
