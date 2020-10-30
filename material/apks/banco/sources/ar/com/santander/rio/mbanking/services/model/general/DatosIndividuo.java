package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class DatosIndividuo {
    @SerializedName("nup")
    String nup;

    public DatosIndividuo(String str) {
        this.nup = str;
    }
}
