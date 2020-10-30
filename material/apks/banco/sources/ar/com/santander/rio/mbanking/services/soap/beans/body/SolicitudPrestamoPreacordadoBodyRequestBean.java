package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamo;
import com.google.gson.annotations.SerializedName;

public class SolicitudPrestamoPreacordadoBodyRequestBean {
    @SerializedName("datosSolicPrest")
    DatosSolicitudPrestamo datosSolicitudPrestamo;

    public DatosSolicitudPrestamo getDatosSolicitudPrestamo() {
        return this.datosSolicitudPrestamo;
    }

    public void setDatosSolicitudPrestamo(DatosSolicitudPrestamo datosSolicitudPrestamo2) {
        this.datosSolicitudPrestamo = datosSolicitudPrestamo2;
    }
}
