package ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa;

import com.google.gson.annotations.SerializedName;

public class DatosSuscripcion {
    @SerializedName("listaDeProductos")
    ListaProductos listaProductos;
    @SerializedName("nup")
    String nup;

    public String getNup() {
        return this.nup;
    }

    public void setNup(String str) {
        this.nup = str;
    }

    public ListaProductos getListaProductos() {
        return this.listaProductos;
    }

    public void setListaProductos(ListaProductos listaProductos2) {
        this.listaProductos = listaProductos2;
    }
}
