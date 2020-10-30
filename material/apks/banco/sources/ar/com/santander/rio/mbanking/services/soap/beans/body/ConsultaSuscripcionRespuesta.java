package ar.com.santander.rio.mbanking.services.soap.beans.body;

import ar.com.santander.rio.mbanking.services.model.general.Destinos;
import ar.com.santander.rio.mbanking.services.model.suscripcionSorpresa.ListaProductos;
import com.google.gson.annotations.SerializedName;

public class ConsultaSuscripcionRespuesta {
    @SerializedName("ClienteEstado")
    String clienteEstado;
    @SerializedName("CodRet")
    String codRet;
    @SerializedName("destinos")
    Destinos destinos;
    @SerializedName("leyenda")
    ListaLeyendas listaLeyendas;
    @SerializedName("listaProductos")
    ListaProductos listaProductos;

    public String getCodRet() {
        return this.codRet;
    }

    public void setCodRet(String str) {
        this.codRet = str;
    }

    public String getClienteEstado() {
        return this.clienteEstado;
    }

    public void setClienteEstado(String str) {
        this.clienteEstado = str;
    }

    public Destinos getDestinos() {
        return this.destinos;
    }

    public void setDestinos(Destinos destinos2) {
        this.destinos = destinos2;
    }

    public ListaProductos getListaProductos() {
        return this.listaProductos;
    }

    public void setListaProductos(ListaProductos listaProductos2) {
        this.listaProductos = listaProductos2;
    }
}
