package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetNotificacionesPushBodyResponseBean extends ErrorBodyBean {
    @SerializedName("masNov")
    private String masNov;
    @SerializedName("notificaciones")
    private NotificacionesPushBean notificaciones;

    public GetNotificacionesPushBodyResponseBean() {
    }

    public GetNotificacionesPushBodyResponseBean(NotificacionesPushBean notificacionesPushBean, String str) {
        this.notificaciones = notificacionesPushBean;
        this.masNov = str;
    }

    public NotificacionesPushBean getNotificaciones() {
        return this.notificaciones;
    }

    public void setNotificaciones(NotificacionesPushBean notificacionesPushBean) {
        this.notificaciones = notificacionesPushBean;
    }

    public String getMasNov() {
        return this.masNov;
    }

    public void setMasNov(String str) {
        this.masNov = str;
    }
}
