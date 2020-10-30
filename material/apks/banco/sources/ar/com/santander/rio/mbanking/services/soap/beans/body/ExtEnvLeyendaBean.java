package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ExtEnvLeyendaBean {
    @SerializedName("descripcion")
    public String descripcion;
    @SerializedName("idLeyenda")
    public String idLeyenda;
    @SerializedName("titulo")
    public String titulo;

    public ExtEnvLeyendaBean() {
    }

    public ExtEnvLeyendaBean(String str, String str2, String str3) {
        this.descripcion = str;
        this.idLeyenda = str2;
        this.titulo = str3;
    }
}
