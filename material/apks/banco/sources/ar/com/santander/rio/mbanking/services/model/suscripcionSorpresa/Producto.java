package ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa;

import com.google.gson.annotations.SerializedName;

public class Producto {
    @SerializedName("listaDeMensajes")
    ListaMensajes listaMensajes;
    @SerializedName("listaDeSuscripciones")
    ListaSuscripciones listaSuscripciones;
    @SerializedName("nroProducto")
    String nroProducto;
    @SerializedName("numeroProducto")
    String numeroProducto;

    public String getNumeroProducto() {
        return this.numeroProducto;
    }

    public void setNumeroProducto(String str) {
        this.numeroProducto = str;
    }

    public ListaSuscripciones getListaSuscripciones() {
        return this.listaSuscripciones;
    }

    public void setListaSuscripciones(ListaSuscripciones listaSuscripciones2) {
        this.listaSuscripciones = listaSuscripciones2;
    }

    public void setNroProducto(String str) {
        this.nroProducto = str;
    }

    public String getNroProducto() {
        return this.nroProducto;
    }

    public ListaMensajes getListaMensajes() {
        return this.listaMensajes;
    }

    public void setListaMensajes(ListaMensajes listaMensajes2) {
        this.listaMensajes = listaMensajes2;
    }
}
