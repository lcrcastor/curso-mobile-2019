package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ConsultaAliasBodyRequestBean {
    @SerializedName("cuenta")
    private CuentaShortBean cuenta;

    public ConsultaAliasBodyRequestBean(CuentaShortBean cuentaShortBean) {
        this.cuenta = cuentaShortBean;
    }
}
