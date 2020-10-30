package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsTenenciasDatosCuentaBean {
    @SerializedName("monedaCuenta")
    public String monedaCuenta;
    @SerializedName("numCuenta")
    public String numCuenta;
    @SerializedName("saldo")
    public String saldo;
    @SerializedName("sucCuenta")
    public String sucCuenta;
    @SerializedName("tipoCuenta")
    public String tipoCuenta;

    public CnsTenenciasDatosCuentaBean(String str, String str2, String str3, String str4, String str5) {
        this.tipoCuenta = str;
        this.sucCuenta = str2;
        this.numCuenta = str3;
        this.monedaCuenta = str4;
        this.saldo = str5;
    }
}
