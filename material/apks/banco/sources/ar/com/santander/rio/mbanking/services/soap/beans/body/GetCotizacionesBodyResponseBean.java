package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetCotizacionesBodyResponseBean extends ErrorBodyBean {
    @SerializedName("legales")
    public LegalesFondosBean legales;
    @SerializedName("categorias")
    public ListaCategoriasFondosBean listaCategoriasFondosBean;

    public GetCotizacionesBodyResponseBean() {
    }

    public GetCotizacionesBodyResponseBean(ListaCategoriasFondosBean listaCategoriasFondosBean2) {
        this.listaCategoriasFondosBean = listaCategoriasFondosBean2;
    }

    public ListaCategoriasFondosBean getListaCategoriasFondosBean() {
        return this.listaCategoriasFondosBean;
    }

    public void setListaCategoriasFondosBean(ListaCategoriasFondosBean listaCategoriasFondosBean2) {
        this.listaCategoriasFondosBean = listaCategoriasFondosBean2;
    }

    public LegalesFondosBean getLegales() {
        return this.legales;
    }

    public void setLegales(LegalesFondosBean legalesFondosBean) {
        this.legales = legalesFondosBean;
    }
}
