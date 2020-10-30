package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsDeudaManualBodyResponseBean extends ErrorBodyBean {
    @SerializedName("cuentas")
    public CuentasDeudasBean cuentas;
    @SerializedName("datosDeudas")
    public DatosDeudasBean datosDeudas;
    @SerializedName("leyendaAyuda")
    public String leyendaAyuda;

    public CnsDeudaManualBodyResponseBean(CuentasDeudasBean cuentasDeudasBean, DatosDeudasBean datosDeudasBean) {
        this.cuentas = cuentasDeudasBean;
        this.datosDeudas = datosDeudasBean;
    }
}
