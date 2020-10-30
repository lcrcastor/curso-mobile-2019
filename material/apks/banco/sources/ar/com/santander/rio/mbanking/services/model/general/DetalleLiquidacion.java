package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class DetalleLiquidacion {
    @SerializedName("legales")
    private String legales;
    private Movimientos movimientos;

    public DetalleLiquidacion(Movimientos movimientos2, String str) {
        this.movimientos = movimientos2;
        this.legales = str;
    }

    public DetalleLiquidacion() {
    }

    public Movimientos getMovimientos() {
        return this.movimientos;
    }

    public void setMovimientos(Movimientos movimientos2) {
        this.movimientos = movimientos2;
    }

    public String getLegales() {
        return this.legales;
    }

    public void setLegales(String str) {
        this.legales = str;
    }
}
