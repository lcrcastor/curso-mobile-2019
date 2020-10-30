package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetCuponesSuperClubBodyRequestBean {
    @SerializedName("idAtributo")
    public String idAtributo;
    @SerializedName("idClase")
    public String idClase;
    @SerializedName("nombre")
    public String nombre;

    public GetCuponesSuperClubBodyRequestBean() {
    }

    public GetCuponesSuperClubBodyRequestBean(String str, String str2, String str3) {
        this.idClase = str;
        this.nombre = str2;
        this.idAtributo = str3;
    }
}
