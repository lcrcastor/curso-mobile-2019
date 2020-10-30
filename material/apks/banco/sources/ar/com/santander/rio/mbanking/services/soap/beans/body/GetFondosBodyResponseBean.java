package ar.com.santander.rio.mbanking.services.soap.beans.body;

import com.google.gson.annotations.SerializedName;

public class GetFondosBodyResponseBean extends ErrorBodyBean {
    @SerializedName("categoriasFondos")
    public ListaCategoriasFondosBean listaCategoriasFondosBean;

    public GetFondosBodyResponseBean() {
    }

    public GetFondosBodyResponseBean(ListaCategoriasFondosBean listaCategoriasFondosBean2) {
        this.listaCategoriasFondosBean = listaCategoriasFondosBean2;
    }

    public ListaCategoriasFondosBean getListaCategoriasFondosBean() {
        return this.listaCategoriasFondosBean;
    }

    public void setListaCategoriasFondosBean(ListaCategoriasFondosBean listaCategoriasFondosBean2) {
        this.listaCategoriasFondosBean = listaCategoriasFondosBean2;
    }
}
