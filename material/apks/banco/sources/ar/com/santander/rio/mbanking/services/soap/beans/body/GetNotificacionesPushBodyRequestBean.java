package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetNotificacionesPushBodyRequestBean {
    @SerializedName("idDispositivoTwp")
    private String idDispositivoTwp;
    @SerializedName("nroPagina")
    private String nroPagina;
    @SerializedName("twinpushAppId")
    private String twinpushAppId;
    @SerializedName("twinpushToken ")
    private String twinpushToken;

    public GetNotificacionesPushBodyRequestBean() {
    }

    public GetNotificacionesPushBodyRequestBean(String str, String str2, String str3, String str4) {
        this.twinpushToken = str;
        this.twinpushAppId = str2;
        this.idDispositivoTwp = str3;
        this.nroPagina = str4;
    }

    public String getTwinpushToken() {
        return this.twinpushToken;
    }

    public void setTwinpushToken(String str) {
        this.twinpushToken = str;
    }

    public String getTwinpushAppId() {
        return this.twinpushAppId;
    }

    public void setTwinpushAppId(String str) {
        this.twinpushAppId = str;
    }

    public String getIdDispositivoTwp() {
        return this.idDispositivoTwp;
    }

    public void setIdDispositivoTwp(String str) {
        this.idDispositivoTwp = str;
    }

    public String getNroPagina() {
        return this.nroPagina;
    }

    public void setNroPagina(String str) {
        this.nroPagina = str;
    }
}
