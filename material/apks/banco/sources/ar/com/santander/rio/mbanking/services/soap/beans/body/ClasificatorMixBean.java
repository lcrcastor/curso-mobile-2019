package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class ClasificatorMixBean {
    @SerializedName("idClasificador")
    public String idClasificator;
    @SerializedName("idClasificadorMix")
    public String idClasificatorMix;

    public ClasificatorMixBean() {
    }

    public ClasificatorMixBean(String str, String str2) {
        this.idClasificatorMix = str;
        this.idClasificator = str2;
    }
}
