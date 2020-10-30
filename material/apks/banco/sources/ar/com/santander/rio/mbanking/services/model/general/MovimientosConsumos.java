package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovimientosConsumos {
    @SerializedName("tarjeta")
    private List<TarjetaConsumos> tarjetas;
    @SerializedName("totales")
    private Totales totales;

    public MovimientosConsumos(Totales totales2, List<TarjetaConsumos> list) {
        this.totales = totales2;
        this.tarjetas = list;
    }

    public MovimientosConsumos() {
    }

    public Totales getTotales() {
        return this.totales;
    }

    public void setTotales(Totales totales2) {
        this.totales = totales2;
    }

    public List<TarjetaConsumos> getTarjetas() {
        return this.tarjetas;
    }

    public void setTarjetas(List<TarjetaConsumos> list) {
        this.tarjetas = list;
    }
}
