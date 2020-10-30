package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetMovimientosFondoBodyResponseBean extends ErrorBodyBean {
    @SerializedName("idFondo")
    public String idFondo;
    @SerializedName("movimientos")
    public ListaMovimientosFondosBean listaMovimientosFondosBean;

    public GetMovimientosFondoBodyResponseBean() {
    }

    public GetMovimientosFondoBodyResponseBean(String str, ListaMovimientosFondosBean listaMovimientosFondosBean2) {
        this.idFondo = str;
        this.listaMovimientosFondosBean = listaMovimientosFondosBean2;
    }

    public String getIdFondo() {
        return this.idFondo;
    }

    public void setIdFondo(String str) {
        this.idFondo = str;
    }

    public ListaMovimientosFondosBean getListaMovimientosFondosBean() {
        return this.listaMovimientosFondosBean;
    }

    public void setListaMovimientosFondosBean(ListaMovimientosFondosBean listaMovimientosFondosBean2) {
        this.listaMovimientosFondosBean = listaMovimientosFondosBean2;
    }
}
