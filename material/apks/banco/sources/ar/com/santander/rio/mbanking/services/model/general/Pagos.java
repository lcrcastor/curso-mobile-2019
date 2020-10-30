package ar.com.santander.rio.mbanking.services.model.general;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Pagos {
    @SerializedName("pago")
    List<Pago> pagos;

    public Pagos(List<Pago> list) {
        this.pagos = list;
    }

    public Pagos() {
    }

    public List<Pago> getPagos() {
        return this.pagos;
    }

    public void setPagos(List<Pago> list) {
        this.pagos = list;
    }
}
