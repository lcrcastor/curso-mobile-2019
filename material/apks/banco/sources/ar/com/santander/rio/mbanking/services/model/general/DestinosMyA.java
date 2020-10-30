package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class DestinosMyA {
    @SerializedName("destinos")
    private Destinos destinos;
    @SerializedName("estadoSuscripcion")
    private String estadoSuscripcion;

    public String getEstadoSuscripcion() {
        return this.estadoSuscripcion;
    }

    public void setEstadoSuscripcion(String str) {
        this.estadoSuscripcion = str;
    }

    public Destinos getDestinos() {
        return this.destinos;
    }

    public void setDestinos(Destinos destinos2) {
        this.destinos = destinos2;
    }
}
