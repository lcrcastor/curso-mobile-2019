package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PagosConsumos {
    @SerializedName("pago")
    private List<PagoConsumos> pagos;

    public PagosConsumos(List<PagoConsumos> list) {
        this.pagos = list;
    }

    public PagosConsumos() {
    }

    public List<PagoConsumos> getPagos() {
        return this.pagos;
    }

    public void setPagos(List<PagoConsumos> list) {
        this.pagos = list;
    }
}
