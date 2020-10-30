package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.DatosIndividuo;
import com.google.gson.annotations.SerializedName;

public class AceptaTerminosBodyRequestBean {
    @SerializedName("datosIndividuo")
    DatosIndividuo datosIndividuo;

    public AceptaTerminosBodyRequestBean(String str) {
        this.datosIndividuo = new DatosIndividuo(str);
    }

    public DatosIndividuo getDatosIndividuo() {
        return this.datosIndividuo;
    }

    public void setDatosIndividuo(DatosIndividuo datosIndividuo2) {
        this.datosIndividuo = datosIndividuo2;
    }
}
