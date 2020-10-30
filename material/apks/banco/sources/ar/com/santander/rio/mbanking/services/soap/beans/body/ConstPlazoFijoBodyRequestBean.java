package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ConstPlazoFijoBodyRequestBean {
    public String accionAlVto;
    @SerializedName("cuentaDebito")
    public CuentaDebitoBean cuentaDebitoBean;
    public String importe;
    public String moneda;
    public String plazoDias;
    public String tipoInvocacion;
    public String tipoPlazo = "03";

    public ConstPlazoFijoBodyRequestBean() {
    }

    public ConstPlazoFijoBodyRequestBean(String str, CuentaDebitoBean cuentaDebitoBean2, String str2, String str3, String str4, String str5) {
        this.tipoInvocacion = str;
        this.cuentaDebitoBean = cuentaDebitoBean2;
        this.plazoDias = str2;
        this.importe = str3;
        this.moneda = str4;
        this.accionAlVto = str5;
    }

    public ConstPlazoFijoBodyRequestBean(String str, CuentaDebitoBean cuentaDebitoBean2, String str2, String str3, String str4, String str5, String str6) {
        this.tipoInvocacion = str;
        this.cuentaDebitoBean = cuentaDebitoBean2;
        this.tipoPlazo = str2;
        this.plazoDias = str3;
        this.importe = str4;
        this.moneda = str5;
        this.accionAlVto = str6;
    }
}
