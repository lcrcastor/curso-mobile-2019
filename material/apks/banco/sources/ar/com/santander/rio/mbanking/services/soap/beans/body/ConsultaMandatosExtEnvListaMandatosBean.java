package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ConsultaMandatosExtEnvListaMandatosBean {
    @SerializedName("mandato")
    public List<MandatoBean> mandato;

    public ConsultaMandatosExtEnvListaMandatosBean() {
        this.mandato = new ArrayList();
    }

    public ConsultaMandatosExtEnvListaMandatosBean(List<MandatoBean> list) {
        this.mandato = list;
    }
}
