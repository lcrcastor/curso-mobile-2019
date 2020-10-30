package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;

public class TarjetaResumen {
    @SerializedName("liquidacion")
    private Liquidacion liquidacion;

    public TarjetaResumen(Liquidacion liquidacion2) {
        this.liquidacion = liquidacion2;
    }

    public TarjetaResumen() {
    }

    public Liquidacion getLiquidacion() {
        return this.liquidacion;
    }

    public void setLiquidacion(Liquidacion liquidacion2) {
        this.liquidacion = liquidacion2;
    }
}
