package ar.com.santander.rio.mbanking.services.model.creditos;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PrestamosPermitidosProd {
    @SerializedName("datosPrestPerm")
    List<DatosPrestamoPermitidoProd> listaDatosPrestamoPermitidoProd;

    public List<DatosPrestamoPermitidoProd> getListaDatosPrestamoPermitidoProd() {
        return this.listaDatosPrestamoPermitidoProd;
    }

    public void setListaDatosPrestamoPermitidoProd(List<DatosPrestamoPermitidoProd> list) {
        this.listaDatosPrestamoPermitidoProd = list;
    }
}
