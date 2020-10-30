package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class TarjetaConsumos {
    @SerializedName("codigoTarjeta")
    private String codigoTarjeta;
    @SerializedName("dolares")
    private String dolares;
    @SerializedName("movimiento")
    private List<MovimientoConsumos> movimientos;
    @SerializedName("pesos")
    private String pesos;

    public TarjetaConsumos(String str, String str2, String str3, List<MovimientoConsumos> list) {
        this.codigoTarjeta = str;
        this.dolares = str2;
        this.pesos = str3;
        this.movimientos = list;
    }

    public TarjetaConsumos() {
        this.movimientos = new ArrayList();
    }

    public String getCodigoTarjeta() {
        return this.codigoTarjeta;
    }

    public void setCodigoTarjeta(String str) {
        this.codigoTarjeta = str;
    }

    public String getDolares() {
        return this.dolares;
    }

    public void setDolares(String str) {
        this.dolares = str;
    }

    public String getPesos() {
        return this.pesos;
    }

    public void setPesos(String str) {
        this.pesos = str;
    }

    public List<MovimientoConsumos> getMovimientos() {
        return this.movimientos;
    }

    public void setMovimientos(List<MovimientoConsumos> list) {
        this.movimientos = list;
    }
}
