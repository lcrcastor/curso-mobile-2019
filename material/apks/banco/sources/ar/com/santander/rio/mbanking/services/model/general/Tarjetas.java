package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Tarjetas {
    @SerializedName("tarjeta")
    List<Tarjeta> tarjetas;

    public Tarjetas(List<Tarjeta> list) {
        this.tarjetas = list;
    }

    public Tarjetas() {
    }

    public List<Tarjeta> getTarjetas() {
        return this.tarjetas;
    }

    public void setTarjetas(List<Tarjeta> list) {
        this.tarjetas = list;
    }
}
