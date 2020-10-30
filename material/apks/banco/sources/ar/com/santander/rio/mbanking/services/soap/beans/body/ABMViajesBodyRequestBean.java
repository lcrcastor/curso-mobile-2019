package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ABMViajesBodyRequestBean {
    @SerializedName("mail")
    private String mail;
    @SerializedName("operacion")
    private String operacion;
    @SerializedName("viaje")
    private ViajeBean viaje;

    public ABMViajesBodyRequestBean(String str, String str2, ViajeBean viajeBean) {
        this.operacion = str;
        this.mail = str2;
        this.viaje = viajeBean;
    }
}
