package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.ListaSuscripciones;
import com.google.gson.annotations.SerializedName;

public class ActualizarMensajesMyABodyRequestBean {
    @SerializedName("suscripciones")
    ListaSuscripciones listaSuscripciones;
    @SerializedName("NUP")
    String nup;

    public String getNup() {
        return this.nup;
    }

    public void setNup(String str) {
        this.nup = str;
    }

    public ListaSuscripciones getListaSuscripciones() {
        return this.listaSuscripciones;
    }

    public void setListaSuscripciones(ListaSuscripciones listaSuscripciones2) {
        this.listaSuscripciones = listaSuscripciones2;
    }
}
