package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TarjetasDebito {
    @SerializedName("tarjetaDebito")
    List<Tarjeta> tarjetaDebito;

    public TarjetasDebito(List<Tarjeta> list) {
        this.tarjetaDebito = list;
    }

    public TarjetasDebito() {
    }

    public List<Tarjeta> getTarjetas() {
        return this.tarjetaDebito;
    }

    public void setTarjetas(List<Tarjeta> list) {
        this.tarjetaDebito = list;
    }
}
