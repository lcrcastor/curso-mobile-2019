package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ExtEnvDatosBean {
    @SerializedName("importeLimite")
    public String importeLimite;
    @SerializedName("importeMultiplo")
    public String importeMultiplo;

    public ExtEnvDatosBean() {
    }

    public ExtEnvDatosBean(String str, String str2) {
        this.importeLimite = str;
        this.importeMultiplo = str2;
    }
}
