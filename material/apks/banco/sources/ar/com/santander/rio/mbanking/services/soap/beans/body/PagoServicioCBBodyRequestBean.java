package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class PagoServicioCBBodyRequestBean {
    public String aceptaDA;
    @SerializedName("cuenta")
    public CuentaDebitoBean cuenta;
    @SerializedName("deuda")
    public PagoServicioCBDeudaBean deuda;
    public String flujoOp;

    public PagoServicioCBBodyRequestBean() {
    }

    public PagoServicioCBBodyRequestBean(CuentaDebitoBean cuentaDebitoBean, PagoServicioCBDeudaBean pagoServicioCBDeudaBean, String str, String str2) {
        this.cuenta = cuentaDebitoBean;
        this.deuda = pagoServicioCBDeudaBean;
        this.aceptaDA = str;
        this.flujoOp = str2;
    }
}
