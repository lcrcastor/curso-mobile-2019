package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.Cuentas;
import ar.com.santander.rio.mbanking.services.model.general.Tarjetas;
import com.google.gson.annotations.SerializedName;

public class CnsDeudaPTBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cotizacionDolar")
    private String cotizacionDolar;
    @SerializedName("cuentas")
    private Cuentas cuentas;
    @SerializedName("fechaActual")
    private String fechaActual;
    @SerializedName("horaBancaria")
    private String horaBancaria;
    @SerializedName("mensajeHBancario")
    private String mensajeHBancario;
    @SerializedName("stop_mensaje_mes")
    private String stop_mensaje_mes;
    @SerializedName("stop_recordatorio")
    private String stop_recordatorio;
    @SerializedName("tarjetas")
    private Tarjetas tarjetas;

    public String getFechaActual() {
        return this.fechaActual;
    }

    public void setFechaActual(String str) {
        this.fechaActual = str;
    }

    public String getHoraBancaria() {
        return this.horaBancaria;
    }

    public void setHoraBancaria(String str) {
        this.horaBancaria = str;
    }

    public String getMensajeHBancario() {
        return this.mensajeHBancario;
    }

    public void setMensajeHBancario(String str) {
        this.mensajeHBancario = str;
    }

    public String getStopMensajeMes() {
        return this.stop_mensaje_mes;
    }

    public void setStopMensajeMes(String str) {
        this.stop_mensaje_mes = str;
    }

    public String getStopRecordatorio() {
        return this.stop_recordatorio;
    }

    public void setStopRecordatorio(String str) {
        this.stop_recordatorio = str;
    }

    public String getCotizacionDolar() {
        return this.cotizacionDolar;
    }

    public void setCotizacionDolar(String str) {
        this.cotizacionDolar = str;
    }

    public Cuentas getCuentas() {
        return this.cuentas;
    }

    public void setCuentas(Cuentas cuentas2) {
        this.cuentas = cuentas2;
    }

    public Tarjetas getTarjetas() {
        return this.tarjetas;
    }

    public void setTarjetas(Tarjetas tarjetas2) {
        this.tarjetas = tarjetas2;
    }
}
