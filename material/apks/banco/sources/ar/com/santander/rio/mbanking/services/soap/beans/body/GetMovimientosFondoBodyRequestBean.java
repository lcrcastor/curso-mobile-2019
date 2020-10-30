package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetMovimientosFondoBodyRequestBean {
    @SerializedName("fechaDesde")
    private String fechaDesde;
    @SerializedName("fechaHasta")
    private String fechaHasta;
    @SerializedName("idFondo")
    private String idFondo;
    @SerializedName("importeDesde")
    private String importeDesde;
    @SerializedName("importeHasta")
    private String importeHasta;
    @SerializedName("nroCtaTitulo")
    private String nroCtaTitulo;
    @SerializedName("ultimaSemana")
    private String ultimaSemana;

    public GetMovimientosFondoBodyRequestBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.idFondo = str;
        this.fechaDesde = str2;
        this.fechaHasta = str3;
        this.importeDesde = str4;
        this.importeHasta = str5;
        this.ultimaSemana = str6;
        this.nroCtaTitulo = str7;
    }

    public GetMovimientosFondoBodyRequestBean(String str, String str2, Boolean bool) {
        this.idFondo = str;
        this.nroCtaTitulo = str2;
        this.fechaDesde = "";
        this.fechaHasta = "";
        this.importeDesde = "";
        this.importeHasta = "";
        if (bool.booleanValue()) {
            this.ultimaSemana = "S";
        } else {
            this.ultimaSemana = "N";
        }
    }
}
