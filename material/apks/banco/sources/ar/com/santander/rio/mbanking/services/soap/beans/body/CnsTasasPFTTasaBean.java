package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsTasasPFTTasaBean {
    @SerializedName("plazoTasa")
    public String plazoTasa;
    @SerializedName("tasaOnline")
    public String tasaOnline;

    public CnsTasasPFTTasaBean(String str, String str2) {
        this.plazoTasa = str;
        this.tasaOnline = str2;
    }
}
