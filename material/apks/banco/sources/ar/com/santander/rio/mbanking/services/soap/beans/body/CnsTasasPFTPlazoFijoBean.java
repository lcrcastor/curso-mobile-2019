package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsTasasPFTPlazoFijoBean {
    public String descPlazo;
    public String importeMinimo;
    @SerializedName("listaTasas")
    public CnsTasasPFTListaTasasBean listaTasas;
    public String monedaTasa;
    public String tipoPlazo;

    public CnsTasasPFTPlazoFijoBean(String str, String str2, String str3, String str4, CnsTasasPFTListaTasasBean cnsTasasPFTListaTasasBean) {
        this.tipoPlazo = str;
        this.descPlazo = str2;
        this.monedaTasa = str3;
        this.importeMinimo = str4;
        this.listaTasas = cnsTasasPFTListaTasasBean;
    }

    public CnsTasasPFTPlazoFijoBean() {
    }
}
