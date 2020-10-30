package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.Notificaciones;

public class Crm {
    public String cantidad;
    public Notificaciones notificaciones;

    public Crm() {
    }

    public Crm(String str, Notificaciones notificaciones2) {
        this.cantidad = str;
        this.notificaciones = notificaciones2;
    }

    public Notificaciones getNotificaciones() {
        return this.notificaciones;
    }

    public void setNotificaciones(Notificaciones notificaciones2) {
        this.notificaciones = notificaciones2;
    }
}
