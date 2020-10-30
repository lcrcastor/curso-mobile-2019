package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ContratarSeguroMovilBodyRequestBean {
    @SerializedName("bajaSeguro")
    private BajaSeguroBean bajaSeguro;
    @SerializedName("codOcupacion")
    private String codOcupacion;
    @SerializedName("codPlan")
    private String codPlan;
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
    @SerializedName("propietario")
    private String propietario;
    @SerializedName("tipoOperacion")
    private String tipoOperacion;

    public ContratarSeguroMovilBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7, BajaSeguroBean bajaSeguroBean, CuentaShortBean cuentaShortBean, TarjetaBean tarjetaBean) {
        this.tipoOperacion = str;
        this.codRamo = str2;
        this.codProducto = str3;
        this.numCotizacion = str4;
        this.codPlan = str5;
        this.propietario = str6;
        this.codOcupacion = str7;
        this.bajaSeguro = bajaSeguroBean;
        this.medioPagoCuenta = cuentaShortBean;
        this.medioPagoTarjeta = tarjetaBean;
    }
}
