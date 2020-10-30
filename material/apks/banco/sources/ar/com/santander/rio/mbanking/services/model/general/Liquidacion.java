package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class Liquidacion {
    @SerializedName("anterior")
    private String anterior;
    @SerializedName("datos")
    private DatosLiquidacion datos;
    @SerializedName("detalleLiquidacion")
    private DetalleLiquidacion detalleLiquidacion;
    @SerializedName("fechas")
    private FechasLiquidacion fechas;
    @SerializedName("numero")
    private String numero;
    @SerializedName("pagos")
    private Pagos pagos;
    @SerializedName("proxima")
    private String proxima;
    @SerializedName("resumen")
    private String resumen;
    @SerializedName("saldos")
    private Saldos saldos;
    @SerializedName("sessionID")
    private String sessionID;
    @SerializedName("total")
    private String total;

    public Liquidacion(String str, String str2, String str3, String str4, String str5, Saldos saldos2, DatosLiquidacion datosLiquidacion, FechasLiquidacion fechasLiquidacion, Pagos pagos2, DetalleLiquidacion detalleLiquidacion2) {
        this.anterior = str;
        this.numero = str2;
        this.proxima = str3;
        this.resumen = str4;
        this.total = str5;
        this.saldos = saldos2;
        this.datos = datosLiquidacion;
        this.fechas = fechasLiquidacion;
        this.pagos = pagos2;
        this.detalleLiquidacion = detalleLiquidacion2;
    }

    public Liquidacion() {
    }

    public String getAnterior() {
        return this.anterior;
    }

    public void setAnterior(String str) {
        this.anterior = str;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String str) {
        this.numero = str;
    }

    public String getProxima() {
        return this.proxima;
    }

    public void setProxima(String str) {
        this.proxima = str;
    }

    public String getResumen() {
        return this.resumen;
    }

    public void setResumen(String str) {
        this.resumen = str;
    }

    public String getTotal() {
        return this.total;
    }

    public void setTotal(String str) {
        this.total = str;
    }

    public Saldos getSaldos() {
        return this.saldos;
    }

    public void setSaldos(Saldos saldos2) {
        this.saldos = saldos2;
    }

    public DatosLiquidacion getDatos() {
        return this.datos;
    }

    public void setDatos(DatosLiquidacion datosLiquidacion) {
        this.datos = datosLiquidacion;
    }

    public FechasLiquidacion getFechas() {
        return this.fechas;
    }

    public void setFechas(FechasLiquidacion fechasLiquidacion) {
        this.fechas = fechasLiquidacion;
    }

    public Pagos getPagos() {
        return this.pagos;
    }

    public void setPagos(Pagos pagos2) {
        this.pagos = pagos2;
    }

    public DetalleLiquidacion getDetalleLiquidacion() {
        return this.detalleLiquidacion;
    }

    public void setDetalleLiquidacion(DetalleLiquidacion detalleLiquidacion2) {
        this.detalleLiquidacion = detalleLiquidacion2;
    }

    public String getSessionID() {
        return this.sessionID;
    }

    public void setSessionID(String str) {
        this.sessionID = str;
    }
}
