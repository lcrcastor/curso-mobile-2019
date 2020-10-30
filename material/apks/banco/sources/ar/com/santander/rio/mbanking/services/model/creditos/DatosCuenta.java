package ar.com.santander.rio.mbanking.services.model.creditos;

import com.google.gson.annotations.SerializedName;

public class DatosCuenta {
    @SerializedName("nroCta")
    String nroCta;
    @SerializedName("sucursalCta")
    String sucursalCta;

    public String getSucursalCta() {
        return this.sucursalCta;
    }

    public void setSucursalCta(String str) {
        this.sucursalCta = str;
    }

    public String getNroCta() {
        return this.nroCta;
    }

    public void setNroCta(String str) {
        this.nroCta = str;
    }
}
