package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetInfoAdmFondosBodyResponseBean extends ErrorBodyBean {
    @SerializedName("legales")
    public LegalesAdmFondos legales;
    @SerializedName("fondos")
    public ListaFondosBean listaFondosBean;

    public GetInfoAdmFondosBodyResponseBean(ListaFondosBean listaFondosBean2) {
        this.listaFondosBean = listaFondosBean2;
    }

    public GetInfoAdmFondosBodyResponseBean() {
    }

    public ListaFondosBean getListaFondosBean() {
        return this.listaFondosBean;
    }

    public void setListaFondosBean(ListaFondosBean listaFondosBean2) {
        this.listaFondosBean = listaFondosBean2;
    }

    public LegalesAdmFondos getLegales() {
        return this.legales;
    }

    public void setLegales(LegalesAdmFondos legalesAdmFondos) {
        this.legales = legalesAdmFondos;
    }
}
