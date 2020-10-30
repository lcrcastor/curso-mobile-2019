package ar.com.santander.rio.mbanking.services.model.creditos;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaDestinoPrestamo {
    @SerializedName("listaDestino")
    List<ListaDestino> listaDestinoPrest;

    public List<ListaDestino> getListaDestinoPrest() {
        return this.listaDestinoPrest;
    }

    public void setListaDestinoPrest(List<ListaDestino> list) {
        this.listaDestinoPrest = list;
    }
}
