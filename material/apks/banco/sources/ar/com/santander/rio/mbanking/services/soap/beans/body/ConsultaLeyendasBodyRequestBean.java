package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.Leyenda;
import com.google.gson.annotations.SerializedName;

public class ConsultaLeyendasBodyRequestBean {
    @SerializedName("leyenda")
    public Leyenda leyenda;

    public ConsultaLeyendasBodyRequestBean(String str) {
        this.leyenda = new Leyenda(str);
    }
}
