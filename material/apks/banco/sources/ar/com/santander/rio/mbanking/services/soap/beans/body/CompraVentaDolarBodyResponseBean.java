package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CompraVentaDolarBodyResponseBean extends ErrorBodyBean {
    @SerializedName("fecha")
    public String fecha;
    @SerializedName("hora")
    public String hora;
    @SerializedName("legales1")
    public String legales1;
    @SerializedName("legales2")
    public String legales2;
    @SerializedName("numeroDeComprobante")
    public String numeroDeComprobante;
    @SerializedName("numeroDeOperacion")
    public String numeroDeOperacion;

    public CompraVentaDolarBodyResponseBean(String str, String str2, String str3, String str4, String str5, String str6) {
        this.fecha = str;
        this.hora = str2;
        this.numeroDeOperacion = str3;
        this.numeroDeComprobante = str4;
        this.legales1 = str5;
        this.legales2 = str6;
    }
}
