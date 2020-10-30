package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class DocumentLimites {
    @SerializedName("autorizaciones")
    private Autorizaciones autorizaciones;
    @SerializedName("datos")
    private DatosTarjeta datos;
    @SerializedName("fechas")
    private FechaLiquidacion fechas;
    @SerializedName("saldoenCuenta")
    private SaldoenCuenta saldoenCuenta;

    public DocumentLimites(DatosTarjeta datosTarjeta, Autorizaciones autorizaciones2, SaldoenCuenta saldoenCuenta2, FechaLiquidacion fechaLiquidacion) {
        this.datos = datosTarjeta;
        this.autorizaciones = autorizaciones2;
        this.saldoenCuenta = saldoenCuenta2;
        this.fechas = fechaLiquidacion;
    }

    public DocumentLimites() {
    }

    public DatosTarjeta getDatos() {
        return this.datos;
    }

    public void setDatos(DatosTarjeta datosTarjeta) {
        this.datos = datosTarjeta;
    }

    public Autorizaciones getAutorizaciones() {
        return this.autorizaciones;
    }

    public void setAutorizaciones(Autorizaciones autorizaciones2) {
        this.autorizaciones = autorizaciones2;
    }

    public SaldoenCuenta getSaldoenCuenta() {
        return this.saldoenCuenta;
    }

    public void setSaldoenCuenta(SaldoenCuenta saldoenCuenta2) {
        this.saldoenCuenta = saldoenCuenta2;
    }

    public FechaLiquidacion getFechas() {
        return this.fechas;
    }

    public void setFechas(FechaLiquidacion fechaLiquidacion) {
        this.fechas = fechaLiquidacion;
    }
}
