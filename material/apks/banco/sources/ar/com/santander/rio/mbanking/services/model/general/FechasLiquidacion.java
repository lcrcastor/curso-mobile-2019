package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FechasLiquidacion {
    @SerializedName("fecha")
    List<FechaLiquidacion> fechas;

    public FechasLiquidacion(List<FechaLiquidacion> list) {
        this.fechas = list;
    }

    public FechasLiquidacion() {
    }

    public List<FechaLiquidacion> getFechas() {
        return this.fechas;
    }

    public void setFechas(List<FechaLiquidacion> list) {
        this.fechas = list;
    }
}
