package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ContratarCompraProtegidaBodyRequestBean {
    @SerializedName("codOcupacion")
    private String codOcupacion;
    @SerializedName("codProducto")
    private String codProducto;
    @SerializedName("codRamo")
    private String codRamo;
    @SerializedName("medioPagoCuenta")
    private CuentaShortBean medioPagoCuenta;
    @SerializedName("medioPagoTarjeta")
    private TarjetaBean medioPagoTarjeta;
    @SerializedName("numCotizacion")
    private String numCotizacion;
    @SerializedName("tarjetas")
    private TarjetasBean tarjetas;

    public ContratarCompraProtegidaBodyRequestBean(String str, String str2, String str3, String str4, TarjetasBean tarjetasBean, CuentaShortBean cuentaShortBean, TarjetaBean tarjetaBean) {
        this.codRamo = str;
        this.codProducto = str2;
        this.numCotizacion = str3;
        this.codOcupacion = str4;
        this.tarjetas = tarjetasBean;
        this.medioPagoCuenta = cuentaShortBean;
        this.medioPagoTarjeta = tarjetaBean;
    }
}
