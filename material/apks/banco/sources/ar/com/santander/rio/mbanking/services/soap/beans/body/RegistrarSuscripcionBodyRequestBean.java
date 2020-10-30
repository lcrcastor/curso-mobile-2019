package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class RegistrarSuscripcionBodyRequestBean {
    @SerializedName("Celular")
    public String celular;
    @SerializedName("EmpresaCel")
    public String empresaCel;
    @SerializedName("Mail")
    public String mail;
    @SerializedName("Nup")
    public String nup;

    public RegistrarSuscripcionBodyRequestBean(String str, String str2, String str3, String str4) {
        this.nup = str;
        this.mail = str2;
        this.celular = str3;
        this.empresaCel = str4;
    }
}
