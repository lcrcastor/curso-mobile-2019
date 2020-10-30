package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetDatosInicialesDolaresBodyRequestBean {
    @SerializedName("cuenta")
    CompraVentaDolaresCuentaBean cuenta;
    @SerializedName("tipoOperacion")
    public String tipoOperacion;

    public GetDatosInicialesDolaresBodyRequestBean(String str) {
        this(str, null);
    }

    public GetDatosInicialesDolaresBodyRequestBean(String str, CompraVentaDolaresCuentaBean compraVentaDolaresCuentaBean) {
        this.tipoOperacion = str;
        this.cuenta = compraVentaDolaresCuentaBean;
    }
}
