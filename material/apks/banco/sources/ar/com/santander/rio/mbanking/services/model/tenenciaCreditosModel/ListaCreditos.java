package ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaCreditos {
    @SerializedName("datosCredito")
    public List<DatosCredito> datoscredito;

    public List<DatosCredito> getDatoscredito() {
        return this.datoscredito;
    }

    public void setDatoscredito(List<DatosCredito> list) {
        this.datoscredito = list;
    }
}
