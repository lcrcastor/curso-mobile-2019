package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Destinos {
    @SerializedName("destino")
    private List<Destino> destinos;

    public List<Destino> getDestinos() {
        return this.destinos;
    }

    public void setDestinos(List<Destino> list) {
        this.destinos = list;
    }

    public Destinos(List<Destino> list) {
        this.destinos = list;
    }

    public Destinos() {
    }
}
