package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.creditos.DatosSolicitudPrestamoProd;
import com.google.gson.annotations.SerializedName;

public class SolicitudPrestamoPreacordadoBodyRequestBeanProd {
    @SerializedName("datosSolicPrest")
    DatosSolicitudPrestamoProd datosSolicitudPrestamoProd;

    public DatosSolicitudPrestamoProd getDatosSolicitudPrestamoProd() {
        return this.datosSolicitudPrestamoProd;
    }

    public void setDatosSolicitudPrestamoProd(DatosSolicitudPrestamoProd datosSolicitudPrestamoProd2) {
        this.datosSolicitudPrestamoProd = datosSolicitudPrestamoProd2;
    }
}
