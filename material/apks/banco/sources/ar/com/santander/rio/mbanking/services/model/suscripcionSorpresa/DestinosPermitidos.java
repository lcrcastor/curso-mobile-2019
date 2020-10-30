package ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa;

import com.google.gson.annotations.SerializedName;

public class DestinosPermitidos {
    @SerializedName("dpAgenda")
    String dpAgenda;
    @SerializedName("dpCelular")
    String dpCelular;
    @SerializedName("dpMail")
    String dpMail;

    public String getDpMail() {
        return this.dpMail;
    }

    public void setDpMail(String str) {
        this.dpMail = str;
    }

    public String getDpCelular() {
        return this.dpCelular;
    }

    public void setDpCelular(String str) {
        this.dpCelular = str;
    }

    public String getDpAgenda() {
        return this.dpAgenda;
    }

    public void setDpAgenda(String str) {
        this.dpAgenda = str;
    }
}
