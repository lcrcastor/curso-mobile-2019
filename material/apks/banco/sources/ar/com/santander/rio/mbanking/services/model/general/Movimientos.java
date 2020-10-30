package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Movimientos {
    @SerializedName("movimiento")
    List<Movimiento> movimientos;

    public Movimientos(List<Movimiento> list) {
        this.movimientos = list;
    }

    public Movimientos() {
    }

    public List<Movimiento> getMovimientos() {
        return this.movimientos;
    }

    public void setMovimientos(List<Movimiento> list) {
        this.movimientos = list;
    }
}
