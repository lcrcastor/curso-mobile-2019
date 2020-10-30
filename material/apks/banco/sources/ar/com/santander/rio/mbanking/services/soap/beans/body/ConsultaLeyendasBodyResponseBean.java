package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.Leyenda;
import com.google.gson.annotations.SerializedName;

public class ConsultaLeyendasBodyResponseBean {
    @SerializedName("leyenda")
    public Leyenda leyenda;
}
