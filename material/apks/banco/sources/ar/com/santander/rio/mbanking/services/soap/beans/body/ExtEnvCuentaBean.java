package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ExtEnvCuentaBean {
    @SerializedName("descCtaDebito")
    public String descCtaDebito;
    @SerializedName("numero")
    public String numero;
    @SerializedName("saldoCtaDebito")
    public String saldoCtaDebito;
    @SerializedName("sucursal")
    public String sucursal;
    @SerializedName("tipo")
    public String tipo;

    public ExtEnvCuentaBean() {
    }

    public ExtEnvCuentaBean(String str, String str2, String str3, String str4, String str5) {
        this.tipo = str;
        this.sucursal = str2;
        this.numero = str3;
        this.descCtaDebito = str4;
        this.saldoCtaDebito = str5;
    }
}
