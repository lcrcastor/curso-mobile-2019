package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class NotificacionPushBean {
    @SerializedName("detalleMensaje")
    private String detalleMensaje;
    @SerializedName("fechaApertura")
    private String fechaApertura;
    @SerializedName("fechaCreacion")
    private String fechaCreacion;
    @SerializedName("idNotificacion")
    private String idNotificacion;
    @SerializedName("mensaje")
    private String mensaje;
    @SerializedName("segmento")
    private String segmento;

    public NotificacionPushBean(String str, String str2, String str3, String str4, String str5) {
        this.idNotificacion = str;
        this.fechaCreacion = str2;
        this.fechaApertura = str3;
        this.mensaje = str4;
        this.detalleMensaje = str5;
    }

    public NotificacionPushBean() {
    }

    public String getIdNotificacion() {
        return this.idNotificacion;
    }

    public void setIdNotificacion(String str) {
        this.idNotificacion = str;
    }

    public String getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(String str) {
        this.fechaCreacion = str;
    }

    public String getFechaApertura() {
        return this.fechaApertura;
    }

    public void setFechaApertura(String str) {
        this.fechaApertura = str;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String str) {
        this.mensaje = str;
    }

    public String getDetalleMensaje() {
        return this.detalleMensaje;
    }

    public void setDetalleMensaje(String str) {
        this.detalleMensaje = str;
    }

    public String getSegmento() {
        return this.segmento;
    }

    public void setSegmento(String str) {
        this.segmento = str;
    }
}
