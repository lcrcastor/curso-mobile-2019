package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class UltimoResumenTCBodyRequestBean {
    @SerializedName("codigo_banco")
    public String codigo_banco;
    @SerializedName("numero_cuenta")
    public String numero_cuenta;
    @SerializedName("tarjeta")
    public String tarjeta;

    public UltimoResumenTCBodyRequestBean(String str, String str2, String str3) {
        this.numero_cuenta = str;
        this.tarjeta = str2;
        this.codigo_banco = str3;
    }
}
