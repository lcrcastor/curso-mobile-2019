package ar.com.santander.rio.mbanking.services.model.creditos;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaCuentas {
    @SerializedName("datosCta")
    List<DatosCuenta> listaDatosCuenta;

    public List<DatosCuenta> getListaDatosCuenta() {
        return this.listaDatosCuenta;
    }

    public void setListaDatosCuenta(List<DatosCuenta> list) {
        this.listaDatosCuenta = list;
    }
}
