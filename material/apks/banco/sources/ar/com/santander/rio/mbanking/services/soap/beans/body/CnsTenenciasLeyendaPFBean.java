package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class CnsTenenciasLeyendaPFBean {
    @SerializedName("descripcion")
    public String descripcion;
    @SerializedName("idLeyenda")
    public String idLeyenda;
    @SerializedName("subproducto")
    public String subproducto;
    @SerializedName("titulo")
    public String titulo;

    public CnsTenenciasLeyendaPFBean(String str, String str2, String str3, String str4) {
        this.idLeyenda = str;
        this.titulo = str2;
        this.subproducto = str3;
        this.descripcion = str4;
    }
}
