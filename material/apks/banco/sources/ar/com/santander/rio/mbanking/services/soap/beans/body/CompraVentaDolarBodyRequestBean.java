package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CompraVentaDolarBodyRequestBean {
    @SerializedName("cotizacionDolar")
    public String cotizacionDolar;
    @SerializedName("cuentaDestino")
    CompraVentaDolaresCuentaBean cuentaDestino;
    @SerializedName("cuentaOrigen")
    CompraVentaDolaresCuentaBean cuentaOrigen;
    @SerializedName("importe")
    public String importe;
    @SerializedName("importeAAcreditar")
    public String importeAAcreditar;
    @SerializedName("moneda")
    public String moneda;
    @SerializedName("tipoOperacion")
    public String tipoOperacion;

    public CompraVentaDolarBodyRequestBean(String str, String str2, String str3, String str4, String str5, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean2) {
        this.tipoOperacion = str;
        this.moneda = str2;
        this.importe = str3;
        this.importeAAcreditar = str4;
        this.cotizacionDolar = str5;
        this.cuentaOrigen = compraVentaDolaresCuentaBean;
        this.cuentaDestino = compraVentaDolaresCuentaBean2;
    }
}
