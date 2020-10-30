package ar.com.santander.rio.mbanking.services.model.tenenciaCreditosModel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListaCategoriasCreditos {
    @SerializedName("categoriaCredito")
    private List<CategoriaCredito> categoriacredito;

    public void setCategoriacredito(List<CategoriaCredito> list) {
        this.categoriacredito = list;
    }

    public List<CategoriaCredito> getCategoriacredito() {
        return this.categoriacredito;
    }
}
