package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ObtenerEstadoSuscripcionMyABodyRequestBean {
    @SerializedName("nup")
    String nup;

    public String getNup() {
        return this.nup;
    }

    public void setNup(String str) {
        this.nup = str;
    }
}
