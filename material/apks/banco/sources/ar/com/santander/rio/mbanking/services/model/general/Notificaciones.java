package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Notificaciones {
    @SerializedName("notificacion")
    private List<Notificacion> lstNotificaciones = new ArrayList();

    public Notificaciones() {
    }

    public Notificaciones(List<Notificacion> list) {
        this.lstNotificaciones = list;
    }

    public List<Notificacion> getListNotificaciones() {
        return this.lstNotificaciones;
    }

    public void setListNotificaciones(List<Notificacion> list) {
        this.lstNotificaciones = list;
    }
}
