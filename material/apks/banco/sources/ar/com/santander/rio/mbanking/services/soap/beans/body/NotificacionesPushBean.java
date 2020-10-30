package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class NotificacionesPushBean {
    @SerializedName("notificacion")
    private List<NotificacionPushBean> notificacion;

    public NotificacionesPushBean(List<NotificacionPushBean> list) {
        this.notificacion = list;
    }

    public NotificacionesPushBean() {
        this.notificacion = new ArrayList();
    }

    public List<NotificacionPushBean> getNotificacion() {
        return this.notificacion;
    }

    public void setNotificacion(List<NotificacionPushBean> list) {
        this.notificacion = list;
    }
}
