package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class AutorizacionesConsumos {
    @SerializedName("mensajeAutorizacion")
    private String mensajeAutorizacion;
    @SerializedName("resAutorizacion")
    private String resAutorizacion;
    @SerializedName("tarjeta")
    private List<AuthTarjeta> tarjetas;
    @SerializedName("totales")
    private Totales totales;

    public AutorizacionesConsumos(String str, String str2, Totales totales2, List<AuthTarjeta> list) {
        this.resAutorizacion = "";
        this.mensajeAutorizacion = "";
        this.resAutorizacion = str;
        this.mensajeAutorizacion = str2;
        this.totales = totales2;
        this.tarjetas = list;
    }

    public AutorizacionesConsumos() {
        this.resAutorizacion = "";
        this.mensajeAutorizacion = "";
        this.totales = new Totales();
        this.tarjetas = new ArrayList();
    }

    public String getResAutorizacion() {
        return this.resAutorizacion;
    }

    public void setResAutorizacion(String str) {
        this.resAutorizacion = str;
    }

    public String getMensajeAutorizacion() {
        return this.mensajeAutorizacion;
    }

    public void setMensajeAutorizacion(String str) {
        this.mensajeAutorizacion = str;
    }

    public Totales getTotales() {
        return this.totales;
    }

    public void setTotales(Totales totales2) {
        this.totales = totales2;
    }

    public List<AuthTarjeta> getTarjeta() {
        return this.tarjetas;
    }

    public void setTarjeta(List<AuthTarjeta> list) {
        this.tarjetas = list;
    }
}
