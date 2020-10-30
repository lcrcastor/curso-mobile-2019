package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetDatosInicialesDolaresBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cotizacionDolar")
    public String cotizacionDolar;
    @SerializedName("cuentasDestino")
    public CompraVentaDolaresCuentasBean cuentasDestino;
    @SerializedName("cuentasOrigen")
    public CompraVentaDolaresCuentasBean cuentasOrigen;

    public GetDatosInicialesDolaresBodyResponseBean(CompraVentaDolaresCuentasBean compraVentaDolaresCuentasBean, CompraVentaDolaresCuentasBean compraVentaDolaresCuentasBean2, String str) {
        this.cuentasOrigen = compraVentaDolaresCuentasBean;
        this.cuentasDestino = compraVentaDolaresCuentasBean2;
        this.cotizacionDolar = str;
    }
}
